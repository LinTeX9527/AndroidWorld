package com.lintex9527.androidworld.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * 注意RecyclerView控件是在 com.android.support:recyclerview-v7中的
 * 需要在当前模块的build.gradle中的dependencies闭包中添加依赖。
 *
 */
public class MainActivity extends AppCompatActivity {

    private List<Fruit> mFruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits(); // 初始化水果数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(mFruitList);
        recyclerView.setAdapter(adapter);
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            mFruitList.add(apple);

            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
            mFruitList.add(banana);

            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
            mFruitList.add(orange);

            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
            mFruitList.add(watermelon);

            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
            mFruitList.add(pear);

            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
            mFruitList.add(grape);

            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
            mFruitList.add(pineapple);

            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
            mFruitList.add(strawberry);

            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
            mFruitList.add(cherry);

            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
            mFruitList.add(mango);
        }
    }
}