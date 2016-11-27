package ydt.sunlightcongress.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class IndicatorView extends FrameLayout {
    private View[] mViews;
    private int mSelectIndex;
    private OnIndexSelectListener mListener;
    private View mPointerView;
    private LinearLayout mContainerLayout;
    private Animator mAnimator;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    private void preInit() {
        setBackgroundResource(android.R.color.white);
        mPointerView = new View(getContext());
        mPointerView.setBackgroundResource(android.R.color.holo_blue_light);
        addView(mPointerView);
        mContainerLayout = new LinearLayout(getContext());
        mContainerLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mContainerLayout);
    }

    private int getScreenWidth() {
        return getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public void setOnIndexSelectListener(OnIndexSelectListener listener) {
        this.mListener = listener;
    }

    public void init(String[] titles) {
        if (titles == null || titles.length == 0) {
            setVisibility(GONE);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setZ(10);
        }
        setVisibility(VISIBLE);
        mContainerLayout.setOrientation(LinearLayout.HORIZONTAL);
        mSelectIndex = -1;

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Object obj = view.getTag();
                if (obj != null && obj instanceof Integer) {
                    setSelectedIndex((Integer) obj);
                }
            }
        };

        mViews = new View[titles.length];
        mContainerLayout.removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            p.weight = 1;
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(p);
            textView.setText(titles[i]);
            textView.setTag(i);
            textView.setOnClickListener(onClickListener);
            mViews[i] = textView;
            mContainerLayout.addView(textView);

            mPointerView.setLayoutParams(new LayoutParams(getScreenWidth() / titles.length, ViewGroup.LayoutParams.MATCH_PARENT));

        }

        setSelectedIndex(0);
    }

    private void setSelectedIndex(int index) {
        if (index < 0 || mViews == null || index >= mViews.length || index == mSelectIndex)
            return;

        if (mListener != null)
            mListener.onIndicatorIndexSelected(index);

        if (mAnimator != null)
            mAnimator.cancel();

        mAnimator = ObjectAnimator.ofFloat(mPointerView, "translationX", index * getScreenWidth() / mViews.length);
        mAnimator.start();

        mSelectIndex = index;
    }


    public interface OnIndexSelectListener {
        void onIndicatorIndexSelected(int position);
    }

}
