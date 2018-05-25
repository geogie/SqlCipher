package com.georgeren.sqlcipher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.georgeren.sqlcipher.aes.AesUtils;
import com.georgeren.sqlcipher.db.DBCipherManager;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
        findViewById(R.id.btnUpdate).setOnClickListener(this);
        findViewById(R.id.btnSearch).setOnClickListener(this);

    }

    private void InitializeSQLCipher() {
        SQLiteDatabase.loadLibs(this);
        File databaseFile = getDatabasePath("demo.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFile, "test123", null);
        database.execSQL("create table t1(a, b)");
        database.execSQL("insert into t1(a, b) values(?, ?)", new Object[]{"one for the money",
                "two for the show"});
    }

    private void initAES() {
        //FastJson生成json数据
        String jsonData = "123BCD";
        Log.e(TAG, "AES加密前json数据 ---->" + jsonData);
        Log.e(TAG, "AES加密前json数据长度 ---->" + jsonData.length());

        //生成一个动态key
//        String secretKey = AesUtils.generateKey();
        String secretKey = "firstleap";
        Log.e(TAG, "AES动态secretKey ---->" + secretKey);

        //AES加密
        long start = System.currentTimeMillis();
        String encryStr = AesUtils.encrypt(secretKey, jsonData);
        long end = System.currentTimeMillis();
        Log.e(TAG, "AES加密耗时 cost time---->" + (end - start));
        Log.e(TAG, "AES加密后json数据 ---->" + encryStr);
        Log.e(TAG, "AES加密后json数据长度 ---->" + encryStr.length());

        //AES解密
        start = System.currentTimeMillis();
        String decryStr = AesUtils.decrypt(secretKey, encryStr);
        end = System.currentTimeMillis();
        Log.e(TAG, "AES解密耗时 cost time---->" + (end - start));
        Log.e(TAG, "AES解密后json数据 ---->" + decryStr);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                Log.d(TAG, "清空数据");
                //清空数据
                DBCipherManager.getInstance(MainActivity.this).deleteDatas();
                break;
            case R.id.btnInsert:
                Log.d(TAG, "插入数据");
                //插入数据
                for (int i = 0; i < 10; i++) {
                    DBCipherManager.getInstance(MainActivity.this).insertData(String.valueOf(i));
                }
                break;
            case R.id.btnDelete:
                Log.d(TAG, "删除数据");
                //删除数据
                DBCipherManager.getInstance(MainActivity.this).deleteData(String.valueOf(5));
                break;
            case R.id.btnUpdate:
                Log.d(TAG, "更新数据");
                //更新数据
                DBCipherManager.getInstance(MainActivity.this).updateData(String.valueOf(3));
                break;
            case R.id.btnSearch:
                Log.d(TAG, "查询数据");
                //查询数据
                DBCipherManager.getInstance(MainActivity.this).queryDatas();
                break;
            case R.id.btnAES:
                initAES();
                break;
            default:
                break;
        }
    }
}
