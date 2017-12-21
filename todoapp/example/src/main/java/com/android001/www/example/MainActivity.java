package com.android001.www.example;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.android001.ndk.HelloJNI;
import com.android001.www.example.depend.DependMainActivity;
import com.android001.www.example.ui.UIMainActivity;
import com.android001.www.example.utils.OpenManager;
import com.orhanobut.logger.Logger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUi, btnDepend, btnShell, btnLock, btnJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //---------

    private void initView() {
        btnUi = (Button) findViewById(R.id.ui);
        btnUi.setOnClickListener(this);
        btnDepend = (Button) findViewById(R.id.depend);
        btnDepend.setOnClickListener(this);

        btnShell = (Button) findViewById(R.id.shell);
        btnShell.setOnClickListener(this);

        btnLock = (Button) findViewById(R.id.lock);
        btnLock.setOnClickListener(this);

        btnJni = (Button) findViewById(R.id.jni);
        btnJni.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ui:
                intent = new Intent(this, UIMainActivity.class);
                break;
            case R.id.depend:
                intent = new Intent(this, DependMainActivity.class);
                break;
            case R.id.shell:
                intent = new Intent(this, ShellActivity.class);
                break;
            case R.id.lock:
//                ScreenManager.setLockScreen();
                OpenManager.isAllowMockLocation(this);
                break;
            case R.id.jni:
                intent = new Intent(this, JNIActivity.class);
                break;
            default:
                break;
        }

        startActivity(intent);
    }
}
