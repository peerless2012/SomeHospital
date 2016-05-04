package com.peerless2012.somehospital.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.peerless2012.somehospital.data.source.HospitalDataSource;
import com.peerless2012.somehospital.model.HospitalInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 22:25
 * @Version V1.0
 * @Description
 */
public class HospitalLocalDataSource extends BaseLocalDataSource implements HospitalDataSource {


    public HospitalLocalDataSource(Context context, int version) {
        super(context, version);
    }

    @Override
    public void insertOrUpdateHospitals(List<HospitalInfo> hospitalInfos) {
        SQLiteDatabase database = getWritableDatabase();
    }

    @Override
    public void queryHospitalsByCity(String cityName, LoadHospitalCallBack loadHospitalCallBack) {

    }
}
