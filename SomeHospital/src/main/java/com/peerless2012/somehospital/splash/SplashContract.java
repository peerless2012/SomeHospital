package com.peerless2012.somehospital.splash;

import com.peerless2012.somehospital.BasePresenter;
import com.peerless2012.somehospital.BaseView;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:06
 * @Version V1.0
 * @Description :
 */
interface SplashContract {

    interface SplashView extends BaseView<SplashPresenter>{

        void onCheckFaild();

        void onDbNotNeedUpdate();

        void onDbUpdated(int newVersion);
    }

    interface SplashPresenter extends BasePresenter<SplashView>{

        void checkDbVersion(int localVersion);

        void verifyDb();
    }
}
