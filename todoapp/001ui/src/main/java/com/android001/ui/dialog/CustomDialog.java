package com.android001.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android001.ui.measure.MetricConvert;

/**
 * class design：
 *
 * @author android001
 *         created at 2017/6/14 下午4:18
 *         1. Context
 *         2. R.style.XXXX 样式
 *         3. R.layout.XXXX layout布局
 *         setCanceledOnTouchOutside(XXXX);是否允许点击外部区域来让Dialog消失
 *         WindowManager.LayoutParams对象类的height和width。自定义Dialog布局的大小
 *         自定义布局上各个View的点击事件
 */

public class CustomDialog extends Dialog
{
    private Context context;
    private int height, width;
    private boolean cancelTouchout;
    private View view;

    private CustomDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }

    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        context = builder.context;
        height = builder.height;
        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = height;
        lp.width = width;
        win.setAttributes(lp);
    }

    /**
     * 我这边的Builder中对height和width写了三种方式:
     * 直接写入px的值就调用heightpx(),如果直接写入dp值，就调用heightdp()。
     * 不过最多的应该还是调用heightDimenRes()方法。因为一般我们在写自定义layout布局的时候，height和width的数值肯定是去dimen.xml中获取。
     * 所以我们在代码中生成这个自定义对话框的时候,也就直接调用了heightDimenRes(R.dimen.XXX)
     */

    public static final class Builder
    {
        private Context context;
        private int height,width;//自定义布局的大小
        private boolean cancelTouchout;//点击dialog的外部区域是否允许dialog消失
        private View view;//load的layout布局
        private int resStyle = -1;

        public Builder (Context context)
        {
            this.context = context;
        }

        public Builder view (int resView)
        {
            view = LayoutInflater.from(context).inflate(resView,null);
            return this;
        }

        public Builder heightpx(int val)
        {
            height = val;
            return this;
        }

        public Builder widthpx(int val)
        {
            width = val;
            return this;
        }

        public Builder heightdp(int val)
        {
            height = MetricConvert.dip2px(context,val);
            return this;
        }

        public Builder widthdp(int val)
        {
            width = MetricConvert.dip2px(context,val);
            return this;
        }

        public Builder heightDimenRes(int dimenRes)
        {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes)
        {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style (int resStyle)
        {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout (boolean val)
        {
            cancelTouchout = val;
            return this;
        }

        public Builder addViewOnClick(int viewRes,View.OnClickListener listener)
        {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public CustomDialog build()
        {
            if (resStyle!=-1)
            {
                return new CustomDialog(this,resStyle);
            } else
            {
                return new CustomDialog(this);
            }
        }

    }



}
