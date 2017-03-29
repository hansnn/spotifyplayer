package com.hansn.spotifyplayer.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.hansn.spotifyplayer.R;

public class PlayButton extends View {

    private Paint circlePaint;
    private Paint iconPaint;
    private int width;
    private int height;
    private boolean isPlaying;

    public PlayButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.actionYellow));

        iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setStyle(Paint.Style.FILL);
        iconPaint.setColor(ContextCompat.getColor(getContext(), R.color.actionOrange));
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
        invalidate(); // redraw
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        width = w;
        height = h;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int shortestSide = Math.min(width, height);
        canvas.drawCircle(shortestSide / 2, shortestSide / 2, shortestSide / 2, circlePaint);

        if (isPlaying) {
            drawPauseIcon(canvas, shortestSide);
        } else {
            drawPlayIcon(canvas, shortestSide);
        }
    }

    private void drawPauseIcon(Canvas canvas, int shortestSide) {
        canvas.drawRect(shortestSide * 0.3f, shortestSide * 0.3f, shortestSide * 0.4f, shortestSide * 0.7f, iconPaint);
        canvas.drawRect(shortestSide * 0.6f, shortestSide * 0.3f, shortestSide * 0.7f, shortestSide * 0.7f, iconPaint);
    }

    private void drawPlayIcon(Canvas canvas, int shortestSide) {
        Path trianglePath = new Path();
        trianglePath.moveTo(shortestSide * 0.35f, shortestSide * 0.3f);
        trianglePath.lineTo(shortestSide * 0.35f, shortestSide * 0.7f);
        trianglePath.lineTo(shortestSide * 0.75f, shortestSide * 0.5f);
        trianglePath.lineTo(shortestSide * 0.35f, shortestSide * 0.3f);

        canvas.drawPath(trianglePath, iconPaint);
    }
}
