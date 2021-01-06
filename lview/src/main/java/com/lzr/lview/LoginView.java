package com.lzr.lview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LoginView extends ViewGroup {

    private ViewFactory mViewFactory;
    private EditText emial, pwd, phone;
    private Button vcode;
    private CountDownTimer countDownTimer;

    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewFactory = ViewFactory.create();

        View view = mViewFactory.createView(context, R.mipmap.b, "请输入邮箱");
        addView(view, relayout(view));

        view = mViewFactory.createView(context, R.mipmap.c, "请输入密码");
        addView(view, relayout(view));


        view = mViewFactory.createView(context, R.mipmap.d, "请输入手机号");
        addView(view, relayout(view));

        view = mViewFactory.createView(context, R.mipmap.e, "请输入验证码");
        addView(view, relayout(view));

        emial = findViewWithTag("请输入邮箱");
        pwd = findViewWithTag("请输入密码");
        phone = findViewWithTag("请输入手机号");
        vcode = findViewWithTag("获取验证码");
        vcode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer == null) {
                    countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            vcode.setText(millisUntilFinished / 1000 + " s");
                        }

                        @Override
                        public void onFinish() {
                            vcode.setText("获取验证码");
                        }
                    };
                }
                countDownTimer.start();
            }
        });
    }


    private MarginLayoutParams relayout(View view) {
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 20;
        layoutParams.rightMargin = 20;
        layoutParams.topMargin = 20;
        view.setPadding(20, 0, 0, 5);
        return layoutParams;
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
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                maxWidth += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.leftMargin;
                maxHeight += child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int start = totalHeight + layoutParams.topMargin;
            int end = start + child.getMeasuredHeight();
            child.layout(layoutParams.leftMargin, start, r - layoutParams.rightMargin, end);
            totalHeight = end;
        }
    }


    public String getPhone() {
        return phone.getText().toString();
    }

    public String getEmail() {
        return emial.getText().toString();
    }

    public String getVcode() {
        return vcode.getText().toString();
    }

    public String getPwd() {
        return pwd.getText().toString();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void switchPan() {
        removeAllViews();
    }

    static class ViewFactory {
        private int wrap = LayoutParams.WRAP_CONTENT;
        private int match = LayoutParams.MATCH_PARENT;


        static ViewFactory create() {
            return new ViewFactory();
        }

        @SuppressLint("ResourceType")
        public View createView(Context context, int imgId, String msg) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            setBackgroundStateList(linearLayout, 0, Color.parseColor("#a3bde2"), Color.parseColor("#ffffff"));


            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(44, 44);
            imageView.setImageResource(imgId);
            linearLayout.addView(imageView, layoutParams);


            EditText textView = new EditText(context);
            textView.setId(-100000);
            textView.setSingleLine(true);
            textView.setHint(msg);
            textView.setBackground(null);
            textView.setTag(msg);
            textView.setTextSize(18);
            textView.setPadding(20, 20, 20, 20);

            if ("请输入验证码".equals(msg)) {
                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(300, wrap);
                linearLayout.addView(textView, textLayoutParams);
            } else {
                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(match, wrap);
                linearLayout.addView(textView, textLayoutParams);
            }


            if ("请输入验证码".equals(msg)) {
                Button button = new Button(context);
                button.setText("获取验证码");
                button.setTextColor(Color.parseColor("#ffffff"));
                layoutParams = new LinearLayout.LayoutParams(wrap, match);
                layoutParams.weight = 1;
                setBackgroundStateList(button, 0, 0, Color.parseColor("#7a9ad7"));
                linearLayout.addView(button, layoutParams);
                button.setTag("获取验证码");
            }


            return linearLayout;
        }


        private void setBackgroundStateList(View view, int cornerRadius, int strokeColor, int solidColor) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            gradientDrawable.setCornerRadius(cornerRadius);
            gradientDrawable.setStroke(1, strokeColor);
            gradientDrawable.setColor(solidColor);
            view.setBackground(gradientDrawable);
        }
    }
}
