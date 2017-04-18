package com.android001.www.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by xixionghui on 2017/3/31.
 */

public class DrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initView();
    }

    /**
     * 初始化View
     * 1. 设置Toolbar
     * 2. 初始化抽屉布局和抽屉导航
     */
    private void initView() {
        // 1.设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        // 2.初始化抽屉布局和抽屉导航
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerNavigation = (NavigationView) findViewById(R.id.drawer_Navigation);
    }


}
