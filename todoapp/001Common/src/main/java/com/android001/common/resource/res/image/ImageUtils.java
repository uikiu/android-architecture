package com.android001.common.resource.res.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.android001.common.app.AppHolder;

import static android.R.attr.bitmap;

/**
* class design：
* @author android001
* created at 2017/6/21 下午4:44
*/

public class ImageUtils {


    public static Bitmap resouce2Bitmap(int resoureID){
       return BitmapFactory.decodeResource(AppHolder.getContext().getResources(),resoureID);
    }

    public static Bitmap drawableToBitamp(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }


}


