package com.lintex9527.androidworld.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by LinTeX9527 on 2018/9/2.
 *
 * RecyclerView 的点击事件监听器需要在 FruitAdapter 中实现，可以在方法 onCreateViewHolder()
 * 或者onBindViewHolder() 中实现。（这个有别于ListView）
 * 它比ListView要强大，因为ListView只能响应子项的点击事件，不能单独地响应子项中某个单独的控件的点击事件。
 *
 * 在这里单独为子项的ImageView注册了点击事件监听器。
 * 也为整个子项布局注册了点击事件监听器。
 *
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
        View fruitView; // 子项布局的最外层的布局
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            fruitView = itemView;
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
        final Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());

        // 在这里为单个的子项注册监听器
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "你点击了" + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "图片" + fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
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
