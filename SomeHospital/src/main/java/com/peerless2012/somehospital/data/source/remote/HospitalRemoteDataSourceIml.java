package com.peerless2012.somehospital.data.source.remote;

import com.peerless2012.netlibrary.HttpUtils;
import com.peerless2012.netlibrary.OkHttpUtils;
import com.peerless2012.netlibrary.callback.OkCallBack;
import com.peerless2012.netlibrary.response.AbsResponse;
import com.peerless2012.somehospital.data.RequestAndResponsePara.CheckDbVersionRequest;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import com.peerless2012.somehospital.data.source.HospitalRemoteDataSource;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 23:01
 * @Version V1.0
 * @Description
 */
public class HospitalRemoteDataSourceIml implements HospitalRemoteDataSource{

    @Override
    public void checkDbVersion() {
        CheckDbVersionRequest request = new CheckDbVersionRequest();
        HttpUtils.getInstance().asyncExcute(request, new OkCallBack<VersionInfo>() {
            @Override
            public void onFail(int errorCode, String errorMsg) {

            }

            @Override
            public void onScuss(AbsResponse<VersionInfo> response) {

            }
        });
    }

    @Override
    public void loadDataVersion() {

    }

    @Override
    public void loadHospitals() {

    }

    @Override
    public void loadHospitalsWithGeo() {

    }
}
