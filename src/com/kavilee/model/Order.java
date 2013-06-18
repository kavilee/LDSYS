package com.kavilee.model;

public class Order {
	private Integer orderID;// 订单号
	private String orderPrice;// 订单价格
	private String goodsName;// 货物名称
	private Integer staffID;// 配送员的userID
	private Integer userID;// 用户的userID
	private String userName;// 用户的名字
	private String userPhonum;// 用户电话/收件人电话
	private String staffName;// 配送员的名字
	private String dsPhonum;// 配送员的电话
	private String addressee;// 收件人
	private String zip;// 邮编
	private Long orderLngE6;// 配送地点的经度
	private Long orderLatE6;// 配送地点的纬度
	private Long orderstartLngE6;// 配送地点的经度
	private Long orderstartLatE6;// 配送地点的纬度
	private int status;// 订单状态，0是默认的未确认状态，1是已确认
	private int destatus;// 订单状态，0是默认的配送中状态，1是配送完成状态

	public Integer getOrderID() {
		return orderID;
	}

	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getStaffID() {
		return staffID;
	}

	public void setStaffID(Integer staffID) {
		this.staffID = staffID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getOrderLngE6() {
		return orderLngE6;
	}

	public void setOrderLngE6(Long orderLngE6) {
		this.orderLngE6 = orderLngE6;
	}

	public Long getOrderLatE6() {
		return orderLatE6;
	}

	public void setOrderLatE6(Long orderLatE6) {
		this.orderLatE6 = orderLatE6;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddressee() {
		return addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getUserPhonum() {
		return userPhonum;
	}

	public void setUserPhonum(String userPhonum) {
		this.userPhonum = userPhonum;
	}

	public String getDsPhonum() {
		return dsPhonum;
	}

	public void setDsPhonum(String dsPhonum) {
		this.dsPhonum = dsPhonum;
	}

	public Long getOrderstartLngE6() {
		return orderstartLngE6;
	}

	public void setOrderstartLngE6(Long orderstartLngE6) {
		this.orderstartLngE6 = orderstartLngE6;
	}

	public Long getOrderstartLatE6() {
		return orderstartLatE6;
	}

	public void setOrderstartLatE6(Long orderstartLatE6) {
		this.orderstartLatE6 = orderstartLatE6;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDestatus() {
		return destatus;
	}

	public void setDestatus(int destatus) {
		this.destatus = destatus;
	}

}
