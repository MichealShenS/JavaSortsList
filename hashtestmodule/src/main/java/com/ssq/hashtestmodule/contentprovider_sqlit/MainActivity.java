package com.ssq.hashtestmodule.contentprovider_sqlit;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssq.hashtestmodule.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editAge, editName, editSex;
    private TextView textQuery, textInsert, textUpdate, textDelete, showTextView;

    ContentResolver mContentResolver;
    PersonOberserver personOberserver;
    Uri PEO = Uri.parse("content://test_contentProvider/people");
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //初始化ContentResolver，用来与ContentProvider建立联系
        mContentResolver = getContentResolver();
        //初始化ContentOberserver，用来监听指定URI数据的改变。
        personOberserver = new PersonOberserver(mHandler);
        //注册监听，当指定URI发生改变时，personOberserver中的onChange()会得到执行。
        mContentResolver.registerContentObserver(PEO, true, personOberserver);
    }

    private void initView() {
        editAge = findViewById(R.id.editAge);
        editName = findViewById(R.id.editName);
        editSex = findViewById(R.id.editSex);
        textQuery = findViewById(R.id.textQuery);
        textInsert = findViewById(R.id.textInsert);
        textUpdate = findViewById(R.id.textUpdate);
        textDelete = findViewById(R.id.textDelete);
        showTextView = findViewById(R.id.showTextView);

        textQuery.setOnClickListener(this);
        textInsert.setOnClickListener(this);
        textUpdate.setOnClickListener(this);
        textDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textInsert:
//                Toast.makeText(MainActivity.this, "查询", Toast.LENGTH_SHORT).show();
                //插入数据
                ContentValues contentValues = new ContentValues();
                contentValues.put("num", Integer.parseInt(editAge.getText().toString()));
                contentValues.put("name", editName.getText().toString());
                contentValues.put("sex", editSex.getText().toString());
                mContentResolver.insert(PEO, contentValues);
                break;
            case R.id.textQuery:
//                Toast.makeText(MainActivity.this, "插入", Toast.LENGTH_SHORT).show();
                //根据编号查询数据
                showTextView.setText("");
                Cursor cursor = mContentResolver.query(PEO, null, "num=?",
                        new String[]{editAge.getText().toString()}, null);
                while (cursor.moveToNext()) {
                    showTextView.setText("编号:" + cursor.getInt(cursor.getColumnIndex("num")) +
                            "   姓名:" + cursor.getString(cursor.getColumnIndex("name")) +
                            "   性别:" + cursor.getString(cursor.getColumnIndex("sex")));
                }
                break;
            case R.id.textDelete:
//                Toast.makeText(MainActivity.this, "修改", Toast.LENGTH_SHORT).show();
                //根据编号删除数据
                int result = mContentResolver.delete(PEO, "num=?", new String[]{editAge.getText().toString()});
                break;
            case R.id.textUpdate:
//                Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_SHORT).show();
                //根据编号修改数据
                ContentValues values = new ContentValues();
                values.put("name", editName.getText().toString());
                values.put("sex", editSex.getText().toString());
                mContentResolver.update(PEO, values, "num=?", new String[]{editAge.getText().toString()});
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentResolver.unregisterContentObserver(personOberserver);
    }

    class PersonOberserver extends ContentObserver {

        Handler mHandler;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public PersonOberserver(Handler handler) {
            super(handler);
            mHandler = handler;
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Message message = new Message();
            mHandler.sendMessage(message);
        }
    }
}
