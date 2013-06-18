package com.kavilee.ldsys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.kavilee.about.About;
import com.kavilee.db.UserDao;
import com.kavilee.ldsys.R;
import com.kavilee.model.User;

public class RegisterActivity extends Activity{
	
	private EditText userREditText,pswREditText,phoEditText;
	private Button registerButton;
	private Spinner selectSpinner;
    private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置窗口没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//得到控件
		userREditText = (EditText)findViewById(R.id.userREditText);
		pswREditText = (EditText)findViewById(R.id.pswREditText);
		phoEditText = (EditText)findViewById(R.id.phoEditText);
		registerButton = (Button)findViewById(R.id.registerBtn);
		selectSpinner = (Spinner)findViewById(R.id.regspinner);
		
		user = new User();
		//为注册按钮添加监听器
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Validator()) {
					if (Register()) {
						showDialog("您已成功注册，立即返回登录？");
						//清空输入框的内容
						userREditText.setText("");
				        phoEditText.setText("");
				        pswREditText.setText("");
				        userREditText.requestFocus();
					}else {
						new ShowDialog(RegisterActivity.this).show("用户名已经存在，请重新输入！");
						userREditText.setText("");
						userREditText.requestFocus();
					}
					
				}
			}
		});
		//为下拉列表定义一个适配器，这里用到的事string.xml中的角色数组
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array_re, android.R.layout.simple_spinner_item);
		//为适配器设置下拉菜单的样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
		//将适配器添加到spinner上
		selectSpinner.setAdapter(adapter);
		selectSpinner.setPromptId(R.string.SEL2);//设置下拉菜单标题
		
		//
		selectSpinner.setOnItemSelectedListener(new  Spinner.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view,
					int position, long id) {
				//设置userType用户类型
				int userType = position + 1;
				user.setUserType(userType);
				System.out.println("userType---->"+userType);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
		private boolean Register() {
			String userName = userREditText.getText().toString();
			User result = Query(userName);
			if (result!=null) {//判断用户是否存在
				return false;
			}else {
				UserDao userDao = new UserDao(this);
				//TODO 这里需要增加几个输入框，然后填输入框里的数据
				user.setUserName(userREditText.getText().toString());
				user.setUserPhonum(phoEditText.getText().toString());
				user.setUserPsw(pswREditText.getText().toString());
				//user.setUserType(2);
				return userDao.Insert(user);
			}		
		}
		private User Query(String userName) {
			//从数据库中查找有没有这个user，如果没有就会返回null
			UserDao userDao = new UserDao(this);
			return userDao.search(userName);
		}
        //判断输入是否合法
		private boolean Validator() {
			String username = userREditText.getText().toString();
			if (username.equals("")) {
				new ShowDialog(RegisterActivity.this).show("登录名是必填项，不能为空哦，请重新输入！");
				return false;
			}
			String psw = pswREditText.getText().toString();
			if (psw.equals("")) {
				new ShowDialog(RegisterActivity.this).show("密码是必填项，不能为空哦，请重新输入！");
				return false;
			}
			String pho = phoEditText.getText().toString();
			if (pho.equals("")) {
				new ShowDialog(RegisterActivity.this).show("电话号码是必填项，不能为空哦，请重新输入！");
				return false;
			}
			return true;
			}

		//注册成功后弹出提示框
		private void showDialog(String msg) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(msg)
		       .setCancelable(true).setTitle("恭喜")
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
		        	   startActivity(intent);
		        	   RegisterActivity.this.finish();
		           }
		           
		       });
		AlertDialog alert = builder.create();
		alert.show();
		}
		//设置用户注册界面的菜单
		public boolean onCreateOptionsMenu(Menu menu){
			menu.addSubMenu(0, 1, 1, R.string.exit).setIcon(R.drawable.menu_exit);
			menu.addSubMenu(0, 2, 2, R.string.about).setIcon(R.drawable.menu_about);
			return super.onCreateOptionsMenu(menu);		
		}
		public boolean onOptionsItemSelected(MenuItem item) {
			if (item.getItemId() == 1) {
				new LogoutDialog(RegisterActivity.this).show();
			}
			else if (item.getItemId() == 2) {
				Intent intent = new Intent(RegisterActivity.this,About.class);
				startActivity(intent);
			}
			return super.onOptionsItemSelected(item);		
		}
		
		//返回键弹出对话框
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
				RegisterActivity.this.finish();
			}
			return super.onKeyDown(keyCode, event);
		}

}
