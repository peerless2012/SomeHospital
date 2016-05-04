package com.peerless2012.somehospital.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 11:28
 * @Version V1.0
 * @Description 数据库帮助类
 */
public class HospitalDbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "BlackHeartHospital.db";
    private static volatile HospitalDbHelper mHospitalDbHelper = null;  // <<< 这里添加了 volatile
    private static final int DB_VERSION = 1;
    public static HospitalDbHelper getInstance(Context context) {
        HospitalDbHelper inst = mHospitalDbHelper;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalDbHelper.class) {
                inst = mHospitalDbHelper;
                if (inst == null) {
                    inst = new HospitalDbHelper(context);
                    mHospitalDbHelper = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public HospitalDbHelper(Context context) {
        super(context, DB_NAME ,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HospitalPersistenceContract.HospitalEntry.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + HospitalPersistenceContract.HospitalEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
