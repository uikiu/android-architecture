package com.android001.www.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android001.ndk.HelloJNI;
import com.android001.www.example.R;
import com.orhanobut.logger.Logger;

public class JNIActivity extends AppCompatActivity {

    Button btnJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        btnJni = (Button) findViewById(R.id.helloJni);
        btnJni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("android001",HelloJNI.javaStaticCallJniString());
            }
        });
    }
}
