package com.peerless2012.somehospital.mainactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peerless2012.somehospital.data.bean.HospitalInfo;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/3 21:36
 * @Version V1.0
 * @Description 主页适配器
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{
    private List<HospitalInfo> mHospitalInfos;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater.from(parent.getContext());
        TextView textview = new TextView(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(textview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mHospitalInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return mHospitalInfos == null ? 0 : mHospitalInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView hospitalName;
        public ViewHolder(View itemView) {
            super(itemView);
            hospitalName = (TextView) itemView;
        }

        public void bindData(HospitalInfo hospitalInfo){
        }
    }

    public void changeData(List<HospitalInfo> hospitalInfos){
        mHospitalInfos = hospitalInfos;
        notifyDataSetChanged();
    }
}
