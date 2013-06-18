package com.kavilee.ldsys;

import java.util.List;

import com.kavilee.about.About;
import com.kavilee.db.OrderDao;
import com.kavilee.db.UserDao;
import com.kavilee.detail.DSDetail;
import com.kavilee.detail.OrderDetail_admin;
import com.kavilee.detail.OrderDetail_admin_con;
import com.kavilee.detail.UserDetail;
import com.kavilee.interfaces.DeleteOrderInterface;
import com.kavilee.ldsys.R;
import com.kavilee.ldsys.adapters.OrderListAdapter;
import com.kavilee.ldsys.adapters.UserListAdapter;
import com.kavilee.mapview.MyMapView;
import com.kavilee.model.Order;
import com.kavilee.model.User;

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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class AdminLoginSuccessUI extends TabActivity implements OnClickListener,OnItemClickListener{
	
	private ListView myListView1,myListView2,myListView3,myListView4;
	OrderListAdapter adapter1, adapter2;
	UserListAdapter adapter3, adapter4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//获得当前TabActivity的TabHost  
	    TabHost tabHost = getTabHost();   
	                   
	    LayoutInflater.from(this).inflate(R.layout.tabadmin, tabHost.getTabContentView(), true);   
	          
	    tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("未确认的订单").setContent(R.id.view1));  
	    tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("已确认的订单").setContent(R.id.view2));  
	    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("用户信息").setContent(R.id.view3));  
	    tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("配送员信息").setContent(R.id.view4));  
	    
	    tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO 切换tab的时候刷新界面
				initTab1();
			    initTab2();
			    initTab3();
			    initTab4();
			}
		});
	    initTab1();
	    initTab2();
	    initTab3();
	    initTab4();
	    }

	private void initTab3() {
		// TODO 显示用户列表
		myListView3 = (ListView)findViewById(R.id.adminListView3);
		UserDao userDao = new UserDao(this);
		List<User> list = userDao.getAllUser();
		adapter3 = new UserListAdapter(this, list);
		myListView3.setAdapter(adapter3);
		adapter3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//TODO 显示订单详情
				User user1 = adapter3.getItem(arg2);
				RunTime.adminClickedItemUser = user1; 
				Intent intent3 = new Intent(AdminLoginSuccessUI.this,UserDetail.class);
				startActivity(intent3);
			}
		});
	}

	private void initTab4() {
		// TODO 显示配送员列表
		myListView4 = (ListView)findViewById(R.id.adminListView4);
		UserDao userDao = new UserDao(this);
		List<User> list = userDao.getAllStaff();
		adapter4 = new UserListAdapter(this, list);
		myListView4.setAdapter(adapter4);
		adapter4.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//TODO 显示订单详情
				User user2 = adapter4.getItem(arg2);
				RunTime.adminClickedItemUser = user2; 
				Intent intent4 = new Intent(AdminLoginSuccessUI.this,DSDetail.class);
				startActivity(intent4);
			}
		});
	}

	private void initTab1() {
		// TODO 显示未确认的订单
		myListView1 = (ListView)findViewById(R.id.adminListView1);
		
		OrderDao orderDao = new OrderDao(this);
		List<Order> list = orderDao.getUnidentified();
		adapter1 = new OrderListAdapter(this, list);
		adapter1.setDeleteOrderCallBack(new DeleteOrderInterface() {
			@Override
			public void onSuccess() {
				//刷新列表
				OrderDao orderDao = new OrderDao(AdminLoginSuccessUI.this);
				List<Order> list = orderDao.getUnidentified();
				adapter1 = new OrderListAdapter(AdminLoginSuccessUI.this, list);
				myListView1.setAdapter(adapter1);
			}
			
			@Override
			public void onFaild() {
				//不需要做任何事
			}
		});
		myListView1.setAdapter(adapter1);
		adapter1.setOnItemClickListener(this);
	}

	private void initTab2() {
		// TODO 显示已确认的订单
		myListView2 = (ListView)findViewById(R.id.adminListView2);
		OrderDao orderDao = new OrderDao(this);
		List<Order> list = orderDao.getIdentified();
		adapter2 = new OrderListAdapter(this, list);
		adapter2.setDeleteOrderCallBack(new DeleteOrderInterface() {
			@Override
			public void onSuccess() {
				//刷新列表
				OrderDao orderDao = new OrderDao(AdminLoginSuccessUI.this);
				List<Order> list = orderDao.getIdentified();
				adapter2 = new OrderListAdapter(AdminLoginSuccessUI.this, list);
				myListView2.setAdapter(adapter2);
			}
			
			@Override
			public void onFaild() {
				//不需要做任何事
			}
		});
		myListView2.setAdapter(adapter2);
		//两个listview设置不同的ItemListener
		adapter2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//TODO 显示订单详情
				Order order2 = adapter2.getItem(arg2);
				RunTime.adminClickedItemOrder = order2; 
				Intent intent2 = new Intent(AdminLoginSuccessUI.this,OrderDetail_admin.class);
				startActivity(intent2);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//每次显示此页面都刷新一下
		initTab1();
		initTab2();
		initTab3();
		initTab4();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Order order = adapter1.getItem(arg2);
		// TODO 管理员点击的订单保存到内存中
		RunTime.adminClickedItemOrder = order;
		Intent intent = new Intent(AdminLoginSuccessUI.this,OrderDetail_admin_con.class);
		startActivity(intent);
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
				        Intent intent = new Intent(AdminLoginSuccessUI.this,LoginActivity.class);
				        //返回登录界面
				        startActivity(intent);
				        //关闭当前activity
				        AdminLoginSuccessUI.this.finish();
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
			menu.addSubMenu(0, 2, 2, R.string.mapview).setIcon(R.drawable.mapview);
			menu.addSubMenu(0, 3, 3, R.string.about).setIcon(R.drawable.menu_about);
			return super.onCreateOptionsMenu(menu);		
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == 1) {
				new LogoutDialog(AdminLoginSuccessUI.this).show();
			}
			else if (item.getItemId() == 2) {
				//显示地图
				Intent intent = new Intent(AdminLoginSuccessUI.this,MyMapView.class);
				startActivity(intent);
			}else if (item.getItemId() == 3) {
				Intent intent = new Intent(AdminLoginSuccessUI.this,About.class);
				startActivity(intent);
			}
			return super.onOptionsItemSelected(item);		
		}

}
