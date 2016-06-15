package com.peerless2012.somehospital.data.RequestAndResponsePara;

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
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }
}
