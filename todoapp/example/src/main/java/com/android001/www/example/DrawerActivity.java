package com.android001.www.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

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
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_Layout);
        mDrawerNavigation = (NavigationView) findViewById(R.id.drawer_Navigation);
    }
}
