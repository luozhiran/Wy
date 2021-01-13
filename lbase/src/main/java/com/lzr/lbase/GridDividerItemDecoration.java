package com.lzr.lbase;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itg.lib_log.L;

import java.util.Objects;

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight = 1;
    private int color;
    private final Rect mBounds = new Rect();
    private Paint paint;

    public GridDividerItemDecoration(int color, int stroke) {
        this.color = color;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStrokeWidth(0.5f);
        dividerHeight = stroke;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        canvas.save();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            Objects.requireNonNull(parent.getLayoutManager()).getDecoratedBoundsWithMargins(child, mBounds);

            boolean islastColum = isLastColum(i, parent);//最后一列
            boolean isLastRow = isLastRow(i, parent);//最后一行


            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - dividerHeight;
            final int left = child.getPaddingLeft() + mBounds.left;
            final int right = mBounds.right;
//            canvas.drawRect(left, top, right, bottom, paint);

            //画右边线
            final int right1 = mBounds.right + Math.round(child.getTranslationX());
            final int left1 = right1 - dividerHeight;
            final int top1 = mBounds.top;
            final int bottom1 = mBounds.bottom;
//            canvas.drawRect(left1, top1, right1, bottom1, paint);
            if (islastColum && isLastRow) {

            } else if (islastColum) {//最后一列
                canvas.drawRect(left, top, right, bottom, paint);
            } else if (isLastRow) {//最后一行
                canvas.drawRect(left1, top1, right1, bottom1, paint);
            } else {
                canvas.drawRect(left, top, right, bottom, paint);
                canvas.drawRect(left1, top1, right1, bottom1, paint);
            }



        }
        canvas.restore();
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.set(0, 0, dividerHeight, dividerHeight);

        int position = parent.getChildAdapterPosition(view);
        boolean islastColum = isLastColum(position, parent);//最后一列
        boolean isLastRow = isLastRow(position, parent);//最后一行
        if (islastColum && isLastRow) {
            L.e("最后一行&&最后一行  " + position);
            outRect.set(0, 0, 0, 0);
        } else if (islastColum) {//最后一列
            outRect.set(0, 0, 0, dividerHeight);
            L.e("最后一列  " + position);
        } else if (isLastRow) {//最后一行
            outRect.set(0, 0, dividerHeight, 0);
            L.e("最后一行  " + position);
        } else {
            outRect.set(0, 0, dividerHeight, dividerHeight);
            L.e("----  " + position);
        }

    }


    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int childCount = parent.getAdapter().getItemCount();
            double count = Math.ceil((double) childCount / (double) spanCount);//总行数
            double currentCount = Math.ceil((double) (itemPosition + 1) / spanCount);//当前行数
            //最后当前数量小于总的
            if (currentCount < count) {
                return false;
            }
        }

        return true;
    }


    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int spanCount = getSpanCount(parent);
            if ((itemPosition + 1) % spanCount == 0) {//因为是从0可以所以要将ItemPosition先加1
                return true;
            }
        }
        return false;
    }


    /**
     * 一行分多少列
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

}
