package com.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.library.R;


/**
 * @author cj
 * @date 2018/12/21 15:41.
 */

/**
 * 反向绑定注解
 */
@InverseBindingMethods({
        @InverseBindingMethod(
                type = NumberSeekbar.class,
                attribute = "progress",
                method = "getProgress",
                event = "progressAttrChanged")
})
public class NumberSeekbar extends View {

    private InverseBindingListener mInverseBindingListener;

    private static final String TAG = "cj";
    private Paint mBgPaint;
    private Paint mProgressPaint;
    private Paint mCirclePaint;
    private Paint mBigCirclePaint;
    private Paint mTextPaint;
    private int bigCircleRadius = 38;
    private int lineWidth = 20;
    private float mTextSize = 38F;
    private float mMaxProgress;
    /**
     * 暴露给用户的进度，0~100
     */
    private int progress;
    private float changeRate;
    //用户感知的进度和实际width之间的换算关系
    //100除以总长度，获取到换算率。
    //那么进度像素*速率，得到的就是进度值，进度值/速率得到的就是当前位置。

    public NumberSeekbar(Context context) {
        this(context, null);
    }

    public NumberSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberSeekbar);
        mMaxProgress = typedArray.getFloat(R.styleable.NumberSeekbar_max_progress, 100.0f);
        progress = typedArray.getInt(R.styleable.NumberSeekbar_progress, 0);
        typedArray.recycle();

    }

    private void initPaint() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.parseColor("#50ffffff"));
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeWidth(lineWidth);

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(Color.parseColor("#4F9FE9"));
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setStrokeWidth(lineWidth);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#1F92FD"));
        mCirclePaint.setStyle(Paint.Style.FILL);

        mBigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCirclePaint.setColor(Color.WHITE);
        mBigCirclePaint.setStyle(Paint.Style.FILL);


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = 800;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 100;
        }
        setMeasuredDimension(width, height);

        /*
         * 获取转换率
         */
        //获取实际参与进度的长度px。
        int realPx = getWidth() - bigCircleRadius * 2;
        changeRate = mMaxProgress / realPx;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String text = String.valueOf(progress);
        canvas.drawLine(bigCircleRadius, getHeight() / 2, getWidth() - bigCircleRadius, getHeight() / 2, mBgPaint);
        canvas.drawLine(bigCircleRadius, getHeight() / 2, bigCircleRadius + progress / changeRate, getHeight() / 2, mProgressPaint);
        canvas.drawCircle(bigCircleRadius + progress / changeRate, getHeight() / 2, bigCircleRadius, mBigCirclePaint);
        canvas.drawCircle(bigCircleRadius + progress / changeRate, getHeight() / 2, bigCircleRadius - 2, mCirclePaint);
        float length = mTextPaint.measureText(text);
        canvas.drawText(text, bigCircleRadius + progress / changeRate - length / 2, getHeight() / 2 + mTextSize / 2 - 6, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX();
        if (x < bigCircleRadius) {
            x = bigCircleRadius;
        } else if (x > getWidth() - bigCircleRadius) {
            x = getWidth() - bigCircleRadius;
        }
        float thePX = x - bigCircleRadius;
        int progressF = (int) (thePX * changeRate);
        //减少执行次数，防止多次执行同一个progress。
        if (progressF != progress) {
            progress = (int) progressF;
            postInvalidate();
            //引爆
            if (onProgressChangedListener != null) {
                onProgressChangedListener.progressChanged(progress);
            }
            if (mInverseBindingListener != null) {
                mInverseBindingListener.onChange();
            }
        }
        return true;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgressAttrChanged(InverseBindingListener inverseBindingListener) {
        mInverseBindingListener = inverseBindingListener;
    }

    @BindingAdapter(value = {"progress"})
    public static void progress(NumberSeekbar seekbar, int progress) {
        seekbar.setProgress(progress);
    }

    public interface OnProgressChangedListener {
        void progressChanged(int progress);
    }

    private OnProgressChangedListener onProgressChangedListener;

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.onProgressChangedListener = onProgressChangedListener;
    }
}
