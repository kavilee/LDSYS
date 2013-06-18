package com.kavilee.detail;

import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;
import com.kavilee.mapview.LDline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class OrderDetail_ds_complete extends Activity{
	
	private TextView orderID,orderPrice,goodName,addressee,userPhonum,address,zipCode;
	private Button checkButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_ds1);
		
		orderID = (TextView)findViewById(R.id.textViewOID);
		orderPrice = (TextView)findViewById(R.id.textViewOP);
		goodName = (TextView)findViewById(R.id.textViewGN);
		addressee = (TextView)findViewById(R.id.tvAddressee);
		userPhonum = (TextView)findViewById(R.id.TextViewUPN);
		address = (TextView)findViewById(R.id.tvAddress);
		zipCode = (TextView)findViewById(R.id.textViewZC);
		
		checkButton = (Button)findViewById(R.id.btn_check);
		
		orderID.setText(RunTime.dsClickedItemOrder.getOrderID().toString());
		goodName.setText(RunTime.dsClickedItemOrder.getGoodsName());
		addressee.setText(RunTime.dsClickedItemOrder.getAddressee());
		userPhonum.setText(RunTime.dsClickedItemOrder.getUserPhonum());
		orderPrice.setText(RunTime.dsClickedItemOrder.getOrderPrice()+"元");
		address.setText("经度:"+RunTime.dsClickedItemOrder.getOrderLngE6().toString()+";纬度:"+RunTime.dsClickedItemOrder.getOrderLatE6().toString());
		zipCode.setText(RunTime.dsClickedItemOrder.getZip());
		
		checkButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 根据RunTime.dsClickedItemOrder传进来的收件地址和发件地址在地图上显示路径
				startActivity(new Intent(OrderDetail_ds_complete.this, LDline.class));
			}
		});
	}

}