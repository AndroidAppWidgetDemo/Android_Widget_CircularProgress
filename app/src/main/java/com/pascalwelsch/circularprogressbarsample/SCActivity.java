package com.pascalwelsch.circularprogressbarsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pascalwelsch.circularprogressbarsample.widget.SCProgressBar;

/**
 *
 */
public class SCActivity extends Activity {
    //
    private SCProgressBar mCircularProgressBar;
    //
    private Button mOne;
    private Button mZero;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_sc);
        // 进度条
        mCircularProgressBar = (SCProgressBar) findViewById(R.id.scProgressBar);
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
