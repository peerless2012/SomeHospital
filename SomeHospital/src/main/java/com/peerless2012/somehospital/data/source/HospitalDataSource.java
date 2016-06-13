package com.peerless2012.somehospital.data.source;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:54
 * @Version V1.0
 * @Description :
 */
public interface HospitalDataSource {

    interface OnCheckDataVersionCallBack{

        void onSuccess();

        void onFail();

    }
}
