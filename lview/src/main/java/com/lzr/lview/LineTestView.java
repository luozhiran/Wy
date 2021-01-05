package com.lzr.lview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.itg.lib_log.L;

public class LineTestView extends androidx.appcompat.widget.AppCompatTextView {
    private Paint sPaint;
    private Paint upaint;
    private Paint cPaint;

    public LineTestView(Context context) {
        this(context, null);
    }

    public LineTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sPaint = new Paint();
        sPaint.setAntiAlias(true);
        sPaint.setColor(Color.parseColor("#789ad9"));
        sPaint.setStrokeWidth(2);

        upaint = new Paint();
        upaint.setAntiAlias(true);
        upaint.setStrokeWidth(1);
        upaint.setColor(Color.parseColor("#dedede"));

        cPaint = sPaint;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        L.e(getText() + "  " + getLeft() + "  " + getBottom() + "  " + getRight() + "  " + getBottom());
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), cPaint);
    }

    public void setCheck(boolean check) {
        if (check) {
            cPaint = sPaint;
        } else {
            cPaint = upaint;
        }
    }
}
