package com.kavilee.ldsys;

import java.util.List;

import com.kavilee.about.About;
import com.kavilee.db.OrderDao;
import com.kavilee.detail.OrderDetail_ds_complete;
import com.kavilee.detail.OrderDetail_ds_incomplete;
import com.kavilee.ldsys.R;
import com.kavilee.ldsys.adapters.OrderListAdapter;
import com.kavilee.model.Order;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class DsLoginSuccessUI extends TabActivity implements OnClickListener,OnItemClickListener{
	
	private ListView dsListView1,dslListView2;
	OrderListAdapter adapter1,adapter2;
	private TextView userID,userName,userPhonum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		TabHost tabHost = getTabHost(); 
		LayoutInflater.from(this).inflate(R.layout.tabds, tabHost.getTabContentView(), true);   
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("配送中的订单").setContent(R.id.view1));  
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("配送完的订单").setContent(R.id.view2));
	    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("个人信息").setContent(R.id.view3));
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
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
		// TODO 显示配送员个人信息
		userID = (TextView)findViewById(R.id.textViewOID);
		userName = (TextView)findViewById(R.id.textViewUN);
		userPhonum = (TextView)findViewById(R.id.textViewPN);
		
		Intent intent = getIntent();
		//配送员ID转换为string类型
		int a = intent.getIntExtra("userID", 0);
		String s = Integer.toString(a);
		
		userID.setText(s);
		userName.setText(intent.getStringExtra("userName"));
		userPhonum.setText(intent.getStringExtra("userPhonum"));		
	}

	private void initTab1() {
		// TODO 显示配送中的订单
		dsListView1 = (ListView)findViewById(R.id.dsListView1);
		
		OrderDao orderDao = new OrderDao(this);
		List<Order> list = orderDao.getIncomplete(getIntent().getIntExtra("userID",0));
		System.out.println(getIntent().getIntExtra("userID",0));
		adapter1 = new OrderListAdapter(this, list);
		adapter1.setCanMoveDelete(false);
		dsListView1.setAdapter(adapter1);
		adapter1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 显示订单详情
				Order order1 = adapter1.getItem(arg2);
				RunTime.dsClickedItemOrder = order1;
				Intent intent1 = new Intent(DsLoginSuccessUI.this,OrderDetail_ds_incomplete.class);
				startActivity(intent1);
			}
		});
	}

	private void initTab2() {
		// TODO 显示配送完的订单
		dslListView2 = (ListView)findViewById(R.id.dsListView2);
		
		OrderDao orderDao = new OrderDao(this);
		List<Order> list = orderDao.getComplete(getIntent().getIntExtra("userID",0));
		adapter2 = new OrderListAdapter(this, list);
		adapter1.setCanMoveDelete(false);
		dslListView2.setAdapter(adapter2);
		adapter2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 显示订单详情
				Order order2 = adapter2.getItem(arg2);
				RunTime.dsClickedItemOrder = order2;
				Intent intent2 = new Intent(DsLoginSuccessUI.this,OrderDetail_ds_complete.class);
				startActivity(intent2);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}
	//返回键弹出返回登陆界面的对话框
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				showDialog("此操作会退出当前已经登录的用户并返回登录界面，您要继续吗？");
			}
			return super.onKeyDown(keyCode, event);
		}
		private void showDialog(String string) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(string)
			       .setCancelable(true).setTitle("警告")
			       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Intent intent = new Intent(DsLoginSuccessUI.this,LoginActivity.class);
			        	   //返回登录界面
			        	   startActivity(intent);
			        	   //关闭当前activity
			        	   DsLoginSuccessUI.this.finish();
			           }
			       }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();		
		}		
	//设置用户登录界面的菜单
		public boolean onCreateOptionsMenu(Menu menu){
			menu.addSubMenu(0, 1, 1, R.string.exit).setIcon(R.drawable.menu_exit);
			menu.addSubMenu(0, 2, 2, R.string.about).setIcon(R.drawable.menu_about);
			return super.onCreateOptionsMenu(menu);		
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == 1) {
				new LogoutDialog(DsLoginSuccessUI.this).show();
			}
			else if (item.getItemId() == 2) {
				Intent intent = new Intent(DsLoginSuccessUI.this,About.class);
				startActivity(intent);
			}
			return super.onOptionsItemSelected(item);		
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
