package com.peerless2012.somehospital.mainactivity;

import android.os.SystemClock;

import com.peerless2012.somehospital.data.bean.HospitalInfo;
import com.peerless2012.somehospital.net.OkHttpUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/3 21:25
 * @Version V1.0
 * @Description 业务逻辑处理
 */
public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void startLoading(final String url) {
        Observable.fromCallable(new Func0<List<HospitalInfo>>() {
            @Override
            public List<HospitalInfo> call() {
                List<HospitalInfo> hospitalInfos = null;
                Request mRequest = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = OkHttpUtils.getInstance().getOkHttpClient().newCall(mRequest).execute();
                    if (response != null && response.isSuccessful() && response.body() != null) {
                        String result = response.body().string();
                        hospitalInfos = new ArrayList<HospitalInfo>();
                        for(int i = 0 ;i < 100;i ++){
                            HospitalInfo hospitalInfo = new HospitalInfo();
                            hospitalInfos.add(hospitalInfo);
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                SystemClock.sleep(3000);
                return hospitalInfos;
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<List<HospitalInfo>>() {
                    @Override
                    public void call(List<HospitalInfo> hospitalInfos) {
                        if (mView != null) {
                            mView.onDataLoaded(hospitalInfos);
                        }
                    }
                });


    }

    @Override
    public void refresh() {

    }

    @Override
    public void search(String key) {
    }

    @Override
    public void checkDbUpdate(String version) {

    }

    @Override
    public void attach(MainContract.View view) {

    }

    @Override
    public void detach() {

    }
}
