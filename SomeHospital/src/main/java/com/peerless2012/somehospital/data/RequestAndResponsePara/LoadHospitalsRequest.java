package com.peerless2012.somehospital.data.RequestAndResponsePara;

import com.google.common.reflect.TypeToken;
import com.peerless2012.netlibrary.common.Method;
import com.peerless2012.netlibrary.request.JsonRequest;
import com.peerless2012.somehospital.data.bean.CityInfo;
import java.lang.reflect.Type;
import java.util.List;


/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/22 23:07
 * @Version V1.0
 * @Description :
 */
public class LoadHospitalsRequest extends JsonRequest<List<CityInfo>>{

    @Override
    public String getMethod() {
        return Method.GET;
    }

    @Override
    public String getUrl() {
//        return "https://raw.githubusercontent.com/peerless2012/SomeHospital/master/data/HospitalsGeoInfo.json";
        return "http://192.168.31.201:8080/data/HospitalsGeoInfo.json";
    }


    @Override
    protected Type getResultType() {
        return new TypeToken<List<CityInfo>>(){}.getType();
    }

    @Override
    protected Class<List<CityInfo>> getResultClass() {
        return null;
    }
}
