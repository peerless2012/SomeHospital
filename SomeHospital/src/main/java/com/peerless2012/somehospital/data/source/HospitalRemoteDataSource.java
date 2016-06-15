package com.peerless2012.somehospital.data.source;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:47
 * @Version V1.0
 * @Description :
 */
public interface HospitalRemoteDataSource {

    void checkDbVersion();

    void loadDataVersion();

    void loadHospitals();

    void loadHospitalsWithGeo();
}
