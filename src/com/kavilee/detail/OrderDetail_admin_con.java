package com.kavilee.detail;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kavilee.db.OrderDao;
import com.kavilee.db.UserDao;
import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;
import com.kavilee.ldsys.ShowDialog;
import com.kavilee.mapview.ChooseAddressActivity;
import com.kavilee.model.Order;
import com.kavilee.model.User;

public class OrderDetail_admin_con extends Activity{
	public static final int REQUEST_CODE_CHOOSE_ADDRESS = 101;
	public static final String Bundle_Key_lng = "Bundle_Key_lng";
	public static final String Bundle_Key_lat = "Bunle_Key_lat";
	private EditText myEditText;
	private Spinner mySpinner;
	private RelativeLayout myRelativeLayout;
	private Button myButton;
	private Order currentOrder;
	private List<User> staffList;
	private TextView orderID,goodsName,userID,userName,addressee,zipCode,userPhonum,dsPhonum,address,myTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail_admin_con);
		
		myEditText = (EditText)findViewById(R.id.myEditText);
		mySpinner = (Spinner)findViewById(R.id.mySpinner);
		myRelativeLayout = (RelativeLayout)findViewById(R.id.bt_choose);
		myButton = (Button)findViewById(R.id.btn_confirm);
		myTextView = (TextView)findViewById(R.id.textView01);
		
		orderID = (TextView)findViewById(R.id.textViewOID);
		goodsName = (TextView)findViewById(R.id.textViewGN);
		userID = (TextView)findViewById(R.id.tvUID);
		userName = (TextView)findViewById(R.id.textViewUN);
		addressee = (TextView)findViewById(R.id.tvAddressee);
		address = (TextView)findViewById(R.id.tvAddress);
		zipCode = (TextView)findViewById(R.id.textViewZC);
		userPhonum = (TextView)findViewById(R.id.TextViewUPN);
		dsPhonum = (TextView)findViewById(R.id.TextViewDSPN);
		
		orderID.setText(RunTime.adminClickedItemOrder.getOrderID().toString());
		goodsName.setText(RunTime.adminClickedItemOrder.getGoodsName());
		userID.setText(RunTime.adminClickedItemOrder.getUserID().toString());
		userName.setText(RunTime.adminClickedItemOrder.getUserName());
		addressee.setText(RunTime.adminClickedItemOrder.getAddressee());
		address.setText("地址[经度:"+RunTime.adminClickedItemOrder.getOrderLngE6().toString()+";纬度:"+
				RunTime.adminClickedItemOrder.getOrderLatE6().toString()+"]");
		zipCode.setText(RunTime.adminClickedItemOrder.getZip());
		userPhonum.setText(RunTime.adminClickedItemOrder.getUserPhonum());
		orderID.setText(RunTime.adminClickedItemOrder.getOrderID().toString());
		//dsPhonum.setText(currentOrder.getDsPhonum());
				
		myButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Validator()) {
					if (confirm()) {
						showDialog("订单已成功确认！");
						OrderDetail_admin_con.this.finish();
					}
				}
			}

		});
		UserDao userDao = new UserDao(this);
		staffList = userDao.getAllStaff();
		String[] userName = new String[staffList.size()];
		for (int i = 0; i < staffList.size(); i++) {
			userName[i] = staffList.get(i).getUserName();
		}
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userName);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				
//		ArrayAdapter<CharSequence>adapter = 
//				ArrayAdapter.createFromResource(this, R.array.role_array_re, android.R.layout.simple_spinner_dropdown_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mySpinner.setAdapter(adapter2);
		mySpinner.setPromptId(R.string.SEL1);
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				//将配送员ID放进currentOrder中
				currentOrder.setStaffID(staffList.get(arg2).getUserID());
				currentOrder.setStaffName(staffList.get(arg2).getUserName());
				currentOrder.setDsPhonum(staffList.get(arg2).getUserPhonum());
				dsPhonum.setText(currentOrder.getDsPhonum());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		myRelativeLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 打开地图选择地址
				startActivityForResult(new Intent(OrderDetail_admin_con.this,ChooseAddressActivity.class), REQUEST_CODE_CHOOSE_ADDRESS);
			}
		});
		
		currentOrder = RunTime.adminClickedItemOrder;
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

	private boolean confirm() {
		currentOrder.setOrderPrice(myEditText.getText().toString());
		//修改状态为已确认
		currentOrder.setStatus(1);
		OrderDao orderDao = new OrderDao(this);
		orderDao.Update(currentOrder);
		return true;
	}

	private boolean Validator() {
		// TODO Auto-generated method stub
		String orderPrice = myEditText.getText().toString();
		if (orderPrice.equals("")) {
			new ShowDialog(OrderDetail_admin_con.this).show("订单价格是必填项，不能为空哦，请重新输入！");
			return false;
		}
		if(currentOrder.getStaffID() == null || currentOrder.getStaffID() == 0){
			new ShowDialog(OrderDetail_admin_con.this).show("您还没有选择配送员哦，请指定配送员！");
			return false;
		}
		if (myTextView.getText().equals("去选择")) {
			new ShowDialog(OrderDetail_admin_con.this).show("您还没有发货地址哦，请选择地址！");
			return false;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CHOOSE_ADDRESS) {
			if (resultCode == RESULT_OK) {
				// 这里接收地图页面结束时传过来的数据
				myTextView.setText("地址 " + "(经度:"
						+ data.getIntExtra(Bundle_Key_lng, 0) + "纬度:"
						+ ";" + data.getIntExtra(Bundle_Key_lat, 0) + ")");
				currentOrder.setOrderstartLngE6((long) data.getIntExtra(Bundle_Key_lng, 0));
				currentOrder.setOrderstartLatE6((long) data.getIntExtra(Bundle_Key_lat, 0));
			}
		}
	}

}
