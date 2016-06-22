package com.peerless2012.somehospital.data.thread;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/23 9:15
 * @Version V1.0
 * @Description :
 */
public interface ExecutorCallBack<T> {

    T doInBackGround();

    void onPostExecute(T t);
}
