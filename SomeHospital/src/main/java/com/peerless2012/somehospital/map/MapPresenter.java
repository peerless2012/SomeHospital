package com.peerless2012.somehospital.map;

import android.content.Context;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import com.peerless2012.somehospital.data.source.HospitalDataSource;
import com.peerless2012.somehospital.data.source.HospitalRepository;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 10:33
 * @Version V1.0
 * @Description : 地图presenter
 */
public class MapPresenter implements MapContract.MapPresenter{

    private MapContract.MapView mMapView;

    private HospitalRepository mHospitalRepository;

    public MapPresenter(Context context){
        mHospitalRepository = HospitalRepository.getInstance(context);
    }

    @Override
    public void initData(String cityName) {
        mHospitalRepository.loadHospitalsWithCityName(cityName,new HospitalDataSource.SimpleCallBack<List<HospitalInfo>>() {
            @Override
            public void onSuccess(List<HospitalInfo> hospitalInfos) {
                if (mMapView != null) mMapView.onDataLoaded(hospitalInfos);
            }

            @Override
            public void onFaild(int errorCode, String errorMsg) {
                if (mMapView != null) mMapView.onDataLoaded(null);
            }
        });
    }

    @Override
    public void searchKeyWord(final String keyword) {
        mHospitalRepository.loadHospitalsWithKeyWord(keyword, new HospitalDataSource.SimpleCallBack<List<HospitalInfo>>() {
            @Override
            public void onSuccess(List<HospitalInfo> hospitalInfos) {
                if (mMapView != null) mMapView.onKeyWordSearched(hospitalInfos);
            }

            @Override
            public void onFaild(int errorCode, String errorMsg) {
                if (mMapView != null) mMapView.onKeyWordSearched(null);
            }
        });
    }

    @Override
    public void attach(MapContract.MapView mapView) {
        mMapView = mapView;
    }

    @Override
    public void detach() {
        mMapView = null;
    }
}
