package com.peerless2012.somehospital.splash;

import android.content.Context;
import android.content.SharedPreferences;

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

    private final static String DB_VERSION = "DbVersion";

    private HospitalRepository mHospitalRepository;

    private SharedPreferences mPreferences;

    public SplashPresenter(Context context) {
        this.mHospitalRepository = HospitalRepository.getInstance(context);
        mPreferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
    }

    private SplashContract.SplashView mSplashView;

    @Override
    public void checkDbVersion() {
        final int localVersion = mPreferences.getInt(DB_VERSION, 0);
        mHospitalRepository.checkDbVersion(new HospitalDataSource.SimpleCallBack<VersionInfo>() {
            @Override
            public void onSuccess(final VersionInfo versionInfo) {
                if (versionInfo.getDbversion() > localVersion){
                    mHospitalRepository.loadHospitalsWithGeo(new HospitalDataSource.SimpleCallBack<List<CityInfo>>() {
                        @Override
                        public void onSuccess(List<CityInfo> cityInfos) {
                            mPreferences.edit().putInt(DB_VERSION,versionInfo.getDbversion()).apply();
                            mSplashView.onDbUpdated();
                        }

                        @Override
                        public void onFaild(int errorCode, String errorMsg) {
                            mSplashView.onCheckFaild();
                        }
                    });
                }else {
                    // 已经是最新，无需更新
                    mSplashView.onDbNotNeedUpdate();
                }
            }

            @Override
            public void onFaild(int errorCode, String errorMsg) {
                mSplashView.onCheckFaild();
            }
        });
    }

    @Override
    public void verifyDb() {

    }

    @Override
    public void attach(SplashContract.SplashView splashView) {
        this.mSplashView = splashView;
    }

    @Override
    public void detach() {
        mSplashView = null;
    }
}
