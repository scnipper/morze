package me.creese.morze.morze;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DrawFlashView extends View {

    private boolean white;


    public DrawFlashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void drawFlash() {
        white = true;
        invalidate();
    }
    public void drawBlack() {
        white = false;
        invalidate();
    }




    @Override
    protected void onDraw(Canvas canvas) {
        if(white)
        canvas.drawColor(Color.WHITE);
        else canvas.drawColor(Color.BLACK);


    }

}
