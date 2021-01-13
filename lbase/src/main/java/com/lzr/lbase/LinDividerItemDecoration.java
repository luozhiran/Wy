package com.lzr.lbase;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class LinDividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int dividerHeight = 2;
    private final Rect mBounds = new Rect();

    public LinDividerItemDecoration(int color) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(0.5f);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() == null) {
            return;
        }
        c.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            if (!isLastRow(parent, child)) {
                Objects.requireNonNull(parent.getLayoutManager()).getDecoratedBoundsWithMargins(child, mBounds);
                final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                final int top = bottom - dividerHeight;
                final int left = child.getPaddingLeft() + mBounds.left;
                final int right = mBounds.right - child.getPaddingRight();
                Rect rect = new Rect(left, top, right, bottom);
                c.drawRect(rect, paint);
            }
        }
        c.restore();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (!isLastRow(parent, view)) {
            outRect.set(0, 0, 0, dividerHeight);
        }
    }


    private boolean isLastRow(RecyclerView parent, View view) {
        int position = parent.getChildAdapterPosition(view);
        int childCount = parent.getAdapter().getItemCount();
        if (childCount == position + 1||childCount-1==position+1) {
            return true;
        }
        return false;
    }


}
