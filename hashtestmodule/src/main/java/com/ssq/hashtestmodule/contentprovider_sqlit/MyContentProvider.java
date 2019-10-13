package com.ssq.hashtestmodule.contentprovider_sqlit;

import android.app.slice.Slice;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Author : Mr.Shen
 * Date : 2019/10/13 18:13
 * Description :
 */
public class MyContentProvider extends ContentProvider {

    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase db;
    UriMatcher uriMatcher;
    Uri PEO = Uri.parse("content://test_contentProvider/people");
    static final String tableName = "people";

    public MyContentProvider() {
    }

    //用于匹配传入的URI，如果不匹配返回-1
    public int getUriMatcher(Uri uri) {
        return uriMatcher.match(uri);
    }

    @Override
    public boolean onCreate() {
        //打开数据库，得到db
        myDatabaseHelper = new MyDatabaseHelper(getContext(), "data", null, 1);
        //得到可以读写数据库的SQLiteDatabase
        db = myDatabaseHelper.getWritableDatabase();
        //UriMatcher.NO_MATCH表示不匹配任何Uri的返回码-1
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //添加一个匹配规则：第一个参数contentProvider的authority值，第二个参数数据表名，第三个参数 匹配后返回值
        uriMatcher.addURI("test_contentProvider", tableName, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //查询数据
        if (getUriMatcher(uri) == 1) {
            Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, sortOrder);
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //判断传入的URI是否匹配，匹配的话将数据插入数据库
        if (getUriMatcher(uri) == 1) {
            db.insert(tableName, null, values);
            getContext().getContentResolver().notifyChange(PEO, null);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        //删除数据
        if (getUriMatcher(uri) == 1) {
            db.delete(tableName, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(PEO, null);
            return 1;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        //更新数据
        if (getUriMatcher(uri) == 1) {
            db.update(tableName, values, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(PEO, null);
            return 1;
        }
        return 0;
    }

}
