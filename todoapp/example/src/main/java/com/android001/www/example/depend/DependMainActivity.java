package com.android001.www.example.depend;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android001.depend.zxing.utils.QRCreate;
import com.android001.www.example.R;
import com.google.zxing.WriterException;

public class DependMainActivity extends AppCompatActivity {
    ImageView ivQrCode;

    String qrUrl = "http://192.168.43.1:8888/getAppList?brand=com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depend_main);
        ivQrCode = (ImageView) findViewById(R.id.iv_qrcode);

        Bitmap bitmap = null;

        try {
            bitmap = QRCreate.encodeAsBitmap(qrUrl,520);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        ivQrCode.setImageBitmap(bitmap);
    }
}
