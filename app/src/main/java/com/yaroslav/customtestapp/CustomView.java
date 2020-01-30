package com.yaroslav.customtestapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CustomView extends View {

    private static final int NUMBER_OF_CIRCLES = 8;
    private static final int NUMBER_OF_PLANES = 25;

    Bitmap raw;
    Bitmap plane;
    Paint paint;

    ArrayList<Point> total;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Paint localPaint = new Paint();
        localPaint.setColor(getResources().getColor(R.color.colorAccent));
        localPaint.setAntiAlias(true);
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setStrokeWidth(1.0F);
        localPaint.setAlpha(0);

        paint = new Paint(localPaint);
        paint.setAlpha(255);

        raw = BitmapFactory.decodeResource(getResources(), R.drawable.plane);
        total = new ArrayList<>();
    }

    private ArrayList<Point> createCoordinates(double radius, int shift) {
        int num_of_points = (int) (2 * radius * Math.PI);

        ArrayList<Point> points = new ArrayList<>();

        for (int i = 0; i < num_of_points; ++i) {
            double angle = Math.toRadians(((double) i / num_of_points) * 360d);
            points.add(new Point(
                    Math.cos(angle) * radius + shift,
                    Math.sin(angle) * radius + shift
            ));
        }

        return points;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int max_diam = Math.min(width, height);
        int p0 = max_diam / 2;
        int step = p0 / NUMBER_OF_CIRCLES;

        int radius = step;
        canvas.drawPoint(p0, p0, paint);
        for (int k = 0; k < NUMBER_OF_CIRCLES; k++) {
            canvas.drawCircle(p0, p0, radius, paint);
            radius += step;
        }

        for (int j = 0; j <= NUMBER_OF_CIRCLES; j++) {
            total.addAll(createCoordinates(step * j - step/2, p0 - step/2));
        }

        plane = Bitmap.createScaledBitmap(raw, step, step, false);
        Random random = new Random();

        for (int i = 0; i < NUMBER_OF_PLANES; i++) {
            int index = random.nextInt(total.size());
            canvas.drawBitmap(plane, total.get(index).getX(), total.get(index).getY(), null);
        }
    }

}
