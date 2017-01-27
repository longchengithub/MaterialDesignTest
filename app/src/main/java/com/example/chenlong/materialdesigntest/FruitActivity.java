package com.example.chenlong.materialdesigntest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FruitActivity extends AppCompatActivity
{

    private static final String FRUIT_NAME = "name";
    private static final String FRUIT_ID = "id";

    @BindView(R.id.fruit_image)
    ImageView mFruitImage;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout mCollapsing;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.fruit_des)
    TextView mFruitDes;
    @BindView(R.id.fab_fruit)
    FloatingActionButton mFabFruit;
    private String fruit_name;
    private int fruit_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       /* if (Build.VERSION.SDK_INT >= 21)    代码实现透明的状态栏 也就沉浸式
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }*/
        setContentView(R.layout.activity_fruit);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        fruit_name = intent.getStringExtra(FRUIT_NAME);
        fruit_id = intent.getIntExtra(FRUIT_ID, 0);

        //将toolBar添加到activity
        setSupportActionBar(mToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //加载图片
        mCollapsing.setTitle(fruit_name);
        Glide.with(this)
                .load(fruit_id)
                .centerCrop()
//                .placeholder()  设置加载显示完毕前的占位图片
                .crossFade()
                .into(mFruitImage);

        //设置文本
        mFruitDes.setText(getRandomText());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * 随机生成文本填充
     *
     * @return
     */
    private String getRandomText()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++)
        {
            sb.append(fruit_name);
            Random random = new Random();
            int length = random.nextInt(3);
            for (int i1 = 0; i1 < length; i1++)
            {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static void startFruitActivity(Context context, String fruit_name, int fruit_id)
    {
        Intent intent = new Intent(context, FruitActivity.class);
        intent.putExtra(FRUIT_NAME, fruit_name);
        intent.putExtra(FRUIT_ID, fruit_id);
        context.startActivity(intent);
    }
}
