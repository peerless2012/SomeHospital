package com.peerless2012.somehospital.data.source;

import com.peerless2012.somehospital.data.bean.HospitalInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 23:05
 * @Version V1.0
 * @Description 获取医院列表的类，封装了远程、本地、内存三重缓存
 */
public class HospitalRepository implements HospitalDataSource
                                        ,HospitalRemoteDataSource,HospitalLocalDataSource{

    private static volatile HospitalRepository sInst = null;  // <<< 这里添加了 volatile
    boolean mCacheIsDirty = false;

    private HospitalLocalDataSource mLocalDataSource;

    private HospitalRemoteDataSource mRemoteDataSource;

    private HospitalRepository(HospitalLocalDataSource localDataSource,HospitalRemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static HospitalRepository getInstance(HospitalLocalDataSource localDataSource,HospitalRemoteDataSource remoteDataSource) {
        HospitalRepository inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalRepository.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HospitalRepository(localDataSource,remoteDataSource);
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
    public void checkDbVersion() {
        mRemoteDataSource.checkDbVersion();
    }

    @Override
    public void loadDataVersion() {

    }

    @Override
    public void loadHospitals() {

    }

    @Override
    public void loadHospitalsWithGeo() {

    }
}


