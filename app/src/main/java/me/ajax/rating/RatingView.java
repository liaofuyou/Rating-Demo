package me.ajax.rating;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aj on 2018/2/11
 */

public class RatingView extends View {

    int borderCount = 6; //边数量

    float radius = 50f;

    Paint borderPaint = new Paint();
    Paint ratingPaint = new Paint();

    Path borderLinePath = new Path();
    Path axlePath = new Path();
    Path ratingPath = new Path();

    float parAngle = 0;

    float[] ratings = new float[]{0.3f, 0.9f, 0.8f, 0.5f, 0.6f, 0.7f};

    public RatingView(Context context) {
        super(context);
        init();
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {

        borderPaint.setColor(0xFFFF0000);
        borderPaint.setStrokeWidth(5);
        borderPaint.setStyle(Paint.Style.STROKE);
        ratingPaint.setColor(0xFFFF00FF);

        radius = radius * getResources().getDisplayMetrics().density;

        parAngle = 360 / borderCount;
        for (int i = 0; i < borderCount; i++) {

            float x = getXFromPolar(radius, i * parAngle);
            float y = getYFromPolar(radius, i * parAngle);

            //外边框
            if (i == 0) borderLinePath.moveTo(x, y);
            borderLinePath.lineTo(x, y);

            //轴
            axlePath.moveTo(0, 0);
            axlePath.lineTo(x, y);
        }
        borderLinePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.translate(radius, radius);


        //绘制评分内容
        for (int i = 0; i < borderCount; i++) {

            float x = getXFromPolar(radius * ratings[i], i * parAngle);
            float y = getYFromPolar(radius * ratings[i], i * parAngle);

            if (i == 0) ratingPath.moveTo(x, y);
            ratingPath.lineTo(x, y);
        }
        canvas.drawPath(ratingPath, ratingPaint);

        //绘制外边框
        canvas.drawPath(borderLinePath, borderPaint);
        //绘制轴
        canvas.drawPath(axlePath, borderPaint);

        //canvas.drawCircle(0, 0, 20, borderPaint);
    }

    /**
     * 极坐标转换为直角坐标
     */
    float getXFromPolar(float p, float angle) {
        return (float) (p * Math.cos(Math.PI * angle / 180));
    }

    /**
     * 极坐标转换为直角坐标
     */
    float getYFromPolar(float p, float angle) {
        return (float) (p * Math.sin(Math.PI * angle / 180));
    }
}
