package com.peerless2012.somehospital.data.source;


import com.peerless2012.netlibrary.callback.OkInnerWork;
import com.peerless2012.somehospital.data.bean.CityInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:47
 * @Version V1.0
 * @Description :
 */
public interface HospitalRemoteDataSource {

    void checkDbVersion(HospitalDataSource.SimpleCallBack callBack);

    void loadHospitalsWithGeo(HospitalDataSource.SimpleCallBack callBack, OkInnerWork<List<CityInfo>> innerWork);
}
