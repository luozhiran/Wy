package com.lzr.lview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginView extends ViewGroup {

    private ViewFactory mViewFactory;

    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewFactory = ViewFactory.create();
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mViewFactory.createView(context), layoutParams);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxHeight = 0;
        int maxWidth = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                maxWidth += child.getMeasuredWidth();
                maxHeight += child.getMeasuredHeight();
            }
        }

        setMeasuredDimension(resolveSize(maxWidth,widthMeasureSpec),resolveSize(maxHeight,heightMeasureSpec));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            child.layout(0, 0,  r, b);
        }
    }


    static class ViewFactory {
        private int wrap = LayoutParams.WRAP_CONTENT;
        private int match = LayoutParams.MATCH_PARENT;


        static ViewFactory create() {
            return new ViewFactory();
        }

        @SuppressLint("ResourceType")
        public View createView(Context context) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wrap, wrap);
            imageView.setImageResource(R.mipmap.a);
            linearLayout.addView(imageView, layoutParams);


            TextView textView = new TextView(context);
            textView.setId(-100000);
            textView.setText("ffffffffffffffffffffffffffffff");
            LinearLayout.LayoutParams   textLayoutParams = new LinearLayout.LayoutParams(match, wrap);
            linearLayout.addView(textView, textLayoutParams);

            return linearLayout;
        }
    }
}
