package com.kavilee.ldsys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kavilee.about.About;
import com.kavilee.about.AboutGIS;
import com.kavilee.about.AboutSYS;
import com.kavilee.db.UserDao;
import com.kavilee.ldsys.R;
import com.kavilee.model.User;

public class LoginActivity extends Activity{
	
	private Button loginButton,signButton;
	private EditText userEditText,pwdEditText;
	private User currentUser;
	private RelativeLayout abgisRLayout;
	private RelativeLayout absysRLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置窗口没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		//控制打开程序时editText不自动打开输入法键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);    
		
		loginButton = (Button)findViewById(R.id.loginButton);
		signButton = (Button)findViewById(R.id.signButton);
		
		userEditText = (EditText)findViewById(R.id.userEditText);
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		
		abgisRLayout = (RelativeLayout)findViewById(R.id.abgis);
		absysRLayout = (RelativeLayout)findViewById(R.id.absys);
		
        //为登陆按钮绑定监听器
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Validator()){
					if(Login()){
						Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
						Intent intent = null;
						//这里要判断一下用户类型，不同的类型进入不同的页面
						switch (currentUser.getUserType()) {
						case 0:
							intent = new Intent(LoginActivity.this,AdminLoginSuccessUI.class);
							break;
						case 1:
							intent = new Intent(LoginActivity.this,DsLoginSuccessUI.class);
							intent.putExtra("userName" , userEditText.getText().toString());
							intent.putExtra("userPhonum" , currentUser.getUserPhonum());
							intent.putExtra("userID", currentUser.getUserID());
							break;
						case 2:
							intent = new Intent(LoginActivity.this,UserLoginSuccessUI.class);
							intent.putExtra("userName" , userEditText.getText().toString());
							intent.putExtra("userPhonum" , currentUser.getUserPhonum());
							intent.putExtra("userID", currentUser.getUserID());
//							System.out.println("userName------>"+userEditText.getText().toString());
//							System.out.println("userPhonum------>"+currentUser.getUserPhonum());
//							System.out.println("userID-------->"+currentUser.getUserID());
							break;
						}
						startActivity(intent);
						//关掉当前的activity
						LoginActivity.this.finish();
					}else{
						new ShowDialog(LoginActivity.this).show("用户名称或者密码错误，请重新输入！");
					}
				}
			}
		});
		//为注册按钮绑定监听器
		signButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent signIntent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(signIntent);
				LoginActivity.this.finish();//关闭当前activity
				userEditText.setText("");
				pwdEditText.setText("");
				userEditText.requestFocus();
			}
		});
		abgisRLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent abgisIntent = new Intent(LoginActivity.this,AboutGIS.class);
				startActivity(abgisIntent);
			}
		});
		absysRLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent absysIntent = new Intent(LoginActivity.this,AboutSYS.class);
				startActivity(absysIntent);
			}
		});
		
	}
	//验证输入是否合法    
	protected boolean Validator() {
		// TODO Auto-generated method stub
		String username = userEditText.getText().toString();
		if(username.equals("")){
			new ShowDialog(LoginActivity.this).show("登录名是必填项，不能为空呀，请重新输入！");
			return false;
		}
		String pwd = pwdEditText.getText().toString();
		if(pwd.equals("")){
			new ShowDialog(LoginActivity.this).show("密码是必填项，不能为空呀，请重新输入！");
			return false;
		}
		return true;
	}

	//登录时查询数据库
	private boolean Login(){
		String username = userEditText.getText().toString();
		String pwd = pwdEditText.getText().toString();
		//内定管理员是admin，用户名和密码都是admin
		if("admin".equals(username) && "admin".equals(pwd)){
			User admin = new User();
			admin.setUserID(0);
			admin.setUserType(0);
			currentUser = admin;
			return true;
		}
		User result=Query(username,pwd);
		//登录成功，顺便把用户信息记录下来
		if(result!=null){
			currentUser = result;
			return true;
		}
		//返回null，说明登录失败
		else{
			return false;
		}
	}
	private User Query(String username, String pwd) {
		//从数据库中查找有没有这个user，如果没有就会返回null
		UserDao userDao = new UserDao(this);
		return userDao.find(username, pwd);
	}
	//返回键弹出对话框
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new LogoutDialog(LoginActivity.this).show();
		}
		return super.onKeyDown(keyCode, event);
	}
	//设置用户登录界面的菜单
		public boolean onCreateOptionsMenu(Menu menu){
			menu.addSubMenu(0, 1, 1, R.string.exit).setIcon(R.drawable.menu_exit);
			menu.addSubMenu(0, 2, 2, R.string.about).setIcon(R.drawable.menu_about);
			return super.onCreateOptionsMenu(menu);		
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == 1) {
				new LogoutDialog(LoginActivity.this).show();
			}
			else if (item.getItemId() == 2) {
				Intent intent = new Intent(LoginActivity.this,About.class);
				startActivity(intent);
			}
			return super.onOptionsItemSelected(item);		
		}
	
	
}
