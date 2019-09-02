package com.ancroid.work.clockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ClockView extends View {

    private Paint secondPaint;
    private Paint minutePaint;
    private Path criclePath;
    private int secondAngel;
    private int minuteAngel;
    private int hourAngel;
    private int second;
    private int minute;
    private int hour;

    private Handler handler = new Handler();
    private Paint circlePaint;
    private Timer timer;
    private Paint pointPaint;
    private TimerTask timerTask;
    private int defaultSize = 300;
    private int mWidth;
    private int mHeight;
    private int strokeWidth ;
    private int clockColor = Color.BLACK;
    private int secondColor = Color.RED;
    private int minuteColor = Color.BLACK;
    private int circleCenter;


    public ClockView(Context context) {
        this(context,null,0);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        strokeWidth = dip2px(context,2);
        circleCenter = dip2px(context,3);

        initView();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        circlePaint.setStrokeWidth(strokeWidth);
        secondPaint.setStrokeWidth(strokeWidth);
        minutePaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setClockColor(int clockColor) {
        this.clockColor = clockColor;
        circlePaint.setColor(clockColor);
        invalidate();
    }

    public void setCircleCenter(int circleCenter) {
        this.circleCenter = circleCenter;
    }

    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
        secondPaint.setColor(secondColor);
        invalidate();
    }

    public void setMinuteColor(int minuteColor) {
        this.minuteColor = minuteColor;
        minutePaint.setColor(minuteColor);
        invalidate();
    }

    private void initView() {

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(circleCenter);
        pointPaint.setAntiAlias(true);

        secondPaint = new Paint();
        secondPaint.setColor(Color.RED);
        secondPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        secondPaint.setStrokeWidth(strokeWidth);
        secondPaint.setAntiAlias(true);

        minutePaint = new Paint();
        minutePaint.setColor(Color.BLACK);
        minutePaint.setAntiAlias(true);
        minutePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        minutePaint.setStrokeWidth(strokeWidth);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStrokeWidth(strokeWidth);

        criclePath = new Path();

        //获取当前系统时间
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        //判断指针处于什么角度,1秒6°，1分钟6°，1小时30°,分针12°时针走1°
        if (hour >= 12) {
            hour -= 12;
        }

        secondAngel = second * 6;
        minuteAngel = minute * 6;
        hourAngel = hour * 30 + (minute / 2 * 1);

        timer = new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(r);
            }
        };

        timer.schedule(timerTask,0,1000);

    }

    public void stop(){

        handler.removeCallbacks(r);
        if (timer!=null){
            timer.cancel();
        }if (timerTask!=null){
            timerTask.cancel();
        }
        timerTask=null;
        timer=null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getSize(widthMeasureSpec, defaultSize);
        mHeight = getSize(heightMeasureSpec, defaultSize);

        setMeasuredDimension(mWidth, mHeight);

    }

    private int getSize(int measureSpec,int defaultSize){
        int mode = MeasureSpec.getMode(measureSpec);
        int size = defaultSize;
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                size = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                size = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                size = MeasureSpec.getSize(mode);
                break;
                default:
        }
        return size;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            //秒针旋转
            secondAngel += 6;
            if (secondAngel == 360){
                secondAngel = 0;

                //一分钟分钟旋转
                minuteAngel += 6;
                if (minuteAngel == 360){
                    minuteAngel = 0;
                }

                //分针每转2分钟，时针转1°
                if (minuteAngel % 2 == 0){
                    hourAngel += 1;
                    if (hourAngel == 360){
                        hourAngel = 0;
                    }
                }
            }
            postInvalidate();


        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //移动坐标
        canvas.translate(mWidth/2,mWidth/2);
        canvas.scale(-1,-1);

        //在圆心处点一个黑点
        canvas.drawCircle(0,0,circleCenter,pointPaint);

        //裁剪圆形画布
        criclePath.reset();
        criclePath.addCircle(0,0,mWidth/2, Path.Direction.CW);
        canvas.clipPath(criclePath);

        canvas.drawCircle(0,0,mWidth/2-strokeWidth,circlePaint);

        //画时分秒指针
        canvas.save();
            canvas.rotate(secondAngel);
            canvas.drawLine(0,0,0,(4*mWidth/10),secondPaint);
        canvas.restore();

        canvas.save();
            canvas.rotate(minuteAngel);
            canvas.drawLine(0,0,0,(3*mWidth/10),minutePaint);
        canvas.restore();

        canvas.save();
            canvas.rotate(hourAngel);
            canvas.drawLine(0,0,0,(2*mWidth/10),minutePaint);
        canvas.restore();
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
