package com.kavilee.ldsys;

import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.kavilee.about.About;
import com.kavilee.db.OrderDao;
import com.kavilee.detail.OrderDetail_user;
import com.kavilee.interfaces.DeleteOrderInterface;
import com.kavilee.ldsys.adapters.OrderListAdapter;
import com.kavilee.mapview.ChooseAddressActivity;
import com.kavilee.model.Order;

@SuppressWarnings("deprecation")
public class UserLoginSuccessUI extends TabActivity implements OnClickListener, OnItemClickListener {

	public static final int REQUEST_CODE_CHOOSE_ADDRESS = 101;
	public static final String Bundle_Key_lng = "Bundle_Key_lng";
	public static final String Bundle_Key_lat = "Bunle_Key_lat";
	private EditText goodsnameEditText, addresseeEditText, zipEditText;
	private Order order = new Order();
	private ListView myListView;
	private TextView userID,userName,userPhonum;
	//选择地点按钮
	TextView choose;
	OrderListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// 获得当前TabActivity的TabHost
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.tabuser,
				tabHost.getTabContentView(), true);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("提交订单")
				.setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我的订单")
				.setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("个人信息")
				.setContent(R.id.view3));
		choose = (TextView) findViewById(R.id.textView1);
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO 切换tab的时候刷新界面
				initTab1();
			    initTab2();
			    initTab3();
			}
		});
		initTab1();
		initTab2();
		initTab3();
	}

	private void initTab3() {
		// TODO 显示用户个人信息
		userID = (TextView)findViewById(R.id.textViewOID);
		userName = (TextView)findViewById(R.id.textViewUN);
		userPhonum = (TextView)findViewById(R.id.textViewPN);
		
		Intent intent = getIntent();
		//用户ID转换为string类型
		int a = intent.getIntExtra("userID", 0);
		String s = Integer.toString(a);
		//为textview控件添加显示内容
		userID.setText(s);
		userName.setText(intent.getStringExtra("userName"));
		userPhonum.setText(intent.getStringExtra("userPhonum"));
	}

	private void initTab1() {
		findViewById(R.id.bt_choose).setOnClickListener(this);
		findViewById(R.id.btn_submit).setOnClickListener(this);
		goodsnameEditText = (EditText) findViewById(R.id.gnEditText);
		addresseeEditText = (EditText) findViewById(R.id.aseEditText);
		zipEditText = (EditText) findViewById(R.id.zipEditText);
	}

	
	
	private void initTab2() {
		// TODO 初始化我的订单，从数据库里取出，填到listview里
		myListView = (ListView)findViewById(R.id.myListView);

		OrderDao orderDao = new OrderDao(this);
		List<Order> list = orderDao.getUserOrder(getIntent().getIntExtra("userID",0));
		adapter = new OrderListAdapter(this, list);
		myListView.setAdapter(adapter);
		adapter.setDeleteOrderCallBack(new DeleteOrderInterface() {
			@Override
			public void onSuccess() {
				//刷新列表
				OrderDao orderDao = new OrderDao(UserLoginSuccessUI.this);
				List<Order> list = orderDao.getUserOrder(getIntent().getIntExtra("userID",0));
				adapter = new OrderListAdapter(UserLoginSuccessUI.this, list);
				myListView.setAdapter(adapter);
			}
			
			@Override
			public void onFaild() {
				//不需要做任何事
			}
		});
		adapter.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_choose:
			//设置开启类型为带返回值的Activity
			startActivityForResult(
					new Intent(this, ChooseAddressActivity.class),
					REQUEST_CODE_CHOOSE_ADDRESS);
			break;
		case R.id.btn_submit:
			if (Validator()) {
				if (Submit()) {
					showDialog("您的订单已经成功提交！");
					//清空输入框
					goodsnameEditText.setText("");
					addresseeEditText.setText("");
					zipEditText.setText("");
					choose.setText(R.string.choose);
					goodsnameEditText.requestFocus();
				}
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Order order = adapter.getItem(arg2);
		//把用户点击的这个order保存到内存中
		RunTime.userClickedItemOrder = order;
		Intent intent =   new Intent(UserLoginSuccessUI.this,OrderDetail_user.class);
		startActivity(intent);
	}
	private boolean Validator() {
		// TODO Auto-generated method stub
		String goodsname = goodsnameEditText.getText().toString();
		if (goodsname.equals("")) {
			new ShowDialog(UserLoginSuccessUI.this)
					.show("货物名是必填项，不能为空哦，请重新输入！");
			return false;
		}
		String addressee = addresseeEditText.getText().toString();
		if (addressee.equals("")) {
			new ShowDialog(UserLoginSuccessUI.this)
					.show("收件人是必填项，不能为空哦，请重新输入！");
			return false;
		}
		String zip = zipEditText.getText().toString();
		if (zip.equals("")) {
			new ShowDialog(UserLoginSuccessUI.this).show("邮编是必填项，不能为空哦，请重新输入！");
			return false;
		}if (choose.getText().equals("去选择")) {
			new ShowDialog(UserLoginSuccessUI.this).show("您还没有设置收货地址，请选择地址！");
			return false;
		}
		return true;
	}

	private boolean Submit() {
		OrderDao orderDao = new OrderDao(this);
		Intent intent = getIntent();
		String userName = intent.getStringExtra("userName");
		String userPhonum = intent.getStringExtra("userPhonum");
		int userID = intent.getIntExtra("userID",0);
		
		order.setGoodsName(goodsnameEditText.getText().toString());
		order.setAddressee(addresseeEditText.getText().toString());
		order.setZip(zipEditText.getText().toString());
		order.setUserName(userName);
		order.setUserID(userID);
		order.setUserPhonum(userPhonum);
		return orderDao.Insert(order);
	}
	
	private void showDialog(String msg) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(true).setTitle("恭喜")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// 返回键弹出返回登陆界面的对话框
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showDialog2("此操作会退出当前已经登录的用户并返回登录界面，您要继续吗？");
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDialog2(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(string)
				.setCancelable(true)
				.setTitle("警告")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(UserLoginSuccessUI.this,
								LoginActivity.class);
						// 返回登录界面
						startActivity(intent);
						// 关闭当前activity
						UserLoginSuccessUI.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	// 设置用户登录界面的菜单
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(0, 1, 1, R.string.exit).setIcon(R.drawable.menu_exit);
		menu.addSubMenu(0, 2, 2, R.string.about).setIcon(R.drawable.menu_about);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			new LogoutDialog(UserLoginSuccessUI.this).show();
		} else if (item.getItemId() == 2) {
			Intent intent = new Intent(UserLoginSuccessUI.this, About.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE_ADDRESS) {
			if (resultCode == RESULT_OK) {
				// 这里接收地图页面结束时传过来的数据
				choose.setText("地址 " + "(经度:"
						+ data.getIntExtra(Bundle_Key_lng, 0) + "纬度:"
						+ ";" + data.getIntExtra(Bundle_Key_lat, 0) + ")");
				order.setOrderLngE6((long) data.getIntExtra(Bundle_Key_lng, 0));
				order.setOrderLatE6((long) data.getIntExtra(Bundle_Key_lat, 0));
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO 每次点击都刷新页面
		super.onResume();
		initTab1();
		initTab2();
		initTab3();
	}

}
