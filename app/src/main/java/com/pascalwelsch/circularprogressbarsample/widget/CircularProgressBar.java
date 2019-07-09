package com.pascalwelsch.circularprogressbarsample.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.pascalwelsch.circularprogressbarsample.R;


public class CircularProgressBar extends View {

    // 动画时长
    private final int ANIMA_TIME = 1000;

    /**
     *
     */
    // 外接矩形
    private final RectF mCircleBounds = new RectF();
    // 颜色
    private int mProgressColor;
    // 画笔
    private Paint mPaint;
    // 进度
    private float mProgress = 0.3f;
    /**
     * 中心点移动的距离
     */
    // 坐标轴移动距离
    private float mTranslationOffsetX;
    // 坐标轴移动距离
    private float mTranslationOffsetY;
    //
    private float mProgressPadding;

    /**
     * 蒙板图
     */
    // 外接矩形
    private final RectF mMaskBounds = new RectF();
    private Bitmap mMaskBitmap;

    /**
     * 动画相关
     */
    private ObjectAnimator mProgressBarAnimator;

    /**
     * #############################构造方法###################################
     */
    public CircularProgressBar(final Context context) {
        this(context, null);
    }

    public CircularProgressBar(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressBar(final Context context, final AttributeSet attrs,
                               final int defStyle) {
        super(context, attrs, defStyle);
        // #########加载att################
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar);
        //
        if (attributes != null) {
            try {
                // 颜色
                setProgressColor(attributes.getColor(R.styleable.CircularProgressBar_c_progress_color, Color.CYAN));
                // 进度
                setProgress(attributes.getFloat(R.styleable.CircularProgressBar_c_progress, 0.0f));
                //
                setProgressPadding(attributes.getDimension(R.styleable.CircularProgressBar_c_progress_padding, 0));
                //蒙板图片
                int maskId = attributes.getResourceId(R.styleable.CircularProgressBar_c_mask_id, 0);
                if (maskId != 0) {
                    setMaskBitmap(BitmapFactory.decodeResource(context.getResources(), maskId));
                }
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
        mPaint = new Paint();
        mPaint.setColor(mProgressColor);
        //
        invalidate();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 宽的一半
        final float halfWidth = getMeasuredWidth() * 0.5f;
        // 半径
        float radius = halfWidth - mProgressPadding;
        // 外接矩形
        mCircleBounds.set(-radius, -radius, radius, radius);
        // 坐标轴移动距离(圆的中心点)
        mTranslationOffsetX = halfWidth;
        mTranslationOffsetY = halfWidth;
        /**
         * 蒙板
         */
        mMaskBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        // 保存画布
        int saveCount = canvas.save();

        // 坐标轴移动到View的中心点
        canvas.translate(mTranslationOffsetX, mTranslationOffsetY);
        // 获取当前进度 1*360
        final float progressRotation = getCurrentRotation();
        // draw the progress or a full circle if overdraw is true
        canvas.drawArc(mCircleBounds,
                // 起始位置
                270,
                // 扫过的面积
                progressRotation,
                // 绘制扇形
                true, mPaint);
        // 恢复画布
        canvas.restoreToCount(saveCount);
        /**
         * 绘制蒙板
         */
        if (mMaskBitmap != null) {
            Rect rect = new Rect(0, 0, mMaskBitmap.getWidth(), mMaskBitmap.getHeight());
            // 绘制蒙板
            canvas.drawBitmap(mMaskBitmap, rect, mMaskBounds, mPaint);
        }

    }

    /**
     * 设置进度条颜色值
     *
     * @param color
     */
    public void setProgressColor(final int color) {
        mProgressColor = color;
        initProgressPaint();
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
        return 360 * mProgress;
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

    public Bitmap getMaskBitmap() {
        return mMaskBitmap;
    }

    public void setMaskBitmap(Bitmap maskBitmap) {
        this.mMaskBitmap = maskBitmap;
    }
}
