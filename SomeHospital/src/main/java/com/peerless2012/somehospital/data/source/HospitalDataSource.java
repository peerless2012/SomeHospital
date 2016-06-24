package com.peerless2012.somehospital.data.source;

import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:54
 * @Version V1.0
 * @Description :
 */
public interface HospitalDataSource {

    interface SimpleCallBack<D>{
        void onSuccess(D d);
        void onFaild(int errorCode,String errorMsg);
    }

    void checkDbVersion(SimpleCallBack<VersionInfo> callBack);

    void loadHospitalsWithGeo(SimpleCallBack<List<CityInfo>> callBack);

    void reloadHospitalsWithGeo(SimpleCallBack<List<CityInfo>> callBack);

    void loadHospitalsWithCityName(String cityName,SimpleCallBack<List<HospitalInfo>> callBack);

    void loadHospitalsWithKeyWord(String keyWord,SimpleCallBack<List<HospitalInfo>> callBack);
}
