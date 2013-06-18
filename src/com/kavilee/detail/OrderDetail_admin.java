package com.kavilee.detail;

import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;
import com.kavilee.mapview.AdminLine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OrderDetail_admin extends Activity{
	
	private TextView orderID,orderPrice,goodsName,addressee,userPhonum,dsPhonum,
	dsName,dsID,address,saddress,zipCode,userID,userName;
	private Button myButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_admin);
		
		myButton = (Button)findViewById(R.id.btn_check);
		
		orderID = (TextView)findViewById(R.id.textViewOID);
		orderPrice = (TextView)findViewById(R.id.textViewOP);
		goodsName = (TextView)findViewById(R.id.textViewGN);
		addressee = (TextView)findViewById(R.id.tvAddressee);
		address = (TextView)findViewById(R.id.tvAddress);
		saddress = (TextView)findViewById(R.id.tvsAddress);
		userID = (TextView)findViewById(R.id.tvUID);
		userName = (TextView)findViewById(R.id.textViewUN);
		dsID = (TextView)findViewById(R.id.tvDSID);
		dsName = (TextView)findViewById(R.id.textViewDSN);
		dsPhonum = (TextView)findViewById(R.id.TextViewDSPN);
		zipCode = (TextView)findViewById(R.id.textViewZC);
		userPhonum = (TextView)findViewById(R.id.TextViewUPN);
		
		orderID.setText(RunTime.adminClickedItemOrder.getOrderID().toString());
		orderPrice.setText(RunTime.adminClickedItemOrder.getOrderPrice());
		goodsName.setText(RunTime.adminClickedItemOrder.getGoodsName());
		addressee.setText(RunTime.adminClickedItemOrder.getAddressee());
		userPhonum.setText(RunTime.adminClickedItemOrder.getUserPhonum());
		userID.setText(RunTime.adminClickedItemOrder.getUserID().toString());
		userName.setText(RunTime.adminClickedItemOrder.getUserName());
		dsName.setText(RunTime.adminClickedItemOrder.getStaffName());
		dsID.setText(RunTime.adminClickedItemOrder.getStaffID().toString());
		dsPhonum.setText(RunTime.adminClickedItemOrder.getDsPhonum());
		zipCode.setText(RunTime.adminClickedItemOrder.getZip());
		address.setText("地址[经度:"+RunTime.adminClickedItemOrder.getOrderLngE6().toString()+";纬度:"+
		RunTime.adminClickedItemOrder.getOrderLatE6().toString()+"]");
		saddress.setText("地址[经度:"+RunTime.adminClickedItemOrder.getOrderstartLngE6().toString()+";纬度:"+
		RunTime.adminClickedItemOrder.getOrderstartLatE6().toString()+"]");
		
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 根据RunTime.adminClickedItemOrder传进来的收件地址和发件地址在地图上显示路径
				startActivity(new Intent(OrderDetail_admin.this, AdminLine.class));
			}
		});
	}

}
