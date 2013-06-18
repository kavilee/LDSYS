package com.kavilee.db;

public class SchemeCreator {
	public static final String USERTNAME = "user";
	public static final String ORDERTNAME = "orders";

	public static String getUTableSQL() {
		StringBuilder sb = new StringBuilder();
		// user表
		sb.append("create table user");
		sb.append(" (");
		sb.append("userID          integer primary key autoincrement,");
		sb.append("userType        integer,");
		sb.append("userName        text,");
		sb.append("userPsw         text,");
		sb.append("userPhonum      text");
		sb.append("); ");

		return sb.toString();
	}

	public static String getOTableSQL() {
		StringBuilder sb = new StringBuilder();
		// order表
		sb.append("create table orders");
		sb.append(" (");
		sb.append("orderID          integer primary key autoincrement,");
		sb.append("orderPrice       text,");
		sb.append("goodsName        text,");
		sb.append("staffID          integer,");
		sb.append("userID           integer,");
		sb.append("userName         text,");
		sb.append("userPhonum       text,");
		sb.append("staffName        text,");
		sb.append("dsPhonum         text,");
		sb.append("orderLng         long,");
		sb.append("orderLat         long,");
		sb.append("orderstartLng    long,");
		sb.append("orderstartLat    long,");
		sb.append("addressee        text,");
		sb.append("status           integer,");//订单确定状态
		sb.append("destatus         integer,");//配送状态
		sb.append("zip              text");
		sb.append("); ");

		return sb.toString();

	}

}
