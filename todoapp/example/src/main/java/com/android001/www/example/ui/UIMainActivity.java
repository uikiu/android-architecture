package com.android001.www.example.ui;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.android001.www.example.R;

public class UIMainActivity extends AppCompatActivity {

    private TextView tvStep, tvNoteQRCode,tvNoteInstall;
    private ImageView ivQRCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uimain);
        init();
    }

    private void init() {
        tvStep = (TextView) findViewById(R.id.ui_tvstep);
        tvNoteQRCode = (TextView) findViewById(R.id.tv_note_qrcode);
        ivQRCode = (ImageView) findViewById(R.id.iv_qrcode);
        tvNoteInstall = (TextView) findViewById(R.id.ui_tvnote_install);
        tvStep.setText(SpannableStringSample.getSpannableStep());
        tvNoteQRCode.setText(SpannableStringSample.getSpannableNoteQRCode());
        tvNoteInstall.setText(SpannableStringSample.getSpannableNoteInstallMode());
    }


}
