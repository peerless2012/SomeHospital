package com.peerless2012.somehospital.data.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/17 19:39
 * @Version V1.0
 * @Description :longitude and latitude
 */
public class Geo {
    /**
     * 经度
     */
    @SerializedName("longitude")
    @Expose
    private double longitude;

    /**
     * 纬度
     */
    @SerializedName("latitude")
    @Expose
    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
