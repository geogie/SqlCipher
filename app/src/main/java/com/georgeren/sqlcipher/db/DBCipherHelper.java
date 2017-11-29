package com.georgeren.sqlcipher.db;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;

/**
 * Created by georgeRen on 2017/9/12.
 * 参考：http://www.cnblogs.com/whoislcj/archive/2016/07/30/5511522.html
 * https://www.zetetic.net/sqlcipher/sqlcipher-for-android/
 */

public class DBCipherHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBCipherHelper";
    private static final String DB_PATH = "/mycipher/";//数据库路径
    private static final String DB_NAME = "test_cipher_db";//数据库名字
    public static final String DB_PWD = "whoislcj";//数据库密码
    public static String TABLE_NAME = "person";// 表名
    public static String FIELD_ID = "id";// 列名
    public static String FIELD_NAME = "name";// 列名
    private static final int DB_VERSION = 1;   // 数据库版本

    public DBCipherHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        //不可忽略的 进行so库加载
        SQLiteDatabase.loadLibs(context);
    }

    /**
     * 设置DB数据库的路径
     *
     * @param context
     */
    public static void setDBPath(Context context) {
        try {
            File file = new File(getDBPath(context));
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对DB路径进行处理
     * @param context
     * @return
     */
    public static String getDBPath(Context context) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {// 外部存储，
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + DB_PATH;
        } else {// 内部存储
            path = context.getFilesDir().getAbsolutePath() + DB_PATH;
        }
        return path;
    }

    public DBCipherHelper(Context context) {
        this(context, getDBPath(context) + DB_NAME, null, DB_VERSION);
    }

    /**
     * 创建数据库
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        createTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + FIELD_ID + " integer primary key autoincrement , " + FIELD_NAME + " text not null);";
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate " + TABLE_NAME + "Error" + e.toString());
            return;
        }
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
