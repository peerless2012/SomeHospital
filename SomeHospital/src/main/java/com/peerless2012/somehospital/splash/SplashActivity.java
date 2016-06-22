package com.peerless2012.somehospital.splash;

import android.animation.Animator;
import android.content.SharedPreferences;

import com.peerless2012.somehospital.R;
import com.peerless2012.somehospital.base.BaseActivity;
import com.peerless2012.somehospital.data.source.HospitalRepository;
import com.peerless2012.somehospital.data.source.local.HospitalLocalDataSourceImpl;
import com.peerless2012.somehospital.data.source.remote.HospitalRemoteDataSourceIml;
import com.peerless2012.somehospital.map.MapActivity;
import com.peerless2012.somehospital.widget.HeartView;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 22:00
 * @Version V1.0
 * @Description :
 */
public class SplashActivity extends BaseActivity<SplashContract.SplashView,SplashPresenter>
        implements SplashContract.SplashView,Animator.AnimatorListener{

    private final static String DB_VERSION = "DbVersion";

    private boolean isAnimEnded = false;

    private boolean isDataInited = false;

    private HeartView mHeartView;

    private SharedPreferences mSharedPreferences;

    @Override
    public SplashContract.SplashView getPresenterView() {
        return this;
    }

    @Override
    public SplashPresenter getPresenter() {
        return new SplashPresenter(HospitalRepository.getInstance(this
                , HospitalLocalDataSourceImpl.getInstance(this)
                , HospitalRemoteDataSourceIml.getInstance(this)));
    }

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
        mHeartView.setAnimListener(this);
    }

    @Override
    protected void initData() {
        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        int dbVersion = mSharedPreferences.getInt(DB_VERSION, 0);
        mPresenter.checkDbVersion(dbVersion);
    }


    @Override
    public void setPresenter(SplashContract.SplashPresenter presenter) {

    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        isAnimEnded = true;
        jump();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private void jump(){
        if (isAnimEnded && isDataInited){
            MapActivity.launch(this);
            finish();
        }
    }

    @Override
    public void onCheckFaild() {
        isDataInited = true;
        jump();
    }

    @Override
    public void onDbNotNeedUpdate() {
        isDataInited = true;
        jump();
    }

    @Override
    public void onDbUpdated(int newVersion) {
        mSharedPreferences.edit().putInt(DB_VERSION,newVersion).apply();
        isDataInited = true;
        jump();
        // TODO 存储最新版本号
    }
}
