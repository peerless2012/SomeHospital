package com.peerless2012.somehospital.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.SectionIndexer;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/5 23:24
 * @Version V1.0
 * @Description
 */
public class QuickIndexRecycleView extends RecyclerView{
    private char[] latters ;
    private RectF latterBgRect = new RectF();
    private Paint latterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint latterBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint latterPopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int latterPopMagin;
    private int latterColorNormal;
    private int latterColorFocus;
    private float itemHeight;
    private boolean isFocus = false;
    public QuickIndexRecycleView(Context context) {
        super(context);
        initData();
    }

    public QuickIndexRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public QuickIndexRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initData();
    }
    /**
     * 初始化字母索引数据
     */
    private void initData() {
        latters = new char[26];
        char start = 'A';
        for (int j = 0; j < 26; j++) {
            latters[j] = (char) (start + j);
        }
        latterPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        latterBgPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        latterPopPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24, getResources().getDisplayMetrics()));
        latterPopMagin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        latterColorNormal = Color.BLUE;
        latterColorFocus = Color.WHITE;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int indexBgWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        latterBgRect.set(getWidth() - padding - indexBgWidth, getPaddingTop() + padding, getWidth() - padding, getHeight() - padding -getPaddingBottom());
        itemHeight = latterBgRect.height() / latters.length;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }


    private Rect textBound = new Rect();
    private Rect textPopRect = new Rect();
    private int selectedLatterIndex = -1;
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isFocus) {
            latterBgPaint.setColor(latterColorNormal);
            latterPaint.setColor(latterColorFocus);
            canvas.drawRoundRect(latterBgRect,5,5, latterBgPaint);
        }else {
            latterBgPaint.setColor(Color.TRANSPARENT);
            latterPaint.setColor(latterColorNormal);
        }
        for (int i = 0; i < latters.length; i++) {
            latterPaint.getTextBounds(latters, i, 1, textBound);
            float y = (latterBgRect.top + itemHeight * (i + 0.5f));
            canvas.drawText(latters, i, 1
                    ,latterBgRect.centerX() - (textBound.right + textBound.left)/2
                    , y - (textBound.bottom + textBound.top)/2
                    , latterPaint);
        }
        if (isFocus && selectedLatterIndex >= 0 && selectedLatterIndex < latters.length) {
            latterPopPaint.getTextBounds(latters, selectedLatterIndex, 1, textBound);
            float x = latterBgRect.left - latterPopMagin;
//			float textX = latterBgRect.centerX() - (textBound.right + textBound.left)/2 - latterBgRect.width();
            float textX = x;
            float textY = latterBgRect.top +  itemHeight * (selectedLatterIndex + 0.5f) - (textBound.bottom + textBound.top)/2;
            float y = latterBgRect.top +  itemHeight * (selectedLatterIndex + 0.5f);

            latterPopPaint.setColor(latterColorNormal);
            canvas.drawCircle(x, y, Math.max(textBound.width(), textBound.height()), latterPopPaint);
            latterPopPaint.setColor(latterColorFocus);
            latterPopPaint.setTextAlign(Paint.Align.CENTER);
//			textPopRect.set(latterBgRect.left - latterPopMagin, top, right, bottom);
//			latterPaint.getTextBounds(latters, selectedLatterIndex, 1, textBound);
            canvas.drawText(latters, selectedLatterIndex, 1 ,textX , textY, latterPopPaint);
        }
    }

    /**
     * 触摸区域是否在字母索引
     * @param x
     * @param y
     * @return
     */
    private boolean isLatterViewTouched(float x, float y){
        return latterBgRect.contains(x, y);
    }

/*---------------------------------------------------------------------------------------------------------------------------*/

   /* @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (isLatterViewTouched(x, y)) {
                isFocus = true;
                return true;
            }
        }
        if (isFocus) {
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                isFocus = false;
                //TODO 优化 重绘区域
                selectedLatterIndex = -1;
                invalidate();
            }else if (action == MotionEvent.ACTION_MOVE) {
                int currentIndex = (int) ((y - latterBgRect.top) / itemHeight);
                if (currentIndex != selectedLatterIndex || selectedLatterIndex < 0) {
                    selectedLatterIndex = currentIndex;
                    if (selectedLatterIndex < 0) selectedLatterIndex = 0;
                    if (selectedLatterIndex >= latters.length) selectedLatterIndex = latters.length -1;
                    Adapter listAdapter = getAdapter();
                    if (listAdapter instanceof SectionIndexer) {
                        int positionByLatter = ((SectionIndexer) listAdapter).getPositionByLatter(latters[selectedLatterIndex]);
                        if (positionByLatter >=0){
                            setSelection(positionByLatter);
                        }
                    }
                    invalidate();
                }
            }

            return true;
        }

        // call super if this was not our pinned view
        return super.dispatchTouchEvent(ev);
    }*/
}
