package com.peerless2012.somehospital.data.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/15 23:55
 * @Version V1.0
 * @Description :
 */
public class VersionInfo {
    @SerializedName("dbversion")
    @Expose
    private int dbversion;

    public int getDbversion() {
        return dbversion;
    }

    public void setDbversion(int dbversion) {
        this.dbversion = dbversion;
    }
}
