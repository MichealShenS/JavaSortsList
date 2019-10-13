package com.ssq.hashtestmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shareM();
    }

    public static void main(String[] args) {
        String s = "string";
        int i = s.hashCode();
        if (s.equals("11"))
            System.out.println(i);
    }

    private void shareM() {
        //获取SharedPreferences对象
        SharedPreferences sp = this.getSharedPreferences("SSQ", MODE_PRIVATE);
        //存入数据
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putString("STRING_KEY", "string1");
        editor.putInt("INT_KEY", 0);
        editor.putBoolean("BOOLEAN_KEY", true);
        editor.commit();
        System.out.println(sp.getString("STRING_KEY", "none"));
        System.out.println(sp.getString("FF", "none"));
    }
}
