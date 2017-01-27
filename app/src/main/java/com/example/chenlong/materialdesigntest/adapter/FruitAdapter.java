package com.example.chenlong.materialdesigntest.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenlong.materialdesigntest.FruitActivity;
import com.example.chenlong.materialdesigntest.R;
import com.example.chenlong.materialdesigntest.bean.Fruit;

import java.util.List;

/**
 * Created by ChenLong on 2017/1/27.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder>
{
    private List<Fruit> fruitList;

    public FruitAdapter(List<Fruit> fruitList)
    {
        this.fruitList = fruitList;
    }

    private Context mContext;

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (mContext == null)
        {
            mContext = parent.getContext();
        }
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fruit, parent, false);

        FruitViewHolder fruitViewHolder = new FruitViewHolder(inflate);

        /**
         * 点击跳转事件  也可以在bind中写有现成的position  但是这里也可以用
         */
        fruitViewHolder.cardView.setOnClickListener(v -> {
            int adapterPosition = fruitViewHolder.getAdapterPosition();
            Fruit fruit = fruitList.get(adapterPosition);
            FruitActivity.startFruitActivity(mContext, fruit.getName(), fruit.getImageId());
        });
        return fruitViewHolder;
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position)
    {
        Fruit fruit = fruitList.get(position);
        Glide.with(mContext)
                .load(fruit.getImageId())
                .centerCrop()
                .into(holder.imageView);

        holder.textView.setText(fruit.getName());
    }

    @Override
    public int getItemCount()
    {
        return fruitList == null ? 0 : fruitList.size();
    }

    static class FruitViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public FruitViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.fruit_card);
            imageView = (ImageView) itemView.findViewById(R.id.fruit_image);
            textView = (TextView) itemView.findViewById(R.id.fruit_name);
        }
    }
}
