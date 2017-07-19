package com.liguo.hjdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author : Extends 16:03
 * @description :
 * @email : 2563892038@qq.com
 * @date : 2017/7/13
 * @change :
 * @changer :
 */

public class Tes extends View {
    int w = 0;
    float r = 0;
    float c = 0;
    private float jindu = 0;//0..1
    private int ceng = 0;//0,1,2,3
    Paint bluePaint,greenPaint,yelowPaint,redPaint;
    PointF bluePoint,greenPoint,yellowPoint,redPoint;
    public Tes(Context context) {
        this(context,null);
    }

    public Tes(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Tes(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        bluePaint = new Paint();
        greenPaint = new Paint();
        yelowPaint = new Paint();
        redPaint = new Paint();

        bluePaint.setColor(Color.BLUE);
        greenPaint.setColor(Color.GREEN);
        yelowPaint.setColor(Color.YELLOW);
        redPaint.setColor(Color.RED);


        bluePoint = new PointF();
        greenPoint = new PointF();
        yellowPoint = new PointF();
        redPoint = new PointF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        w = getWidth();
        r = w/8f;

        canvas.drawCircle(bluePoint.x,bluePoint.y,r,bluePaint);
        canvas.drawCircle(yellowPoint.x,yellowPoint.y,r,yelowPaint);
        canvas.drawCircle(redPoint.x,redPoint.y,r,redPaint);
        canvas.drawCircle(greenPoint.x,greenPoint.y,r,greenPaint);

//        Log.e("000000000","================="+ceng);
//
//        switch (ceng){
//            case 0:
//                canvas.drawCircle(c-p+jp,c+p-jp,r,greenPaint);
//                canvas.drawCircle(c+p-jp,c-p+jp,r,yelowPaint);
//                canvas.drawCircle(c+p-jp,c+p-jp,r,redPaint);
//                canvas.drawCircle(c-p+jp,c-p+jp,r,bluePaint);
//                break;
//            case 1:
//                canvas.drawCircle(c-p+jp,c-p+jp,r,bluePaint);
//                canvas.drawCircle(c+p-jp,c-p+jp,r,yelowPaint);
//                canvas.drawCircle(c+p-jp,c+p-jp,r,redPaint);
//                canvas.drawCircle(c-p+jp,c+p-jp,r,greenPaint);
//                break;
//            case 2:
//
//                canvas.drawCircle(c-p+jp,c+p-jp,r,greenPaint);
//                canvas.drawCircle(c-p+jp,c-p+jp,r,bluePaint);
//                canvas.drawCircle(c+p-jp,c-p+jp,r,yelowPaint);
//                canvas.drawCircle(c+p-jp,c+p-jp,r,redPaint);
//                break;
//            case 3:
//
//                canvas.drawCircle(c+p-jp,c+p-jp,r,redPaint);
//                canvas.drawCircle(c-p+jp,c+p-jp,r,greenPaint);
//                canvas.drawCircle(c-p+jp,c-p+jp,r,bluePaint);
//                canvas.drawCircle(c+p-jp,c-p+jp,r,yelowPaint);
//                break;
//        }

    }

    public void setJindu(float f){
        this.jindu = f;
        invalidate();
    }
    public void setCeng(int i){
        this.ceng = i;
    }

}
