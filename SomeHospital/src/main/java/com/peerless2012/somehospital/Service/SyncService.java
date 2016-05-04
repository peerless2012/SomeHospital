package com.peerless2012.somehospital.Service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/5/3 20:21
* @Version V1.0
* @Description: 同步数据库
*/
public class SyncService extends IntentService {

    public SyncService() {
        super("SyncService");
    }


    /**
     * 开启同步数据库
     *
     * @see IntentService
     */
    public static void startSync(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
