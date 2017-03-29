package com.hansn.spotifyplayer.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hansn.spotifyplayer.R;

public class ProgressBarPin extends View{

    private Paint pinPaint;
    private Paint circlePaint;
    private int width;
    private int height;
    private double currentPos;

    public ProgressBarPin(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        pinPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pinPaint.setStyle(Paint.Style.STROKE);
        pinPaint.setStrokeWidth(10);
        pinPaint.setColor(ContextCompat.getColor(getContext(), R.color.actionYellow));

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.actionOrange));
    }

    public void setCurrentPos(double currentPos) {
        this.currentPos = currentPos;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float currentPosX = (float)((width / 100) * currentPos);
        canvas.drawLine(currentPosX, height / 10, currentPosX, height / 5, pinPaint);

        canvas.drawCircle(currentPosX, height / 10, 5, circlePaint);
    }
}
