package com.lintex9527.androidworld.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * 注意RecyclerView控件是在 com.android.support:recyclerview-v7中的
 * 需要在当前模块的build.gradle中的dependencies闭包中添加依赖。
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
