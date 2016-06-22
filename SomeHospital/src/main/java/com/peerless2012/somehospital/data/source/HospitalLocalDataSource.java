package com.peerless2012.somehospital.data.source;

import com.peerless2012.somehospital.data.bean.CityInfo;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:48
 * @Version V1.0
 * @Description :
 */
public interface HospitalLocalDataSource {

    void saveHospitals(List<CityInfo> cityInfos);

    void getHospitals(HospitalDataSource.LoadHospitalsCallBack callBack);

}
