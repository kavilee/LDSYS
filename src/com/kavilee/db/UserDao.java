package com.kavilee.db;

import java.util.ArrayList;
import java.util.List;

import com.kavilee.ldsys.MyApp;
import com.kavilee.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private SQLiteDatabase db;

	public UserDao(Context context) {
		db = context.openOrCreateDatabase(MyApp.DBNAME, Context.MODE_PRIVATE,
				null);
		if (ExistTable(db, SchemeCreator.USERTNAME) == false) {
			db.execSQL(SchemeCreator.getUTableSQL());
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
	 * @param database
	 * @param tableName
	 * @return
	 */
	private static boolean ExistTable(SQLiteDatabase database, String tableName) {
		// TODO Auto-generated method stub
		Cursor cursor = database.query("sqlite_master", new String[] { "1" },
				"type=? and name=?", new String[] { "table", tableName }, null,
				null, null);
		try {
			return cursor.moveToFirst();
		} finally {
			cursor.close();
		}
	}

	/**
	 * 添加一行User数据
	 * 
	 * @param model
	 * @return
	 */
	public boolean Insert(User model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		long id = db.insert(SchemeCreator.USERTNAME, "", values);
		return id != -1;

	}

	/**
	 * 根据userID修改数据
	 * 
	 * @param model
	 * @return
	 */
	public int Update(User model) {
		ContentValues values = new ContentValues();
		values = InitialContentValues(model, values);
		int count = db.update(SchemeCreator.USERTNAME, values, "userID=?",
				new String[] { model.getUserID().toString() });
		return count;

	}
	/**
	 * 用户登录
	 * @param user 传进来用户名和密码
	 * @return 如果不存在，就返回null
	 */
	public User find(String userName, String userPsw){
		Cursor c = db.rawQuery("select * from user where userName = ? and userPsw = ?", new String[]{userName, userPsw});
		if(c.moveToFirst()){
			User user = new User();
			user.setUserID(c.getInt(c.getColumnIndex("userID")));
			user.setUserName(userName);
			user.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			user.setUserPsw(userPsw);
			user.setUserType(c.getInt(c.getColumnIndex("userType")));
			return user;
		}
		else{
			c.close();
			return null;
		}
	}
	/**
	 * 注册时查询用户是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public User search(String userName) {
		Cursor cursor = db.rawQuery("select * from user where userName = ?", new String[]{userName});
		if (cursor.moveToFirst()) {
			User user = new User();
			user.setUserName(userName);
			return user;
		}
		else {
			cursor.close();
			return null;
		}	
	}
	
	/**
	 * 所有配送员
	 * @return
	 */
	public List<User> getAllStaff(){
		Cursor c = db.rawQuery("select * from user where userType = ?", new String[]{"1"});
		List<User> list = new ArrayList<User>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			User user = new User();
			user.setUserID(c.getInt(c.getColumnIndex("userID")));
			user.setUserName(c.getString(c.getColumnIndex("userName")));
			user.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			user.setUserPsw(c.getString(c.getColumnIndex("userPsw")));
			user.setUserType(c.getInt(c.getColumnIndex("userType")));
			list.add(user);
			c.moveToNext();
		}
		c.close();
		return list;
	}
	/**
	 * 获得所有用户
	 * @param model
	 * @param values
	 * @return
	 */
	public List<User> getAllUser(){
		Cursor c = db.rawQuery("select * from user where userType = ?", new String[]{"2"});
		List<User> list = new ArrayList<User>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			User user = new User();
			user.setUserID(c.getInt(c.getColumnIndex("userID")));
			user.setUserName(c.getString(c.getColumnIndex("userName")));
			user.setUserPhonum(c.getString(c.getColumnIndex("userPhonum")));
			user.setUserPsw(c.getString(c.getColumnIndex("userPsw")));
			user.setUserType(c.getInt(c.getColumnIndex("userType")));
			list.add(user);
			c.moveToNext();
		}
		c.close();
		return list;
	}

	private ContentValues InitialContentValues(User model, ContentValues values) {
		// TODO Auto-generated method stub
		values.put("userName", model.getUserName());
		values.put("userPsw", model.getUserPsw());
		values.put("userPhonum", model.getUserPhonum());
		values.put("userType", model.getUserType());
		return values;
	}

}
