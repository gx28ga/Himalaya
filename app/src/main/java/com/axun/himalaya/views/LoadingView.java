package com.axun.himalaya.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.axun.himalaya.R;

public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {
    private int rotateDegree = 0;
    private boolean mNeedRotate = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 设置图标
        setImageResource(R.mipmap.loading);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(rotateDegree, getWidth() / 2, getHeight() / 2);

        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mNeedRotate = true;
        post(new Runnable() {
            @Override
            public void run() {
                rotateDegree += 10;
                rotateDegree = rotateDegree <= 360 ? rotateDegree : 0;
                invalidate();
                if (mNeedRotate) {
                    postDelayed(this, 20);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mNeedRotate = false;
    }
}
