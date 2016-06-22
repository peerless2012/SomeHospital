package com.peerless2012.somehospital.data.RequestAndResponsePara;

import com.peerless2012.netlibrary.request.JsonRequest;


/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/22 23:07
 * @Version V1.0
 * @Description :
 */
public class LoadHospitalsRequest extends JsonRequest<LoadHospitalsResult>{


    @Override
    public String getUrl() {
        return "https://raw.githubusercontent.com/peerless2012/SomeHospital/master/data/HospitalsGeoInfo.json";
    }

    @Override
    protected Class<LoadHospitalsResult> getResultClass() {
        return LoadHospitalsResult.class;
    }
}
