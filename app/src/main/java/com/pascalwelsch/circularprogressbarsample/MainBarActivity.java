package com.pascalwelsch.circularprogressbarsample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 *
 */
public class MainBarActivity extends Activity {
    //
    private CircularProgressBar mCircularProgressBar;
    //
    private Button mOne;
    private Button mZero;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.main_activity);
        // 进度条
        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.CircularProgressBar);
        // 从0到1的动画
        mOne = (Button) findViewById(R.id.one);
        mOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCircularProgressBar.startAnima(1);
            }
        });
        // 从 1到0的动画
        mZero = (Button) findViewById(R.id.zero);
        mZero.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCircularProgressBar.startAnima(0);
            }
        });


    }

}
