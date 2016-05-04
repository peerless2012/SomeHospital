package com.peerless2012.somehospital.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/4 22:31
 * @Version V1.0
 * @Description DataSource抽象基类，封装数据库的基本操作以及关闭方法
 */
public abstract class BaseLocalDataSource {
    protected byte[] LOCK = new byte[0];
    protected SQLiteOpenHelper dbHelper = null;
    protected Context mContext;
    public BaseLocalDataSource(Context context, int version) {
        if (mContext == null) throw new IllegalArgumentException("Context can't be null !");
        mContext = context;
        dbHelper = HospitalDbHelper.getInstance(mContext);
    }

    public SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    protected void close(Cursor cursor, SQLiteDatabase db) {
        closeCursor(cursor);
        closeDatabase(db);
    }

    protected void closeDatabase(SQLiteDatabase db) {
        try {
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    protected void closeCursor(Cursor cursor) {
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }
}
