package com.peerless2012.somehospital.data.source;

import android.content.Context;
import com.peerless2012.netlibrary.callback.OkInnerWork;
import com.peerless2012.netlibrary.request.AbsRequest;
import com.peerless2012.netlibrary.response.AbsResponse;
import com.peerless2012.somehospital.data.bean.CityInfo;
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

    private HospitalRepository(Context context,HospitalLocalDataSource localDataSource, HospitalRemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
        this.mContext = context.getApplicationContext();
    }

    public static HospitalRepository getInstance(Context context,HospitalLocalDataSource localDataSource,HospitalRemoteDataSource remoteDataSource) {
        HospitalRepository inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalRepository.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HospitalRepository(context,localDataSource,remoteDataSource);
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
    public void checkDbVersion(CheckDbCallBack callBack) {
        mRemoteDataSource.checkDbVersion(callBack);
    }

    @Override
    public void loadHospitalsWithGeo(final LoadHospitalsCallBack callBack) {
        mLocalDataSource.getHospitals(new LoadHospitalsCallBack() {
            @Override
            public void onLoadSucess(List<CityInfo> cityInfos) {
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
                    callBack.onLoadSucess(cityInfos);
                }
            }

            @Override
            public void onFaild() {
                callBack.onFaild();
            }
        });

    }
}


