package me.creese.morze.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by yoba2 on 06.12.2017.
 */


@SuppressLint("AppCompatCustomView")
public class AnimateButton extends ImageButton {
    public AnimateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public float getYFraction() {
        final WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return (height == 0) ? 0 : getY() / (float) height;
    }

    public void setYFraction(float yFraction) {
        final WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        setY((height > 0) ? (yFraction * height) : 0);
    }
    public float getXFraction()
    {
        final WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getWidth();
        return (height == 0) ? 0 : getX() / (float) height;
    }

    public void setXFraction(float xFraction) {
        final WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getWidth();
        setX((height > 0) ? (xFraction * height) : 0);
    }
}
