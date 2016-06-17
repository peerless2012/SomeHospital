package com.peerless2012.somehospital.splash;

import com.peerless2012.netlibrary.HttpUtils;
import com.peerless2012.netlibrary.callback.OkCallBack;
import com.peerless2012.netlibrary.response.AbsResponse;
import com.peerless2012.somehospital.data.RequestAndResponsePara.CheckDbVersionRequest;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import com.peerless2012.somehospital.data.source.HospitalRepository;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:13
 * @Version V1.0
 * @Description :
 */
public class SplashPresenter implements SplashContract.SplashPresenter{

    private HospitalRepository mHospitalRepository;

    public SplashPresenter(HospitalRepository mHospitalRepository) {
        this.mHospitalRepository = mHospitalRepository;
    }

    private SplashContract.SplashView msSplashView;

    @Override
    public void checkDbVersion() {
//        mHospitalRepository.checkDbVersion();
        CheckDbVersionRequest request = new CheckDbVersionRequest();
        HttpUtils.getInstance().asyncExcute(request, new OkCallBack<VersionInfo>() {
            @Override
            public void onFail(int errorCode, String errorMsg) {
                msSplashView.onCheckResult(false);
            }

            @Override
            public void onScuss(AbsResponse<VersionInfo> response) {
                msSplashView.onCheckResult(true);
            }
        });
    }

    @Override
    public void verifyDb() {

    }

    @Override
    public void attach(SplashContract.SplashView splashView) {
        this.msSplashView = splashView;
    }

    @Override
    public void detach() {
        msSplashView = null;
    }
}
