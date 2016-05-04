package com.peerless2012.somehospital.data.source;

import android.Manifest;
import android.support.annotation.CallSuper;
import android.support.annotation.RequiresPermission;

import com.peerless2012.somehospital.model.HospitalInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 22:19
 * @Version V1.0
 * @Description
 */
public interface HospitalDataSource {

    interface LoadHospitalCallBack{
        void onHospitlsLoaded(List<HospitalInfo> hospitalInfos);
        void onDataNotAvailable();
    }

    @RequiresPermission(Manifest.permission.SET_WALLPAPER)
    void insertOrUpdateHospitals(List<HospitalInfo> hospitalInfos);

    @CallSuper
    void queryHospitalsByCity(String cityName,LoadHospitalCallBack loadHospitalCallBack);

}
