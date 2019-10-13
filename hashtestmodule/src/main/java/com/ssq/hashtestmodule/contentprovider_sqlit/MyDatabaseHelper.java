package com.ssq.hashtestmodule.contentprovider_sqlit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Author : Mr.Shen
 * Date : 2019/10/13 18:05
 * Description :
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1001;

    private static MyDatabaseHelper instance = null;

//    public static MyDatabaseHelper getInstance(){
//        if (instance == null){
//            synchronized (MyDatabaseHelper.class){
//                if (instance == null){
//                    instance = new MyDatabaseHelper();
//                }
//            }
//        }
//        return instance;
//    }

    //sql语句，用来创建表。
    public static final String People_Message = "create table people("
            + "num integer primary key autoincrement,"
            + "name text,"
            + "sex text)";

    Context mContext;


    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //调用父类构造方法，创建数据库。
        // 第一个参数是context。
        // 第二个参数是数据库的名字，
        // 第三个参CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类，
        // 第四个参数是数据库的版本号，不能小于一。
        super(context, name, factory, version);
        mContext = context;
    }

    //
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表,执行sql语句
        db.execSQL(People_Message);

        // 若不是第一个版本安装，直接执行数据库升级
        // 请不要修改FIRST_DATABASE_VERSION的值，其为第一个数据库版本大小
        final int FIRST_DATABASE_VERSION = 1000;
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
    }

    /************************** 数据库升级处理 *******************************/

    public static final String T_FAVORITE = "favorite";
    public static final String CREATE_TABLE_FAVORITE =
            "CREATE TABLE IF NOT EXISTS " + T_FAVORITE + "(" +
                    "id VARCHAR PRIMARY KEY, " +
                    "title VARCHAR, " +
                    "url VARCHAR, " +
                    "createDate VARCHAR " +
                    ")";


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果需要升级数据库，会调用该方法。可以用来删除表或者添加表。
        // 使用for实现跨版本升级数据库
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1000:
                    upgradeToVersion1001(db);
                    break;
                case 1001:
                    upgradeToVersion1002(db);
                    break;
                default:
                    break;
            }

        }
    }


    private void upgradeToVersion1001(SQLiteDatabase db) {
        // favorite表新增1个字段
        String sql1 = "ALTER TABLE " + T_FAVORITE + " ADD COLUMN deleted VARCHAR";
        db.execSQL(sql1);
    }

    private void upgradeToVersion1002(SQLiteDatabase db) {
        // favorite表新增2个字段,添加新字段只能一个字段一个字段加，sqlite有限制不予许一条语句加多个字段
        String sql1 = "ALTER TABLE " + T_FAVORITE + " ADD COLUMN message VARCHAR";
        String sql2 = "ALTER TABLE " + T_FAVORITE + " ADD COLUMN type VARCHAR";
        db.execSQL(sql1);
        db.execSQL(sql2);
    }
}
