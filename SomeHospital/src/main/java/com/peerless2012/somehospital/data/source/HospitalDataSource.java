package com.peerless2012.somehospital.data.source;

import com.peerless2012.somehospital.data.bean.CityInfo;
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

    interface CheckDbCallBack{
        void onCheckSucess(VersionInfo versionInfo);
        void onFaild();
    }

    interface LoadHospitalsCallBack{
        void onLoadSucess(List<CityInfo> cityInfos);
        void onFaild();
    }

    void checkDbVersion(CheckDbCallBack callBack);

    void loadHospitalsWithGeo(LoadHospitalsCallBack callBack);
}
