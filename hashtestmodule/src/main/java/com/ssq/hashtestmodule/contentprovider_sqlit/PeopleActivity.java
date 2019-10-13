package com.ssq.hashtestmodule.contentprovider_sqlit;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssq.hashtestmodule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Mr.Shen
 * Date : 2019/10/13 18:35
 * Description :
 */
public class PeopleActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    SQLiteDatabase db;
    MyContentProvider myContentProvider;
    ListView listView;
    List<PeopleBeen> mPeopleBeens;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        listView = findViewById(R.id.list);
        mPeopleBeens = new ArrayList<>();
        myDatabaseHelper = new MyDatabaseHelper(getApplicationContext(), "data", null, 1);
        //得到可以读写数据库的SQLiteDatabase
        db = myDatabaseHelper.getWritableDatabase();
        PeopleBeen p;
        for (int i = 0; i < 10; i++) {
            p = new PeopleBeen();
            p.setNum(10 + i);
            p.setName("王" + i);
            p.setSex((i % 3 == 0) ? "男" : "女");
            ContentValues values = new ContentValues();
            values.put("num", p.getNum());
            values.put("name", p.getName());
            values.put("sex", p.getSex());
            db.insert("people", null, values);
        }

        Cursor cursor = db.query("people", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            PeopleBeen peopleBeen = new PeopleBeen();
            peopleBeen.setNum(cursor.getInt(cursor.getColumnIndex("num")));
            peopleBeen.setName(cursor.getString(cursor.getColumnIndex("name")));
            peopleBeen.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            mPeopleBeens.add(peopleBeen);
        }
        //mMyContentProvider=new MyContentProvider();
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_expandable_list_item_1, getData()));

    }

    private List<String> getData() {
        List<String> list = new ArrayList<String>();
        for (PeopleBeen p : mPeopleBeens) {
            list.add(p.getNum() + "   名字：" + p.getName() + "   性别：" + p.getSex());
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
