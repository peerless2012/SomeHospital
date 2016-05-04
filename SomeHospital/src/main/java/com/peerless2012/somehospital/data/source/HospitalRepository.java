package com.peerless2012.somehospital.data.source;

import com.peerless2012.somehospital.model.HospitalInfo;

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

    private HospitalRepository(HospitalDataSource localDataSource,HospitalDataSource remoteDataSource) {
    }

    public static HospitalRepository getInstance(HospitalDataSource localDataSource,HospitalDataSource remoteDataSource) {
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

    @Override
    public void insertOrUpdateHospitals(List<HospitalInfo> hospitalInfos) {

    }

    @Override
    public void queryHospitalsByCity(String cityName, LoadHospitalCallBack loadHospitalCallBack) {

    }
}


