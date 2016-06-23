package com.peerless2012.somehospital.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:28
 * @Version V1.0
 * @Description :
 */
public class HospitalSearchSuggestion implements SearchSuggestion {

    private HospitalInfo hospitalInfo;

    public HospitalSearchSuggestion(HospitalInfo hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    @Override
    public String getBody() {
        return hospitalInfo.getName();
    }

    @Override
    public Creator getCreator() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public final static Creator<HospitalSearchSuggestion> CREATOR = new Creator<HospitalSearchSuggestion>() {
        @Override
        public HospitalSearchSuggestion createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public HospitalSearchSuggestion[] newArray(int size) {
            return new HospitalSearchSuggestion[0];
        }
    };

}
