package me.creese.morze.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by yoba2 on 09.12.2017.
 */

public class SymbolWait extends View {

    private RectF rect;
    private Paint paint;
    private Character symbol;
    private Paint textPaint;
    private Paint paintLine;
    private float widthLine;
    private boolean drawAnimateProgress;

    public SymbolWait(Context context) {
        super(context);

    }

    public SymbolWait(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        textPaint = new Paint();
        rect = new RectF();
        paintLine = new Paint();

    }

    public SymbolWait(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SymbolWait(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSymbol(Character symbol, int lengthAnimation) {
        widthLine = getWidth();
        this.symbol = symbol;
        invalidate();

        startAnimation(lengthAnimation);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(300,300);
        setMeasuredDimension(200, 200);

    }
    public void startAnimation(int lengthAnimation) {
        drawAnimateProgress = true;
        ValueAnimator animator = ValueAnimator.ofFloat(widthLine, 15);
        animator.setDuration(lengthAnimation);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                widthLine = (float) animation.getAnimatedValue();
                System.out.println(widthLine);
                invalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = (float) getWidth();
        float height = (float) getHeight();

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);




        rect.set(0, 0, width,
                height);
        canvas.drawRoundRect(rect, 50, 50, paint);
        if (symbol != null) {
            textPaint.setColor(Color.DKGRAY);
            textPaint.setTextSize(100);
            textPaint.setTextAlign(Paint.Align.CENTER);

            float heightText = Math.abs(textPaint.getFontMetrics().top - textPaint.getFontMetrics().bottom);
            float x = getWidth() / 2;
            float y = (getHeight() / 2) + (heightText / 4);
            canvas.drawText(symbol+"",x,y,textPaint);
        }
        paintLine.setColor(Color.GREEN);
        paintLine.setStrokeWidth(10);
        /*if(!drawAnimateProgress) {
            System.out.println(widthLine +" widthLine after invalidate" );
            widthLine = width-15;
        }*/
        canvas.drawLine(15,height-30,widthLine,height-30,paintLine);

    }
}
