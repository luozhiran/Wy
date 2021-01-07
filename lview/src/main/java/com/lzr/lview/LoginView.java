package com.lzr.lview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginView extends ViewGroup {


    public static final int LOGIN = 1;
    public static final int REGISTER = 2;
    private int type = 0;
    private ViewFactory mViewFactory;
    private EditText emial, pwd, phone, code;
    private EditText loginName, loginPwd;
    private Button vcode;
    private CountDownTimer countDownTimer;
    private List<View> registerViews = new ArrayList<>();
    private List<View> loginViews = new ArrayList<>();
    private int[] registerIc = {R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private String[] registerSr = {"请输入邮箱", "请输入密码", "请输入手机号", "请输入验证码"};
    private int[] loginIc = {R.mipmap.f, R.mipmap.c};
    private String[] loginSr = {"请输入用户名", "请输入密码"};


    public LoginView(Context context) {
        this(context, null);
    }

    public LoginView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewFactory = ViewFactory.create();

        for (int i = 0; i < registerIc.length; i++) {
            View view = mViewFactory.createView(context, registerIc[i], registerSr[i], false);
            registerViews.add(view);
        }
        for (int i = 0; i < loginIc.length; i++) {
            View view = mViewFactory.createView(context, loginIc[i], loginSr[i], true);
            loginViews.add(view);
            addView(view, relayout(view));
        }
        switchPan(REGISTER);
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
        if (type == LOGIN) {
            return loginName.getText().toString();
        } else {
            return phone.getText().toString();
        }

    }

    public String getEmail() {
        return emial.getText().toString();
    }

    public String getVcode() {
        return code.getText().toString();
    }

    public String getPwd() {
        if (type == LOGIN) {
            return loginPwd.getText().toString();
        } else {
            return pwd.getText().toString();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void switchPan(int type) {
        if (this.type == type) return;
        removeAllViews();
        this.type = type;
        if (type == LOGIN) {
            for (View v : loginViews) {
                addView(v, relayout(v));
            }
            findViewForLogin();
        } else if (type == REGISTER) {
            for (View v : registerViews) {
                addView(v, relayout(v));
            }
            findViewForRegister();
        }
    }


    private void findViewForRegister() {
        if (emial != null) return;
        emial = findViewWithTag(registerSr[0]);
        pwd = findViewWithTag(registerSr[1]);
        phone = findViewWithTag(registerSr[2]);
        code = findViewWithTag(registerSr[3]);
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

    private void findViewForLogin() {
        if (loginName != null) return;
        loginName = findViewWithTag(loginSr[0]);
        loginPwd = findViewWithTag(loginSr[1]);
    }

    static class ViewFactory {
        private int wrap = LayoutParams.WRAP_CONTENT;
        private int match = LayoutParams.MATCH_PARENT;


        static ViewFactory create() {
            return new ViewFactory();
        }


        View createView(Context context, int imgId, String msg, boolean drawline) {
            LineLinearLayout linearLayout = new LineLinearLayout(context);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            if (drawline) {
                linearLayout.drawLine(true);
            } else {
                setBackgroundStateList(linearLayout, 0, Color.parseColor("#a3bde2"), Color.parseColor("#ffffff"));
            }
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(44, 44);
            imageView.setImageResource(imgId);
            linearLayout.addView(imageView, layoutParams);


            EditText textView = new EditText(context);
            textView.setSingleLine(true);
            textView.setHint(msg);
            textView.setBackground(null);
            textView.setTag(msg);
            textView.setTextSize(18);
            textView.setPadding(20, 20, 20, 20);

            if ("请输入验证码".equals(msg)) {
                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(300, wrap);
                textView.setFilters(new InputFilter[]{new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (dest.length() > 5) {
                            return "";
                        }
                        return null;
                    }
                }});
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
