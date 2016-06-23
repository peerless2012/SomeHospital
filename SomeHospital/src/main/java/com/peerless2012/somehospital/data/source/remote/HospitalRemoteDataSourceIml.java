package com.peerless2012.somehospital.data.source.remote;

import android.content.Context;

import com.peerless2012.netlibrary.HttpUtils;
import com.peerless2012.netlibrary.callback.OkCallBack;
import com.peerless2012.netlibrary.callback.OkInnerWork;
import com.peerless2012.somehospital.data.RequestAndResponsePara.CheckDbVersionRequest;
import com.peerless2012.somehospital.data.RequestAndResponsePara.LoadHospitalsRequest;
import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import com.peerless2012.somehospital.data.source.HospitalDataSource;
import com.peerless2012.somehospital.data.source.HospitalRemoteDataSource;
import com.peerless2012.somehospital.data.source.local.HospitalLocalDataSourceImpl;

import java.io.File;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 23:01
 * @Version V1.0
 * @Description
 */
public class HospitalRemoteDataSourceIml implements HospitalRemoteDataSource{

    private static volatile HospitalRemoteDataSource sInst = null;  // <<< 这里添加了 volatile

    private Context mContext;
    File jsonFile = null;
    private HospitalRemoteDataSourceIml(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static HospitalRemoteDataSource getInstance(Context context) {
        HospitalRemoteDataSource inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalLocalDataSourceImpl.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HospitalRemoteDataSourceIml(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public void destroyInstance(){
        sInst = null;
    }


    @Override
    public void checkDbVersion(final HospitalDataSource.SimpleCallBack callBack) {
        CheckDbVersionRequest request = new CheckDbVersionRequest();
        HttpUtils.getInstance().asyncExcute(request, new OkCallBack<VersionInfo>() {
            @Override
            public void onFail(int errorCode, String errorMsg) {
                callBack.onFaild(errorCode,errorMsg);
            }

            @Override
            public void onScuss(VersionInfo versionInfo) {
                callBack.onSuccess(versionInfo);
            }
        });

    }

    @Override
    public void loadHospitalsWithGeo(final HospitalDataSource.SimpleCallBack callBack, OkInnerWork<List<CityInfo>> innerWork) {
        final LoadHospitalsRequest request = new LoadHospitalsRequest();
        HttpUtils.getInstance().asyncExcute(request, new OkCallBack<List<CityInfo>>() {
            @Override
            public void onFail(int errorCode, String errorMsg) {
                callBack.onFaild(errorCode,errorMsg);
            }

            @Override
            public void onScuss(List<CityInfo> response) {
                callBack.onSuccess(response);
            }
        }, innerWork);
    }
}
