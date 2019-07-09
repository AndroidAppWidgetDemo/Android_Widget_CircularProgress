package com.pascalwelsch.circularprogressbarsample.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.pascalwelsch.circularprogressbarsample.R;


public class SCProgressBar extends View {

    // 动画时长
    private final int ANIMA_TIME = 1000;

    /**
     *
     */
    // 画笔
    private Paint mPaint;
    // 外接矩形
    private final RectF mCircleBounds = new RectF();
    // 背景颜色
    private int mProgressBgColor;
    // 进度开始颜色
    private int mProgressStartColor;
    // 进度结束颜色
    private int mProgressEndColor;

    // 进度
    private float mProgress = 0.3f;
    /**
     * 中心点移动的距离
     */
    // 坐标轴移动距离
    private float mTranslationOffsetX;
    // 坐标轴移动距离
    private float mTranslationOffsetY;
    // padding
    private float mProgressPadding;
    // width
    private float mProgressWidth;


    /**
     * 动画相关
     */
    private ObjectAnimator mProgressBarAnimator;

    /**
     * #############################构造方法###################################
     */
    public SCProgressBar(final Context context) {
        this(context, null);
    }

    public SCProgressBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SCProgressBar(final Context context, final AttributeSet attrs,
                         final int defStyle) {
        super(context, attrs, defStyle);
        // #########加载att################
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SCProgressBar);
        //
        if (attributes != null) {
            try {
                // 背景 颜色
                setProgressBgColor(attributes.getColor(R.styleable.SCProgressBar_sc_progress_bg_color, Color.CYAN));
                // 进度 开始颜色
                setProgressStartColor(attributes.getColor(R.styleable.SCProgressBar_sc_progress_color_start, Color.RED));
                // 进度 结束颜色
                setProgressEndColor(attributes.getColor(R.styleable.SCProgressBar_sc_progress_color_end, Color.RED));
                // 进度
                setProgress(attributes.getFloat(R.styleable.SCProgressBar_sc_progress, 0.0f));
                // padding
                setProgressPadding(attributes.getDimension(R.styleable.SCProgressBar_sc_progress_padding, 0));
                // width
                setProgressWidth(attributes.getDimension(R.styleable.SCProgressBar_sc_progress_width, 0));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                attributes.recycle();
            }
        }
        // 初始化画笔
        initProgressPaint();
    }

    /**
     * 初始化画笔
     */
    private void initProgressPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 描边
        mPaint.setStyle(Paint.Style.STROKE);
        // 进度条宽度
        mPaint.setStrokeWidth(getProgressWidth());
        // 画笔首尾为圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 宽的一半
        final float halfWidth = getMeasuredWidth() * 0.5f;
        // 半径
        float radius = halfWidth - mProgressWidth / 2 - mProgressPadding;
        // 外接矩形
        mCircleBounds.set(-radius, -radius, radius, radius);
        // 坐标轴移动距离(圆的中心点)
        mTranslationOffsetX = halfWidth;
        mTranslationOffsetY = halfWidth;

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        // 保存画布
        int saveCount = canvas.save();
        // 坐标轴移动到View的中心点
        canvas.translate(mTranslationOffsetX, mTranslationOffsetY);

        /**
         * 绘制背景
         */
        mPaint.setShader(null);
        mPaint.setColor(mProgressBgColor);
        // draw the progress or a full circle if overdraw is true
        canvas.drawArc(mCircleBounds,
                // 起始位置
                180,
                // 扫过的面积
                180,
                // 是否绘制扇形
                false, mPaint);

        /**
         * 绘制进度
         */
        // 获取当前进度 1*180
        final float progressRotation = getCurrentRotation();
        //
        LinearGradient linearGradient = new LinearGradient(
                mCircleBounds.left, mCircleBounds.bottom,
                mCircleBounds.right, mCircleBounds.bottom,
                mProgressStartColor, mProgressEndColor, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        //
        canvas.drawArc(mCircleBounds,
                // 起始位置
                180,
                // 扫过的面积
                progressRotation,
                // 是否绘制扇形
                false, mPaint);

        // 恢复画布
        canvas.restoreToCount(saveCount);

    }

    /**
     * 设置进度条 背景 颜色值
     *
     * @param color
     */
    public void setProgressBgColor(final int color) {
        mProgressBgColor = color;
    }


    public void setProgressStartColor(int mProgressStartColor) {
        this.mProgressStartColor = mProgressStartColor;
    }


    public void setProgressEndColor(int mProgressEndColor) {
        this.mProgressEndColor = mProgressEndColor;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (Math.abs(progress - mProgress) <= 0.001f) {
            return;
        }
        if (progress <= 0) {
            mProgress = 0;
        } else if (progress >= 1) {
            mProgress = 1;
        } else {
            mProgress = progress;
        }
        //
        invalidate();
    }


    /**
     * 获取进度
     *
     * @return
     */
    public float getProgress() {
        return mProgress;
    }


    /**
     * Gets the current rotation.
     *
     * @return the current rotation
     */
    private float getCurrentRotation() {
        return 180 * mProgress;
    }


    // #############################动画相关####################################

    /**
     * 从“当前进度开始”到 “progress进度”的一个动画
     *
     * @param progress 0到1之间的一个数值
     */
    public void startAnima(final float progress) {
        // 取消动画
        if (mProgressBarAnimator != null) {
            mProgressBarAnimator.cancel();
        }
        mProgressBarAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        mProgressBarAnimator.setDuration(ANIMA_TIME);
        mProgressBarAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mProgressBarAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setProgress(progress);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mProgressBarAnimator.start();
    }


    public float getProgressPadding() {
        return mProgressPadding;
    }

    public void setProgressPadding(float progressPadding) {
        this.mProgressPadding = progressPadding;
    }

    public float getProgressWidth() {
        return mProgressWidth;
    }

    public void setProgressWidth(float mProgressWidth) {
        this.mProgressWidth = mProgressWidth;
    }
}
