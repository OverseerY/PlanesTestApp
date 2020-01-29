package com.yaroslav.customtestapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class CustomView extends View {

    private final int POINT_ARRAY_SIZE = 25;
    private static final int NUMBER_OF_CIRCLES = 10;
    private static final int NUMBER_OF_PLANES = 100;



    Bitmap raw = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
    Bitmap plane;// = Bitmap.createScaledBitmap(raw, step - 2, step - 2, false);
    Paint[] paints = new Paint[POINT_ARRAY_SIZE];


    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Paint localPaint = new Paint();
        localPaint.setColor(getResources().getColor(R.color.colorAccent));
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1.0F);
        localPaint.setAlpha(0);

        int alpha_step = 255 / POINT_ARRAY_SIZE;
        for (int i = 0; i < paints.length; i++) {
            paints[i] = new Paint(localPaint);
            paints[i].setAlpha(255 - (i* alpha_step));
        }
    }

    public void debugInfo() {
        final int NUM_POINTS = 1000;
        final double RADIUS = 100d;

        final Point[] points = new Point[NUM_POINTS];

        for (int i = 0; i < NUM_POINTS; ++i)
        {
            final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);

            points[i] = new Point(
                    Math.cos(angle) * RADIUS,
                    Math.sin(angle) * RADIUS
            );
        }

        Log.i("TEST", "Array: " + Arrays.toString(points));
    }

    private Point[] createCoordinates(double radius) {
        final int NUM_POINTS = 1000;

        final Point[] points = new Point[NUM_POINTS];

        for (int i = 0; i < NUM_POINTS; ++i)
        {
            final double angle = Math.toRadians(((double) i / NUM_POINTS) * 360d);

            points[i] = new Point(
                    Math.cos(angle) * radius,
                    Math.sin(angle) * radius
            );
        }
        return points;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint localPaint = paints[0];

        int width = getWidth();
        int height = getHeight();

        int diam = Math.min(width, height);
        int p0 = diam / 2;
        int step = p0 / NUMBER_OF_CIRCLES;
        int radius = step;

        canvas.drawPoint(p0, p0, localPaint);
        for (int k = 0; k < NUMBER_OF_CIRCLES; k++) {
            canvas.drawCircle(p0, p0, radius, localPaint);
            radius += step;
        }

        plane = Bitmap.createScaledBitmap(raw, step - 2, step - 2, false);
        //canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_PLANES; i++) {
            canvas.drawBitmap(plane, random.nextInt(diam), random.nextInt(diam), null);
        }

        //canvas.drawBitmap(plane, 0, 0, null);
    }

}
