package com.kavilee.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kavilee.ldsys.MyApp;
import com.kavilee.model.Order;

public class OrderDao {
	private SQLiteDatabase db;
	
	public OrderDao (Context context) {
		db = context.openOrCreateDatabase(MyApp.DBNAME, Context.MODE_PRIVATE,
				null);
		if (ExistTable(db, SchemeCreator.ORDERTNAME) == false) {
			db.execSQL(SchemeCreator.getOTableSQL());
		}
	}
	/**
	 * 关闭数据库
	 */
	public void Close() {
		if (db != null && db.isOpen() == true) {
			db.close();
		}
	}
    /**
     * 判断是否存在指定的表
     * 
     * @param db2
     * @param usertname
     * @return
     */
	private boolean ExistTable(SQLiteDatabase db2, String usertname) {
		// TODO Auto-generated method stub
		Cursor cursor = db2.query("sqlite_master", new String[] { "1" },
				"type=? and name=?", new String[] { "table", usertname }, null,
				null, null);
		try {
			return cursor.moveToFirst();
		} finally {
			cursor.close();
		}
	}
	/**
	 * 添加订单信息到数据库
	 * 
	 * @param model
	 * @return
	 */
	public boolean Insert(Order model){
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		long id = db.insert(SchemeCreator.ORDERTNAME, "", values);		
		return id != -1;		
	}
	/**
	 * 根据orderID修改订单信息
	 * 
	 * @param model
	 * @return
	 */
	public int Update(Order model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model,values);
		int count = db.update(SchemeCreator.ORDERTNAME, values, "orderID=?", new String[]{model.getOrderID().toString()});
		return count;	
	}
	
	public List<Order> getUserOrder(Integer userID){
		Cursor c = db.rawQuery("select * from orders where userID = ?", new String[]{userID.toString()});
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;
	}
	
	/**
	 * 所有未确认订单
	 * @return
	 */
	public List<Order> getUnidentified(){
		Cursor c = db.rawQuery("select * from orders where status = ?", new String[]{"0"});
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;
	}
	
	/**
	 * 所有已确认订单
	 * @return
	 */
	public List<Order> getIdentified(){
		Cursor c = db.rawQuery("select * from orders where status = ?", new String[]{"1"});
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;
	}
	/**
	 * 配送员配送中的订单
	 * @param userID
	 * @return
	 */
	public List<Order> getIncomplete(Integer userID) {
		Cursor c = db.rawQuery("select * from orders where staffID = ? and destatus = ?", new String[]{userID.toString(),"0"});
		System.out.println("是不是"+userID);
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;		
	}
	/**
	 * 配送员配送完成的订单
	 * @param userID
	 * @return
	 */
	public List<Order> getComplete(Integer userID) {
		Cursor c = db.rawQuery("select * from orders where staffID = ? and destatus = ?", new String[]{userID.toString(),"1"});
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;		
	}
	
	/**
	 * 获取所有配送中的订单
	 * @param userID
	 * @return
	 */
	public List<Order> getAllIncomplete() {
		Cursor c = db.rawQuery("select * from orders where destatus = ?", new String[]{"0"});
		List<Order> list = new ArrayList<Order>();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Order order = new Order();
			order.setOrderID(c.getInt(c.getColumnIndex("orderID")));
			order.setGoodsName(c.getString(c.getColumnIndex("goodsName")));
			order.setAddressee(c.getString(c.getColumnIndex("addressee")));
			order.setZip(c.getString(c.getColumnIndex("zip")));
			order.setUserName(c.getString(c.getColumnIndex("userName")));
			order.setUserID(c.getInt(c.getColumnIndex("userID")));
			order.setStaffID(c.getInt(c.getColumnIndex("staffID")));
			order.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			order.setOrderPrice(c.getString(c.getColumnIndex("orderPrice")));
			order.setDsPhonum(c.getString(c.getColumnIndex("dsPhonum")));
			order.setStaffName(c.getString(c.getColumnIndex("staffName")));
			order.setOrderLatE6(c.getLong(c.getColumnIndex("orderLat")));//收件地址纬度
			order.setOrderLngE6(c.getLong(c.getColumnIndex("orderLng")));//收件地址经度
			order.setOrderstartLatE6(c.getLong(c.getColumnIndex("orderstartLat")));//发件地址纬度
			order.setOrderstartLngE6(c.getLong(c.getColumnIndex("orderstartLng")));//发件地址经度
			list.add(order);
			c.moveToNext();
		}
		c.close();
		return list;
	}
	
	public boolean deleteOrder(Integer orderId){
		int result = db.delete("orders", "orderId=?", new String[]{orderId.toString()});
		if(result != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	private ContentValues InitialContentValues(Order model, ContentValues values) {
		// TODO Auto-generated method stub
		values.put("orderPrice",      model.getOrderPrice());
		values.put("goodsName",       model.getGoodsName());
		values.put("orderLat",        model.getOrderLatE6());
		values.put("orderLng",        model.getOrderLngE6());
		values.put("orderstartLat",   model.getOrderstartLatE6());
		values.put("orderstartLng",   model.getOrderstartLngE6());
		values.put("staffID",         model.getStaffID());
		values.put("staffName",       model.getStaffName());
		values.put("userID",          model.getUserID());
		values.put("userName",        model.getUserName());
		values.put("addressee",       model.getAddressee());
		values.put("zip",             model.getZip());
		values.put("userPhonum",      model.getUserPhonum());
		values.put("dsPhonum",        model.getDsPhonum());
		values.put("status",          model.getStatus());
		values.put("destatus",        model.getDestatus());
		return values;
	}

}
