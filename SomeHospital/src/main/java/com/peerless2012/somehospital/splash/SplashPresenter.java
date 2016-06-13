package com.peerless2012.somehospital.splash;

import android.os.Handler;
import android.os.Message;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:13
 * @Version V1.0
 * @Description :
 */
public class SplashPresenter implements SplashContract.SplashPresenter{

    private SplashContract.SplashView msSplashView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msSplashView.onCheckResult(true);
        }
    };

    @Override
    public void checkDbVersion() {
        mHandler.sendEmptyMessageDelayed(0,3000);
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
