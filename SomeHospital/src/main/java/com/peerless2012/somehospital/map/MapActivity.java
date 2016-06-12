package com.peerless2012.somehospital.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.peerless2012.somehospital.R;
import com.peerless2012.somehospital.base.BaseActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MapActivity extends BaseActivity
        implements LocationSource,AMapLocationListener,AMap.OnMapLoadedListener{

    private FloatingSearchView mFloatSearchView;

    private View contentPanel;

    private MapView mMapView;

    private AMap mAMap;

    private OnLocationChangedListener mListener;

    private AMapLocationClient mlocationClient;

    private AMapLocationClientOption mLocationOption;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView() {
        contentPanel = getView(R.id.contentPanel);
        mMapView = getView(R.id.map_view);
        mFloatSearchView = getView(R.id.floating_search_view);
        mMapView.onCreate(null);
        mAMap = mMapView.getMap();
        setUpMap();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.arrow));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setLocationSource(this);// 设置定位监听
        UiSettings uiSettings = mAMap.getUiSettings();
        // 缩放按钮位置，右下角或右侧中间，略单薄
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        // 设置默认定位按钮是否显示
        // 好坑，只能放到右上角，真是醉了
        uiSettings.setMyLocationButtonEnabled(false);
        uiSettings.setCompassEnabled(false);
        // aMap.setMyLocationType()
        mAMap.setOnMapLoadedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mAMap.setMyLocationEnabled(false);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context,MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
        }
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
//                mAMap.setMyLocationEnabled(false);
                mlocationClient.stopLocation();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void onMapLoaded() {
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    public void onBackPressed() {
        if (mFloatSearchView.isSearchBarFocused()){
            mFloatSearchView.clearSearchFocus();
        }else {
            super.onBackPressed();
        }
    }
}
