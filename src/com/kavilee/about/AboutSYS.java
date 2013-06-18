package com.kavilee.about;

import com.kavilee.ldsys.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutSYS extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//设置窗口没有标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutsys);
	}
}
