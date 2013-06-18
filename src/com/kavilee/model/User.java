package com.kavilee.model;

public class User {
	private Integer userID;
	private String userName;
	private String userPsw;
	private String userPhonum;
	private int userType;// 账号类型，0：管理员，1：配送员，2：普通用户

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhonum() {
		return userPhonum;
	}

	public void setUserPhonum(String userPhonum) {
		this.userPhonum = userPhonum;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

}
