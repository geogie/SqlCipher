package com.georgeren.sqlcipher.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import net.sqlcipher.Cursor;
import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by georgeRen on 2017/9/12.
 */

public class DBCipherManager {
    private static final String TAG = "DatabaseManager";
    // 静态引用
    private volatile static DBCipherManager mInstance;
    // DatabaseHelper
    private DBCipherHelper dbHelper;

    private DBCipherManager(Context context) {
        dbHelper = new DBCipherHelper(context.getApplicationContext());
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBCipherManager getInstance(Context context) {
        DBCipherManager inst = mInstance;
        if (inst == null) {
            synchronized (DBCipherManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new DBCipherManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 插入数据
     */
    public void insertData(String name) {
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();
        cv.put(DBCipherHelper.FIELD_NAME, name);
        // insert 操作
        long value = db.insert(DBCipherHelper.TABLE_NAME, null, cv);
        Log.d(TAG, "insertData-value:" + value);
        //关闭数据库
        db.close();
    }

    /**
     * 未开启事务批量插入
     *
     * @param testCount
     */
    public void insertDatasByNomarl(int testCount) {
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        for (int i = 0; i < testCount; i++) {
            //生成要修改或者插入的键值
            ContentValues cv = new ContentValues();
            cv.put(DBCipherHelper.FIELD_NAME, String.valueOf(i));
            // insert 操作
            db.insert(DBCipherHelper.TABLE_NAME, null, cv);
            Log.e(TAG, "insertDatasByNomarl");
        }
        //关闭数据库
        db.close();
    }

    /**
     * 测试开启事务批量插入
     *
     * @param testCount
     */
    public void insertDatasByTransaction(int testCount) {
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        db.beginTransaction();  //手动设置开始事务
        try {
            //批量处理操作
            for (int i = 0; i < testCount; i++) {
                //生成要修改或者插入的键值
                ContentValues cv = new ContentValues();
                cv.put(DBCipherHelper.FIELD_NAME, String.valueOf(i));
                // insert 操作
                db.insert(DBCipherHelper.TABLE_NAME, null, cv);
                Log.e(TAG, "insertDatasByTransaction");
            }
            db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交
        } catch (Exception e) {

        } finally {
            db.endTransaction(); //处理完成
            //关闭数据库
            db.close();
        }
    }

    /**
     * 删除数据
     */
    public void deleteData(String name) {
        //生成条件语句
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(DBCipherHelper.FIELD_NAME).append(" = ").append("'").append(name).append("'");
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        // delete 操作
        int value = db.delete(DBCipherHelper.TABLE_NAME, whereBuffer.toString(), null);
        Log.d(TAG, "deleteData-value:" + value);
        //关闭数据库
        db.close();
    }

    /**
     * 删除所有数据
     */
    public void deleteDatas() {
        String sql = "delete from " + DBCipherHelper.TABLE_NAME;
        execSQL(sql);
    }

    /**
     * 更新数据
     */
    public void updateData(String name) {
        //生成条件语句
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(DBCipherHelper.FIELD_NAME).append(" = ").append("'").append(name).append("'");
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();
        cv.put(DBCipherHelper.FIELD_NAME, name + name);
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        // update 操作
        int value = db.update(DBCipherHelper.TABLE_NAME, cv, whereBuffer.toString(), null);
        Log.d(TAG, "updateData-value:" + value);
        //关闭数据库
        db.close();
    }

    /**
     * 指定条件查询数据
     */
    public void queryDatas(String name) {
        //生成条件语句
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(DBCipherHelper.FIELD_NAME).append(" = ").append("'").append(name).append("'");
        //指定要查询的是哪几列数据
        String[] columns = {DBCipherHelper.FIELD_NAME};
        //获取可读数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase(DBCipherHelper.DB_PWD);
        //查询数据库
        Cursor cursor = null;
        try {
            cursor = db.query(DBCipherHelper.TABLE_NAME, columns, whereBuffer.toString(), null, null, null, null);
            while (cursor.moveToNext()) {
                int count = cursor.getColumnCount();
                String columName = cursor.getColumnName(0);
                String tname = cursor.getString(0);
                Log.e(TAG, "count = " + count + " columName = " + columName + "  name =  " + tname);
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e(TAG, "queryDatas" + e.toString());
        }
        //关闭数据库
        db.close();
    }

    /**
     * 查询全部数据
     */
    public void queryDatas() {
        //指定要查询的是哪几列数据
        String[] columns = {DBCipherHelper.FIELD_NAME};
        //获取可读数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase(DBCipherHelper.DB_PWD);
        //查询数据库
        Cursor cursor = null;
        try {
            cursor = db.query(DBCipherHelper.TABLE_NAME, columns, null, null, null, null, null);//获取数据游标
            while (cursor.moveToNext()) {
                int count = cursor.getColumnCount();
                String columeName = cursor.getColumnName(0);//获取表结构列名
                String name = cursor.getString(0);//获取表结构列数据
                Log.d(TAG, "count = " + count + " columName = " + columeName + "  name =  " + name);
            }
            //关闭游标防止内存泄漏
            if (cursor != null) {
                cursor.close();
            }
        } catch (SQLException e) {
            Log.e(TAG, "queryDatas" + e.toString());
        }
        //关闭数据库
        db.close();
    }

    /**
     * 执行sql语句
     */
    private void execSQL(String sql) {
        //获取写数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase(DBCipherHelper.DB_PWD);
        //直接执行sql语句
        db.execSQL(sql);//或者
        //关闭数据库
        db.close();
    }

}
