package com.peerless2012.somehospital.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorCreator;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.peerless2012.somehospital.R;
import com.peerless2012.somehospital.base.BaseActivity;
import com.peerless2012.somehospital.data.bean.Geo;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import com.peerless2012.somehospital.data.bean.HospitalSearchSuggestion;
import com.peerless2012.somehospital.utils.GeoGenerateUtils;
import com.peerless2012.somehospital.widget.MapControl;
import java.util.ArrayList;
import java.util.List;
/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/6/13 10:24
* @Version V1.0
* @Description:
*/
public class MapActivity extends BaseActivity<MapContract.MapView,MapContract.MapPresenter>
        implements LocationSource,AMapLocationListener,AMap.OnMapLoadedListener
                    ,MapContract.MapView,AMap.OnMarkerClickListener{

    private static final String TAG = "MapActivity";
    private FloatingSearchView mFloatSearchView;

    private View contentPanel;

    private MapView mMapView;

    private AMap mAMap;

    private OnLocationChangedListener mListener;

    private AMapLocationClient mlocationClient;

    private AMapLocationClientOption mLocationOption;


    private MapControl mMapControl;

    private HospitalInfoWindowAdapter mInfoWindowAdapter;

    private LatLng mLatLng;

    @Override
    public MapContract.MapView getPresenterView() {
        return this;
    }

    @Override
    public MapContract.MapPresenter getPresenter() {
        return new MapPresenter(this);
    }

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
        mMapControl = getView(R.id.map_control);
        setUpMap();
    }

    @Override
    protected void initListener() {
        mFloatSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction() {

            }
        });

        mFloatSearchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });

        mFloatSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (newQuery.length() <2) return;
                mPresenter.searchKeyWord(newQuery);
            }
        });

        mFloatSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                generateGeo();
            }
        });
    }

    @Override
    protected void initData() {
        mInfoWindowAdapter = new HospitalInfoWindowAdapter();
        mAMap.setInfoWindowAdapter(mInfoWindowAdapter);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.person));// 设置小蓝点的图标
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
        mAMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else if (id == R.id.action_generate_data){
//            generateGeo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateGeo() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                GeoGenerateUtils utils = new GeoGenerateUtils();
                utils.generateGeo(MapActivity.this);
            }
        }.start();
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
        mMapControl.detachMap();
        mlocationClient.stopLocation();
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
            if (aMapLocation.getErrorCode() == 0) {
                mPresenter.initData(aMapLocation.getCity());
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
//                mAMap.setMyLocationEnabled(false);
                mLatLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
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
        mMapControl.attachMap(mAMap);
    }

    @Override
    public void onBackPressed() {
        if (mFloatSearchView.isSearchBarFocused()){
            mFloatSearchView.clearSearchFocus();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDataLoaded(List<HospitalInfo> hospitalInfos) {
        mAMap.clear();

        initCurrentLocation();

        if (hospitalInfos != null){
            int size = hospitalInfos.size();
            for (int i = 0; i < size; i++) {
                HospitalInfo hospitalInfo = hospitalInfos.get(i);
//                Bitmap hospitalBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.hospital);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.hospital);
                MarkerOptions markerOptions = new MarkerOptions();
                Geo geo = hospitalInfo.getGeo();
                markerOptions.position(new LatLng(geo.getLatitude(),geo.getLongitude()));
                markerOptions.icon(bitmapDescriptor);
                markerOptions.snippet("");// 否则不显示InfoWindow
                Marker marker = mAMap.addMarker(markerOptions);
                marker.setObject(hospitalInfo);
            }
        }else {
            // 数据为空
        }

    }

    private void initCurrentLocation(){
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.person);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(bitmapDescriptor);
        Marker marker = mAMap.addMarker(markerOptions);
        marker.setObject(null);
    }

    @Override
    public void onKeyWordSearched(List<HospitalInfo> hospitalInfos) {
        if (hospitalInfos == null) {
            mFloatSearchView.clearSuggestions();
        }else {
            HospitalSearchSuggestion suggestion;
            List<HospitalSearchSuggestion> hospitalSearchSuggestions = new ArrayList<HospitalSearchSuggestion>();
            for (int i = 0; i < hospitalInfos.size(); i++) {
                suggestion = new HospitalSearchSuggestion(hospitalInfos.get(i));
                hospitalSearchSuggestions.add(suggestion);
            }
            mFloatSearchView.swapSuggestions(hospitalSearchSuggestions);
        }
    }

    @Override
    public void setPresenter(MapContract.MapPresenter presenter) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        HospitalInfo hospitalInfo = (HospitalInfo) marker.getObject();
        if (hospitalInfo == null){
            return true;
        }else {
            marker.showInfoWindow();
            return false;
        }
    }


    class HospitalInfoWindowAdapter implements AMap.InfoWindowAdapter{

        @Override
        public View getInfoWindow(Marker marker) {
            View infoWindow = getLayoutInflater().inflate(R.layout.view_infowindow, mMapView, false);
            HospitalInfo hospitalInfo = (HospitalInfo) marker.getObject();
            ((TextView)infoWindow.findViewById(R.id.hospital_name)).setText(hospitalInfo.getName());
            ((TextView)infoWindow.findViewById(R.id.hospital_address)).setText(hospitalInfo.getAddress());
            String[] proofs = hospitalInfo.getProofs();
            StringBuilder proofBuilder = null;
            if (proofs != null && proofs.length > 0){
                proofBuilder = new StringBuilder();
                for (int i = 0; i < proofs.length; i++) {
                    proofBuilder.append(proofs[i]);
                    if (i < proofs.length -1) proofBuilder.append("\n");
                }
            }else {
                proofBuilder = new StringBuilder("无");
            }
            ((TextView)infoWindow.findViewById(R.id.hospital_pro)).setText(Html.fromHtml(proofBuilder.toString()));
            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}
