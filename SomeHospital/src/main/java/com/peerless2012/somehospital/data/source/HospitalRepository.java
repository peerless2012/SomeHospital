package com.peerless2012.somehospital.data.source;

import android.content.Context;
import android.content.SharedPreferences;

import com.peerless2012.netlibrary.callback.OkInnerWork;
import com.peerless2012.netlibrary.request.AbsRequest;
import com.peerless2012.netlibrary.response.AbsResponse;
import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import com.peerless2012.somehospital.data.bean.VersionInfo;
import com.peerless2012.somehospital.data.source.local.HospitalLocalDataSourceImpl;
import com.peerless2012.somehospital.data.source.remote.HospitalRemoteDataSourceIml;
import java.util.ArrayList;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 23:05
 * @Version V1.0
 * @Description 获取医院列表的类，封装了远程、本地、内存三重缓存
 */
public class HospitalRepository implements HospitalDataSource{

    private static volatile HospitalRepository sInst = null;  // <<< 这里添加了 volatile

    boolean mCacheIsDirty = false;

    private HospitalLocalDataSource mLocalDataSource;

    private HospitalRemoteDataSource mRemoteDataSource;

    private Context mContext;

    private List<CityInfo> mCityInfos;

    private HospitalRepository(Context context) {
        this.mLocalDataSource = HospitalLocalDataSourceImpl.getInstance(context);
        this.mRemoteDataSource = HospitalRemoteDataSourceIml.getInstance(context);
        this.mContext = context.getApplicationContext();
    }

    public static HospitalRepository getInstance(Context context) {
        HospitalRepository inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalRepository.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HospitalRepository(context);
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
    public void checkDbVersion(SimpleCallBack<VersionInfo> callBack) {
        mRemoteDataSource.checkDbVersion(callBack);
    }

    @Override
    public void loadHospitalsWithGeo(final SimpleCallBack<List<CityInfo>> callBack) {
        if (mCityInfos != null && !mCacheIsDirty){
            callBack.onSuccess(mCityInfos);
        }else {
            mLocalDataSource.getHospitals(new SimpleCallBack<List<CityInfo>>() {
                @Override
                public void onSuccess(List<CityInfo> cityInfos) {
                    if (cityInfos == null){
                        mRemoteDataSource.loadHospitalsWithGeo(callBack, new OkInnerWork<List<CityInfo>>() {
                            @Override
                            public void preDo(AbsRequest<List<CityInfo>> request) {

                            }

                            @Override
                            public void afterDo(AbsResponse<List<CityInfo>> response) {
                                // 存储数据
                                if (response != null && response.isSuccess()){
                                    mLocalDataSource.saveHospitals(response.getData());
                                }
                            }
                        });
                    }else {
                        mCityInfos = cityInfos;
                        callBack.onSuccess(cityInfos);
                    }
                }

                @Override
                public void onFaild(int errorCode, String errorMsg) {
                    callBack.onFaild(errorCode,errorMsg);
                }
            });
        }

    }

    @Override
    public void loadHospitalsWithCityName(final String cityName, final SimpleCallBack<List<HospitalInfo>> callBack) {
        loadHospitalsWithGeo(new SimpleCallBack<List<CityInfo>>() {
            @Override
            public void onSuccess(List<CityInfo> cityInfos) {
                if (cityInfos != null){
                    int size = cityInfos.size();
                    for (int i = 0; i < size; i++) {
                        CityInfo cityInfo = cityInfos.get(i);
                        if (cityName.contains(cityInfo.getCityName())){
                            List<HospitalInfo> hospitalInfos = cityInfo.getHospitalInfos();
                            callBack.onSuccess(hospitalInfos);
                            return;
                        }

                    }
                }
                callBack.onFaild(0,null);
            }

            @Override
            public void onFaild(int errorCode, String errorMsg) {
                callBack.onFaild(errorCode,errorMsg);
            }
        });
    }

    @Override
    public void loadHospitalsWithKeyWord(final String keyWord, final SimpleCallBack<List<HospitalInfo>> callBack) {
        loadHospitalsWithGeo(new SimpleCallBack<List<CityInfo>>() {
            @Override
            public void onSuccess(List<CityInfo> cityInfos) {
                List<HospitalInfo> hospitalInfosResult = new ArrayList<HospitalInfo>();
                if (cityInfos != null){
                    int size = cityInfos.size();
                    for (int i = 0; i < size; i++) {
                        CityInfo cityInfo = cityInfos.get(i);
                        List<HospitalInfo> hospitalInfos = cityInfo.getHospitalInfos();
                        int size1 = hospitalInfos.size();
                        for (int j = 0; j < size1; j++) {
                            HospitalInfo hospitalInfo = hospitalInfos.get(j);
                            if (hospitalInfo.getName().contains(keyWord))
                                hospitalInfosResult.add(hospitalInfo);
                        }
                    }
                }
                callBack.onSuccess(hospitalInfosResult);
            }

            @Override
            public void onFaild(int errorCode, String errorMsg) {
                callBack.onFaild(errorCode,errorMsg);
            }
        });
    }
}


