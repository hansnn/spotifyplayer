package com.hansn.spotifyplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hansn.spotifyplayer.R;

public class ProgressBar extends View {

    private Paint barPaint;
    private Paint posPaint;
    private Paint pinPaint;
    private Paint circlePaint;
    private int width;
    private int height;
    private double currentPos;
    private double leftPinPos;
    private double rightPinPos = 100;

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        posPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        posPaint.setStyle(Paint.Style.FILL);
        posPaint.setColor(ContextCompat.getColor(getContext(), R.color.lightBlue));

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
        invalidate();
    }

    public void setLeftPinPos(double leftPinPos) {
        this.leftPinPos = leftPinPos;
        invalidate();
    }

    public void setRightPinPos(double rightPinPos) {
        this.rightPinPos = rightPinPos;
        invalidate();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(10, 0, width - 10, height, barPaint);

        float currentPosX = (float) ((width / 100) * currentPos);
        float barTop = (height / 8);
        canvas.drawRect(currentPosX, barTop, currentPosX + 10, height, posPaint);

        float pinTop = (height / 10);
        float pinBottom = (height / 5);
        float leftPinPosX = (float) ((width / 100) * leftPinPos);
        canvas.drawLine(leftPinPosX, pinTop, leftPinPosX, pinBottom, pinPaint);
        canvas.drawCircle(leftPinPosX, pinTop, 5, circlePaint);

        float rightPinPosX = (float) ((width / 100) * rightPinPos);
        canvas.drawLine(rightPinPosX, pinTop, rightPinPosX, pinBottom, pinPaint);
        canvas.drawCircle(rightPinPosX, pinTop, 25, circlePaint);

    }
}
