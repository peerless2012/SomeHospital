package com.peerless2012.somehospital.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:32
 * @Version V1.0
 * @Description :
 */
public class CityInfo {
    private String cityName;
    private List<HospitalInfo> hospitalInfos;

    @Override
    public String toString() {
        return "CityInfo [cityName=" + cityName + ", hospitalInfo="
                + hospitalInfos + "]";
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<HospitalInfo> getHospitalInfos() {
        return hospitalInfos;
    }

    public void addHospitalInfo(HospitalInfo hospitalInfo) {
        if (hospitalInfos == null) {
            hospitalInfos = new ArrayList<HospitalInfo>();
        }
        hospitalInfos.add(hospitalInfo);
    }

    public void setHospitalInfos(List<HospitalInfo> hospitalInfos) {
        this.hospitalInfos = hospitalInfos;
    }
}
