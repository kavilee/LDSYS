package com.kavilee.ldsys;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MyApp extends Application {

	public static final String DBNAME = "LDSYS.db";
	private static List<Activity> allActivities;
	
	private static MyApp mInstance = null;
	public boolean m_bKeyRight = true;
	public BMapManager mBMapManager = null;
	public static final String strKey = "810DCF1272DEC1D79218DE8E74D377EF841C13FA";
	//设置起点为广州市
	public static final GeoPoint point = new GeoPoint((int)(23.053*1e6),(int)(113.387*1e6));

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initEngineManager(this);
		allActivities = new ArrayList<Activity>();
	}

	@Override
	// 建议在您app的退出之前调用mapadpi的destroy()函数，避免重复初始化带来的时间消耗
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onTerminate();
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
			Toast.makeText(MyApp.getInstance().getApplicationContext(),
					"BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
		}
	}

	public static MyApp getInstance() {
		return mInstance;
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(MyApp.getInstance().getApplicationContext(),
						"您的网络出错啦！", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(MyApp.getInstance().getApplicationContext(),
						"输入正确的检索条件！", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(MyApp.getInstance().getApplicationContext(),
						"请在 MyApp.java文件输入正确的授权Key！",
						Toast.LENGTH_LONG).show();
				MyApp.getInstance().m_bKeyRight = false;
			}
		}
	}
	//把生成的activity存入表中
	public static void add(Activity ac) {
		allActivities.add(ac);
	}	
	/**
	 * 退出系统
	 */
	public static void exit() {
		for (Activity ac : allActivities) {
			ac.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
