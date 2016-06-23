package com.peerless2012.somehospital.data.source.local;

import android.content.Context;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peerless2012.somehospital.data.bean.CityInfo;
import com.peerless2012.somehospital.data.source.HospitalDataSource;
import com.peerless2012.somehospital.data.source.HospitalLocalDataSource;
import com.peerless2012.somehospital.data.thread.ExecutorCallBack;
import com.peerless2012.somehospital.data.thread.ExecutorRunnable;
import com.peerless2012.somehospital.data.thread.WorkExecutor;
import com.peerless2012.somehospital.utils.FileUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 22:25
 * @Version V1.0
 * @Description
 */
public class HospitalLocalDataSourceImpl implements HospitalLocalDataSource {

    private static volatile HospitalLocalDataSource sInst = null;  // <<< 这里添加了 volatile

    private Context mContext;
    File jsonFile = null;
    Gson gson = new Gson();
    private HospitalLocalDataSourceImpl(Context context) {
        this.mContext = context.getApplicationContext();
        File file = mContext.getFilesDir();
        jsonFile = new File(file,"Cities.json");
    }

    public static HospitalLocalDataSource getInstance(Context context) {
        HospitalLocalDataSource inst = sInst;  // <<< 在这里创建临时变量
        if (inst == null) {
            synchronized (HospitalLocalDataSourceImpl.class) {
                inst = sInst;
                if (inst == null) {
                    inst = new HospitalLocalDataSourceImpl(context);
                    sInst = inst;
                }
            }
        }
        return inst;  // <<< 注意这里返回的是临时变量
    }

    public void destroyInstance(){
        sInst = null;
    }


    @Override
    public void saveHospitals(final List<CityInfo> cityInfos) {
        WorkExecutor.getInstance().execute(new ExecutorRunnable<>(new ExecutorCallBack<Void>() {
            @Override
            public Void doInBackGround() {
                if (jsonFile.exists()) jsonFile.delete();
                String jsonString = gson.toJson(cityInfos).toString();
                FileUtils.saveStrToFile(jsonString,jsonFile.getAbsolutePath(),"UTF-8");
                return null;
            }

            @Override
            public void onPostExecute(Void aVoid) {

            }
        }));
    }

    @Override
    public void getHospitals(final HospitalDataSource.SimpleCallBack<List<CityInfo>> callBack) {
        WorkExecutor.getInstance().execute(new ExecutorRunnable<>(new ExecutorCallBack<List<CityInfo>>() {
            @Override
            public List<CityInfo> doInBackGround() {
                try {
                    StringBuilder stringBuilder = FileUtils.readFile(jsonFile.getAbsolutePath(), "UTF-8");
                    return gson.fromJson(stringBuilder.toString(), new TypeToken<List<CityInfo>>() {
                    }.getType());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onPostExecute(List<CityInfo> cityInfos) {
                callBack.onSuccess(cityInfos);
            }
        }));
    }
}
