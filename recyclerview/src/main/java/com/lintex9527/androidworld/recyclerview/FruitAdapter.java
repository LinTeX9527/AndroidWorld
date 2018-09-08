package com.lintex9527.androidworld.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LinTeX9527 on 2018/9/2.
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    // 这个适配器要管理的数据的集合的引用
    private List<Fruit> mFruitList;


    /**
     * 找控件
     * ViewHolder 中保留了要显示一个Fruit实例的UI元素的句柄
     * 例如显示Fruit的图片和名字，就保留了ImageView 和 TextView的句柄
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitImage = (ImageView) itemView.findViewById(R.id.fruit_image);
            fruitName = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }


    /**
     * 通过构造方法绑定要管理的数据集合
     * @param fruits
     */
    public FruitAdapter(List<Fruit> fruits) {
        mFruitList = fruits;
    }


    /**
     * 加载布局，创建ViewHolder实例
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    /**
     * 由RecyclerView来调用，显示指定位置的数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }


    /**
     * 返回这个适配器管理的数据的总个数
     * @return
     */
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
