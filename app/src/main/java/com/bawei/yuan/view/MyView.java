package com.bawei.yuan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bawei.yuan.R;

/**
 * 作    者：云凯文
 * 时    间：2017/2/10
 * 描    述：
 * 修改时间：
 */

public class MyView extends View {

    private Paint mPaint;

    private int bigRadius;
    private int smallRadius;
    private String text;
    private int textSize;
    private int color2;

    private int width;
    private int height;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        init(context, attrs);
    }

    //初始化
    private void init(Context context, AttributeSet attrs) {
        //初始化画笔
        mPaint = new Paint();
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        //外圆半径
        bigRadius = (int) typedArray.getDimension(R.styleable.MyView_bigRadius, 50);
        //内圆半径
        smallRadius = (int) typedArray.getDimension(R.styleable.MyView_smallRadius, 30);
        //字体
        text = typedArray.getString(R.styleable.MyView_text);
        //字体大小
        textSize = (int) typedArray.getDimension(R.styleable.MyView_textSize, 20);
        //圆环颜色
        color2 = typedArray.getColor(R.styleable.MyView_color1, Color.YELLOW);

    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth() / 2;
        height = getHeight() / 2;
        setMeasuredDimension(bigRadius * 2, bigRadius * 2);

    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置画笔的颜色
        mPaint.setColor(color2);
        //绘制外圆
        canvas.drawCircle(width, height, bigRadius, mPaint);

        //设置画笔的颜色
        mPaint.setColor(Color.WHITE);
        //绘制内圆
        canvas.drawCircle(width, height, smallRadius, mPaint);

        //设置字体大小，颜色
        mPaint.setTextSize(textSize);
        mPaint.setColor(Color.BLACK);

        //绘制文本内容，先获取字体的宽，高
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);

        //获取文本的宽高
        int textWidth = rect.width();
        int textHeight = rect.height();

        //进行绘制
        canvas.drawText(text, width - (textWidth / 2), height + (textHeight / 2), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取按下时的位置
                float x = event.getX();
                float y = event.getY();
                //对按下时的位置进行判断
                judegDown(x, y);

                break;
        }
        //进行拦截
        return true;
    }

    //判断按下时的位置
    private void judegDown(float x, float y) {
        //根据勾股定理获取点击位置到圆中心点的距离来判断点击的位置
        float absX = Math.abs(width - x);
        float absY = Math.abs(height - y);
        double sqrt = Math.sqrt(absX * absX + absY * absY);

        //吐司
        if (sqrt < smallRadius) {
            Toast.makeText(getContext(), "在小圆内", Toast.LENGTH_SHORT).show();
        } else if (sqrt > smallRadius && sqrt < bigRadius) {
            Toast.makeText(getContext(), "在圆环内", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "在圆环外", Toast.LENGTH_SHORT).show();
        }
    }
}
