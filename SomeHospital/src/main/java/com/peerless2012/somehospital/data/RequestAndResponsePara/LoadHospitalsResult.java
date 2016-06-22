package com.peerless2012.somehospital.data.RequestAndResponsePara;

import com.peerless2012.somehospital.data.bean.CityInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/22 23:09
 * @Version V1.0
 * @Description :
 */
public class LoadHospitalsResult {

    private List<CityInfo> cityInfos;

    public List<CityInfo> getCityInfos() {
        return cityInfos;
    }

    public void setCityInfos(List<CityInfo> cityInfos) {
        this.cityInfos = cityInfos;
    }
}
