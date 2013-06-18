package com.kavilee.mapview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.kavilee.ldsys.MyApp;
import com.kavilee.ldsys.R;
import com.kavilee.ldsys.RunTime;
import com.kavilee.model.Order;

/**
 * 用于配送员界面面显示发货地与收货地两点间的线路
 */
public class LDline extends Activity {
	private MapView mMapView = null;
	private MapController mMapController = null;
	private MKOfflineMap mOffline = null;
	private MKSearch mMKSearch;
	private Order currentOrder;
	private MyApp app;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public NotifyLister mNotifyer = null;
	MyLocationOverlay myLocationOverlay = null;
	int index = 0;
	LocationData locData = null;
	MKPlanNode start;
	MKPlanNode end;

	private static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		currentOrder = RunTime.dsClickedItemOrder;
		app = (MyApp) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MyApp.strKey, new MyApp.MyGeneralListener());
		}
		setContentView(R.layout.layout_dsmapview);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMapController.animateTo(new GeoPoint(
						(int) (locData.latitude * 1e6),
						(int) (locData.longitude * 1e6)), mHandler
						.obtainMessage(1));
			}
		});
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setCenter(MyApp.point);
		setmOffline(new MKOfflineMap());
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		mMapView.displayZoomControls(true);
		mMapView.setDoubleClickZooming(true);

		initMKSearch();
		start = new MKPlanNode();
		start.pt = new GeoPoint(currentOrder.getOrderstartLatE6().intValue(),
				currentOrder.getOrderstartLngE6().intValue());
		end = new MKPlanNode();
		end.pt = new GeoPoint(currentOrder.getOrderLatE6().intValue(),
				currentOrder.getOrderLngE6().intValue());
		// 设置驾车路线搜索策略，时间优先、费用最少或距离最短
		mMKSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
		Toast.makeText(LDline.this, "路程最短路线", Toast.LENGTH_SHORT).show();
		mMKSearch.drivingSearch(null, start, null, end);

		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(5000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mMapView.setBuiltInZoomControls(true);
		myLocationOverlay = new MyLocationOverlay(mMapView);
		locData = new LocationData();
		myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		mMapView.refresh();
	}

	private void initMKSearch() {
		mMKSearch = new MKSearch();
		mMKSearch.init(app.mBMapManager, new MKSearchListener() {
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
			}

			public void onGetPoiResult(MKPoiResult res, int type, int error) {
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult result,
					int error) {
				if (result == null) {
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(LDline.this,
						mMapView); // 此处仅展示一个方案作为示例
				routeOverlay.setData(result.getPlan(0).getRoute(0));
				mMapView.getOverlays().add(routeOverlay);
				mMapView.refresh();
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}
		});
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);
			mMapView.refresh();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		menu.addSubMenu(0, 1, 1, R.string.TIME).setIcon(R.drawable.icon_time);//时间优先
		menu.addSubMenu(0, 2, 2, R.string.DIS).setIcon(R.drawable.icon_dis);//距离优先
		menu.addSubMenu(0, 3, 3, R.string.FEE).setIcon(R.drawable.icon_fee);//费用优先
		return super.onCreateOptionsMenu(menu);		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			//设置路线为时间优先
			mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
			Toast.makeText(LDline.this, "时间最短路线", Toast.LENGTH_SHORT).show();
		}
		else if (item.getItemId() == 2) {
			//设置路线为距离优先
			mMKSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
			Toast.makeText(LDline.this, "路程最短路线", Toast.LENGTH_SHORT).show();
		}
		else if (item.getItemId() == 3) {
			//设置路线为费用优先
			mMKSearch.setDrivingPolicy(MKSearch.ECAR_FEE_FIRST);
			Toast.makeText(LDline.this, "费用最少路线", Toast.LENGTH_SHORT).show();
		}
		mMKSearch.drivingSearch(null, start, null, end);
		return super.onOptionsItemSelected(item);		
	}

	public MKOfflineMap getmOffline() {
		return mOffline;
	}

	public void setmOffline(MKOfflineMap mOffline) {
		this.mOffline = mOffline;
	}
	@Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			LDline.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
    
}
