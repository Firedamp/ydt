package ydt.sunlightcongress.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ydt.sunlightcongress.R;

/**
 * Created by Caodongyao on 2016/11/27.
 */

public class SideBar extends View{
    private String[] mIndex;
    private OnIndexChangeListener mListener;
    private int mSelected;

    private int mWidth;
    private int mHeight;
    private int mUnitHeight;
    private int mTextSize;

    private Paint mPaint;

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundColor(Color.parseColor("#EEEEEE"));
        mPaint = new Paint();
        mSelected = -1;
    }

    public void setOnIndexChangeListener(OnIndexChangeListener listener){
        this.mListener = listener;
    }

    public void setIndex(String[] index){
        this.mIndex = index;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mTextSize = mWidth/3*2;
        mUnitHeight = mIndex == null ? 0 : mHeight/mIndex.length > mWidth ? mWidth : mHeight/mIndex.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mIndex == null || mIndex.length == 0)
            return;

        mPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setStyle(Paint.Style.STROKE);

        for (int i = 0; i < mIndex.length; i++) {
            float x = mWidth / 2 - mPaint.measureText(mIndex[i]) / 2;
            float y = (float) (mHeight-mIndex.length*mUnitHeight)/2+mUnitHeight*i+mTextSize;
            canvas.drawText(mIndex[i], x, y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int select;
        if(event.getAction() == MotionEvent.ACTION_UP){
            select = -1;
        }
        else{
            select = event.getY() <= (mHeight-mIndex.length*mUnitHeight)/2 ? 0 :
                    event.getY() >= (mHeight + mIndex.length*mUnitHeight)/2 ? mIndex.length-1 :
                            (int) ((event.getY() - (mHeight-mUnitHeight*mIndex.length)/2)/mUnitHeight);
        }

        if(select != mSelected && mListener != null)
            mListener.onSidebarIndexChange(select == -1 ? null : mIndex[select]);

        mSelected = select;


        return true;
    }

    public interface OnIndexChangeListener{
        void onSidebarIndexChange(String index);
    }
}
