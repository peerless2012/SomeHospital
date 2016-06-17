package com.peerless2012.somehospital.data.RequestAndResponsePara;
import com.peerless2012.netlibrary.common.Method;
import com.peerless2012.netlibrary.request.JsonRequest;
import com.peerless2012.somehospital.data.bean.VersionInfo;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/15 23:57
 * @Version V1.0
 * @Description :
 */
public class CheckDbVersionRequest extends JsonRequest<VersionInfo>{
    @Override
    protected Class<VersionInfo> getResultClass() {
        return VersionInfo.class;
    }

    @Override
    public String getMethod() {
        return Method.GET;
    }

    @Override
    public String getUrl() {
        return "https://raw.githubusercontent.com/peerless2012/SomeHospital/master/data/DbVersion.json";
    }
}
