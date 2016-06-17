package com.peerless2012.somehospital.splash;

import com.peerless2012.somehospital.R;
import com.peerless2012.somehospital.base.BaseActivity;
import com.peerless2012.somehospital.map.MapActivity;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:00
 * @Version V1.0
 * @Description :
 */
public class SplashActivity extends BaseActivity implements SplashContract.SplashView{

    private SplashPresenter mSplashPresenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mSplashPresenter = new SplashPresenter(null);
        mSplashPresenter.attach(this);
        mSplashPresenter.checkDbVersion();
    }

    @Override
    protected void onDestroy() {
        mSplashPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onCheckResult(boolean success) {
        MapActivity.launch(this);
        finish();
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {

    }
}
