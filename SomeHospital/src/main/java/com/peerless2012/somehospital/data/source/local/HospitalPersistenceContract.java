package com.peerless2012.somehospital.data.source.local;

import android.provider.BaseColumns;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 22:13
 * @Version V1.0
 * @Description
 */
public final class HospitalPersistenceContract {
    public HospitalPersistenceContract(){}


    public static abstract class HospitalEntry implements BaseColumns{

        public static final String TABLE_NAME = "Hospitals";
        public static final String NAME = "_name";
        public static final String TEL = "_tel";
        public static final String WEB1 = "_web1";
        public static final String WEB2 = "_web2";
        public static final String DESC = "_desc";

        public static final String CREATE_SQL = "CREATE TABLE IF NOT EXIST "
                + TABLE_NAME +" ( " + _ID +" PRIMARY KEY AUTOINCREAMENT, "
                + NAME +" VCHAR ," + TEL +" VCHAR , " + WEB1 + " VCHAR , "
                + WEB2 + " VCHAR , " + DESC + " VCHAR);";
    }
}
