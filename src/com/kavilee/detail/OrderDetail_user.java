package com.kavilee.detail;

import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderDetail_user extends Activity{
	private TextView orderID,orderPrice,goodName,addressee,userPhonum,dsPhonum,dsName,dsID,address,zipCode,userID,userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_user);
		
		orderID = (TextView)findViewById(R.id.textViewOID);
		orderPrice = (TextView)findViewById(R.id.textViewOP);
		goodName = (TextView)findViewById(R.id.textViewGN);
		addressee = (TextView)findViewById(R.id.tvAddressee);
		userPhonum = (TextView)findViewById(R.id.TextViewUPN);
		dsPhonum = (TextView)findViewById(R.id.TextViewDSPN);
		dsName = (TextView)findViewById(R.id.textViewDSN);
		dsID = (TextView)findViewById(R.id.tvDSID);
		address = (TextView)findViewById(R.id.tvAddress);
		zipCode = (TextView)findViewById(R.id.textViewZC);
		userID = (TextView)findViewById(R.id.tvUID);
		userName = (TextView)findViewById(R.id.textViewUN);
		
		orderID.setText(RunTime.userClickedItemOrder.getOrderID().toString());
		goodName.setText(RunTime.userClickedItemOrder.getGoodsName());
		addressee.setText(RunTime.userClickedItemOrder.getAddressee());
		userPhonum.setText(RunTime.userClickedItemOrder.getUserPhonum());
		orderPrice.setText(RunTime.userClickedItemOrder.getOrderPrice()+"元");
		dsPhonum.setText(RunTime.userClickedItemOrder.getDsPhonum());
		dsName.setText(RunTime.userClickedItemOrder.getStaffName());
		dsID.setText(RunTime.userClickedItemOrder.getStaffID().toString());
		address.setText("经度:"+RunTime.userClickedItemOrder.getOrderLngE6().toString()+";纬度:"+RunTime.userClickedItemOrder.getOrderLatE6().toString());
		zipCode.setText(RunTime.userClickedItemOrder.getZip());
		userID.setText(RunTime.userClickedItemOrder.getUserID().toString());
		userName.setText(RunTime.userClickedItemOrder.getUserName());
	}

}
