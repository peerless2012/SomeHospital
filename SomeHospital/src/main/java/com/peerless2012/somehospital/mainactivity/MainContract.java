package com.peerless2012.somehospital.mainactivity;

import com.peerless2012.somehospital.BasePresenter;
import com.peerless2012.somehospital.BaseView;
import com.peerless2012.somehospital.model.HospitalInfo;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/3 21:18
 * @Version V1.0
 * @Description
 */
public interface MainContract {
    interface View extends BaseView<Presenter>{
        void onDataLoaded(List<HospitalInfo> hospitalInfos);
    }

    interface Presenter extends BasePresenter{
        void startLoading(String url);
        void refresh();
        void search(String key);
        void checkDbUpdate(String version);
    }
}
