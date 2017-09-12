package com.georgeren.sqlcipher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.georgeren.sqlcipher.db.DBCipherManager;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnClear:
                Log.d(TAG,"清空数据");
                //清空数据
                DBCipherManager.getInstance(MainActivity.this).deleteDatas();
                break;
            case R.id.btnInsert:
                Log.d(TAG,"插入数据");
                //插入数据
                for (int i = 0; i < 10; i++) {
                    DBCipherManager.getInstance(MainActivity.this).insertData(String.valueOf(i));
                }
                break;
            case R.id.btnDelete:
                Log.d(TAG,"删除数据");
                //删除数据
                DBCipherManager.getInstance(MainActivity.this).deleteData(String.valueOf(5));
                break;
            case R.id.btnUpdate:
                Log.d(TAG,"更新数据");
                //更新数据
                DBCipherManager.getInstance(MainActivity.this).updateData(String.valueOf(3));
                break;
            case R.id.btnSearch:
                Log.d(TAG,"查询数据");
                //查询数据
                DBCipherManager.getInstance(MainActivity.this).queryDatas();
                break;
        }
    }
}
