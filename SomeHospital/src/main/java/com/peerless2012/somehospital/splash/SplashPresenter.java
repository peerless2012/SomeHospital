package com.peerless2012.somehospital.splash;

import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import com.peerless2012.somehospital.data.source.HospitalDataSource;
import com.peerless2012.somehospital.data.source.HospitalRepository;
import java.util.List;

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
    public void checkDbVersion(final int localVersion) {
        mHospitalRepository.checkDbVersion(new HospitalDataSource.CheckDbCallBack() {
            @Override
            public void onCheckSucess(final VersionInfo versionInfo) {
                if (versionInfo.getDbversion() > localVersion){
                    mHospitalRepository.loadHospitalsWithGeo(new HospitalDataSource.LoadHospitalsCallBack() {
                        @Override
                        public void onLoadSucess(List<CityInfo> cityInfos) {
                            msSplashView.onDbUpdated(versionInfo.getDbversion());
                        }

                        @Override
                        public void onFaild() {
                            msSplashView.onCheckFaild();
                        }
                    });
                }else {
                    // 已经是最新，无需更新
                    msSplashView.onDbNotNeedUpdate();
                }
            }

            @Override
            public void onFaild() {
                msSplashView.onCheckFaild();
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
