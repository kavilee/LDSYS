package com.kavilee.mapview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MKOfflineMap;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.kavilee.ldsys.MyApp;
import com.kavilee.ldsys.R;
import com.kavilee.ldsys.UserLoginSuccessUI;

public class ChooseAddressActivity extends Activity {

	private MapView mMapView = null;
	private MapController mMapController = null;
	private MKOfflineMap mOffline = null;
	MyOverlay overlay;
	

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApp app = (MyApp) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(MyApp.strKey, new MyApp.MyGeneralListener());
		}
		setContentView(R.layout.layout_choose_addredd_activity);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
		mMapView.getController().setCenter(MyApp.point);
		setmOffline(new MKOfflineMap());
		mMapController.enableClick(true);
		mMapController.setZoom(12);
		mMapView.displayZoomControls(true);
		mMapView.setDoubleClickZooming(true);
		mMapView.regMapViewListener(MyApp.getInstance().mBMapManager, mapViewListener); 
		overlay = new MyOverlay(getResources().getDrawable(R.drawable.icon_mark_point), mMapView);
	}

	public MKOfflineMap getmOffline() {
		return mOffline;
	}

	public void setmOffline(MKOfflineMap mOffline) {
		this.mOffline = mOffline;
	}

	MKMapViewListener mapViewListener = new MKMapViewListener() {

		@Override
		public void onMapMoveFinish() {
			// 此处可以实现地图移动完成事件的状态监听
		}

		@Override
		public void onClickMapPoi(MapPoi arg0) {
			// 此处可实现地图点击事件的监听
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(overlay);
			mMapView.refresh();
			
			OverlayItem item1 = new OverlayItem(arg0.geoPt,"item1","item1");
			overlay.removeAll();
			overlay.addItem(item1);
			mMapView.refresh();
		}

		@Override
		public void onGetCurrentMap(Bitmap arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMapAnimationFinish() {
			// TODO Auto-generated method stub

		}
		
	};

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
			// 在此处理MapView的点击事件，当返回 true时
//			super.onTap(pt, mapView);
			OverlayItem item1 = new OverlayItem(pt,"item1","item1");
			overlay.removeAll();
			overlay.addItem(item1);
			mMapView.refresh();
			
			AlertDialog dialog;
			Builder builder = new Builder(ChooseAddressActivity.this);
			builder.setTitle("确认");
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent intent = new Intent();
					intent.putExtra(UserLoginSuccessUI.Bundle_Key_lng, pt.getLongitudeE6());
					intent.putExtra(UserLoginSuccessUI.Bundle_Key_lat, pt.getLatitudeE6());
					setResult(RESULT_OK, intent);
					ChooseAddressActivity.this.finish();
				}
			});
			builder.setMessage("是否要选择当前位置？");
			dialog = builder.create();
			dialog.show();
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
			ChooseAddressActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
