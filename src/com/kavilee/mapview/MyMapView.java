package com.kavilee.mapview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.kavilee.db.OrderDao;
import com.kavilee.ldsys.MyApp;
import com.kavilee.ldsys.R;
import com.kavilee.model.Order;
/**
 * 用于管理员界面显示的查看地图
 */
public class MyMapView extends Activity{

	private MapView mMapView = null;
	private MapController mMapController = null;
	private MKOfflineMap mOffline = null;
	MyOverlay myOverlay;
	OrderDao orderDao;
	List<Order> allOrder;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApp app = (MyApp) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MyApp.strKey, new MyApp.MyGeneralListener());
		}
		setContentView(R.layout.layout_mymapview);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setCenter(MyApp.point);
		setmOffline(new MKOfflineMap());
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		mMapView.displayZoomControls(true);
		mMapView.setDoubleClickZooming(true);
		myOverlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_mark_point), mMapView);
		
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(myOverlay);
		mMapView.refresh();
		
		orderDao = new OrderDao(this);
		allOrder = orderDao.getAllIncomplete();
		
		initStartOverlay();
		initEndOverlay();
		initReceiveOverlay();
		initSendOverlay();
	}
	//标记发货点的位置
	private void initStartOverlay(){
		List<OverlayItem> itemList = new ArrayList<OverlayItem>();
		for (int i = 0; i < allOrder.size(); i++) {
			Order item = allOrder.get(i);
			GeoPoint point = new GeoPoint(item.getOrderstartLatE6().intValue(), item.getOrderstartLngE6().intValue());
			OverlayItem item1 = new OverlayItem(point,"start","start");
			item1.setMarker(getResources().getDrawable(R.drawable.icon_mark1));
			itemList.add(item1);
		}
		myOverlay.addItem(itemList);
		mMapView.refresh();
	}
	//标记收货点的位置
	private void initEndOverlay(){
		List<OverlayItem> itemList = new ArrayList<OverlayItem>();
		for (int i = 0; i < allOrder.size(); i++) {
			Order item = allOrder.get(i);
			GeoPoint point = new GeoPoint(item.getOrderLatE6().intValue(), item.getOrderLngE6().intValue());
			OverlayItem item1 = new OverlayItem(point,"end","end");
			item1.setMarker(getResources().getDrawable(R.drawable.icon_mark2));
			itemList.add(item1);
		}
		myOverlay.addItem(itemList);
		mMapView.refresh();
	}
	//计算发货地点的平均值，并在地图上显示
	private void initReceiveOverlay(){
		List<Long> latList = new ArrayList<Long>();
		List<Long> lngList = new ArrayList<Long>();
		for (int i = 0; i < allOrder.size(); i++) {
			Order item = allOrder.get(i);
			latList.add(item.getOrderstartLatE6());
			lngList.add(item.getOrderstartLngE6());
		}
		Long averLat = getAverage(latList);
		Long averLng = getAverage(lngList);
		GeoPoint point = new GeoPoint(averLat.intValue(), averLng.intValue());
		OverlayItem item1 = new OverlayItem(point,"receive","receive");
		item1.setMarker(getResources().getDrawable(R.drawable.icon_home_send));
		myOverlay.addItem(item1);
		mMapView.refresh();
	}
	//计算收货地点的平均值，并在地图上显示
	private void initSendOverlay(){
		List<Long> latList = new ArrayList<Long>();
		List<Long> lngList = new ArrayList<Long>();
		for (int i = 0; i < allOrder.size(); i++) {
			Order item = allOrder.get(i);
			latList.add(item.getOrderLatE6());
			lngList.add(item.getOrderLngE6());
		}
		Long averLat = getAverage(latList);
		Long averLng = getAverage(lngList);
		GeoPoint point = new GeoPoint(averLat.intValue(), averLng.intValue());
		OverlayItem item1 = new OverlayItem(point,"send","send");
		item1.setMarker(getResources().getDrawable(R.drawable.icon_home_receive));
		myOverlay.addItem(item1);
		mMapView.refresh();
	}
	//计算平均值
	private Long getAverage(List<Long> list){
		Long total = 0L;
		for (int i = 0; i < list.size(); i++) {
			total += list.get(i);
		}
		Double average = total.doubleValue()/list.size();
		return average.longValue();
	}
	

	public MKOfflineMap getmOffline() {
		return mOffline;
	}
	public void setmOffline(MKOfflineMap mOffline) {
		this.mOffline = mOffline;
	}


	class MyOverlay extends ItemizedOverlay<OverlayItem> {
		
		// 用MapView构造ItemizedOverlay
		public MyOverlay(Drawable marker, MapView mapView) {
			super(marker, mapView);
		}
		protected boolean onTap(final int index) {
			System.out.println("item onTap111 ");
			return true;
		}

		public boolean onTap(final GeoPoint pt, MapView mapView) {
			System.out.println("item onTap2222 ");

			return true;
		}
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
			MyMapView.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
    
}
