package com.example.chenlong.materialdesigntest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.chenlong.materialdesigntest.adapter.FruitAdapter;
import com.example.chenlong.materialdesigntest.bean.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToolBarActivity extends AppCompatActivity
{

    private DrawerLayout mDrawer;
    private NavigationView mNav;
    private FloatingActionButton mFab;
    private RecyclerView mRecycler;
    private FruitAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private List<Fruit> fruitList = new ArrayList<>();

    /**
     * 有点小细节
     * AppbarLayout 不能单独使用 必须是在CoordinatorLayout的子布局
     * 同理 collapsingToolBarLayout 必须也是AppBarLayout的子布局 不能单独使用
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置一个toolBar添加到activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        //拿到actionBar 修改一个设置样式
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            //其实android内置默认的导航上有一个返回的箭头图标 就是HomeAsUp按钮 id是android.R.id.home
            actionBar.setDisplayHomeAsUpEnabled(true); //修改设置开启显示
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //修改设置显示的图标
        }

        /**
         * 初始化控件
         */
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(v -> {
            /**
             * 第一个参数传递一个view  随便当前布局哪个都行 他会去找最外层的view 当作自己的载体
             * 第二个参数 就是显示的内容
             * 第三个参数 显示的时长
             */
            Snackbar.make(v, "即将删除数据", Snackbar.LENGTH_INDEFINITE)
                    /**
                     * 这里又提供了一个交互的按钮 用于操作
                     */
                    .setAction("点击消失", v1 -> {
                        Toast.makeText(this, "保存了数据", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });

        mNav = (NavigationView) findViewById(R.id.navigation);
        //设置一个默认选中项
        mNav.setCheckedItem(R.id.nav_call);
        /**
         * 侧滑出的抽屉的简单点击事件
         */
        View headerView = mNav.getHeaderView(0);
        headerView.findViewById(R.id.header_icon)
                .setOnClickListener(v ->
                        Toast.makeText(ToolBarActivity.this, "我点了头像", Toast.LENGTH_SHORT).show()
                );
        //设置点击事件
        mNav.setNavigationItemSelectedListener(item -> {
            mDrawer.closeDrawers(); //简单的点击关闭功能
            return true;
        });

        initRecycler();
        initSwipe();
    }

    /**
     * 初始化刷新
     */
    private void initSwipe()
    {
        mSwipe.setColorSchemeColors(Color.RED, Color.GREEN);
        mSwipe.setOnRefreshListener(() -> {
            refreshFruit(); //执行刷新
        });
    }

    /**
     * 模拟刷新的数据
     */
    private void refreshFruit()
    {
        new Thread(() -> {
            try
            {
                Thread.sleep(2000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                initFruits();
                mAdapter.notifyDataSetChanged();
                mSwipe.setRefreshing(false);        //刷新完数据后关闭下拉
            });
        }).start();
    }


    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple)
            , new Fruit("Banana", R.drawable.banana)
            , new Fruit("Orange", R.drawable.orange)
            , new Fruit("Watermelon", R.drawable.watermelon)
            , new Fruit("Pear", R.drawable.pear)
            , new Fruit("Grape", R.drawable.grape)
            , new Fruit("Pineapple", R.drawable.pineapple)
            , new Fruit("Strawberry", R.drawable.strawberry)
            , new Fruit("Cherry", R.drawable.cherry)
            , new Fruit("Mango", R.drawable.mango)};

    /**
     * 初始化RecyclerView操作
     */
    private void initRecycler()
    {
        initFruits();
        mAdapter = new FruitAdapter(fruitList);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(mAdapter);
    }

    /**
     * 初始化数据 从数组的角标 随机添加50个元素
     */
    private void initFruits()
    {
        fruitList.clear();
        for (int i = 0; i < 50; i++)
        {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //todo step1 创建一个xxx.xml文件在res的menu包下
    //todo step2 将布局的文件打气到activity中
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //参数1 表示指定哪个资源文件来创建菜单
        //参数2 表示我们的菜单选项将被添加到哪一个Menu对象中
        //return true 表示允许显示前面创建的菜单  false则不允许
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //todo step3 响应menu菜单的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.backup:
                Toast.makeText(this, "点击了上传", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "点击了删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                //设置点击后弹出
                mDrawer.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
