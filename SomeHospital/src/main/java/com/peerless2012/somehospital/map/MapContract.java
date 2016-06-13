package com.peerless2012.somehospital.map;

import com.peerless2012.somehospital.BasePresenter;
import com.peerless2012.somehospital.BaseView;
import com.peerless2012.somehospital.data.bean.HospitalInfo;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 10:03
 * @Version V1.0
 * @Description :
 */
public interface MapContract {

    interface MapView extends BaseView<MapPresenter>{

        void onDataLoaded(List<HospitalInfo> hospitalInfos);

        void onKeyWordSearched(List<HospitalInfo> hospitalInfos);
    }

    interface MapPresenter extends BasePresenter<MapView>{

        void initData();

        void searchKeyWord(String keyword);
    }
}
