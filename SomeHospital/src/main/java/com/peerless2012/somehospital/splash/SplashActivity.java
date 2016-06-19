package com.peerless2012.somehospital.splash;

import android.animation.Animator;
import com.peerless2012.somehospital.R;
import com.peerless2012.somehospital.base.BaseActivity;
import com.peerless2012.somehospital.map.MapActivity;
import com.peerless2012.somehospital.widget.HeartView;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:00
 * @Version V1.0
 * @Description :
 */
public class SplashActivity extends BaseActivity
        implements SplashContract.SplashView,Animator.AnimatorListener{

    private SplashPresenter mSplashPresenter;

    private HeartView mHeartView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mHeartView = getView(R.id.heart_view);
    }

    @Override
    protected void initListener() {
        mHeartView.setOnAnimListener(this);
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
    }

    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {

    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        MapActivity.launch(this);
        finish();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
