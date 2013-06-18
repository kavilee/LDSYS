package com.kavilee.detail;

import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DSDetail extends Activity{
	
	private TextView userID,userName,userPhonum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dsdetail);
		
		userID = (TextView)findViewById(R.id.textViewOID);
		userName = (TextView)findViewById(R.id.textViewUN);
		userPhonum = (TextView)findViewById(R.id.textViewPN);
		
		userID.setText(RunTime.adminClickedItemUser.getUserID().toString());
		userName.setText(RunTime.adminClickedItemUser.getUserName());
		userPhonum.setText(RunTime.adminClickedItemUser.getUserPhonum());
	}

}
