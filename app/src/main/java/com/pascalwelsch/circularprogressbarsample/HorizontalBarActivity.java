package com.pascalwelsch.circularprogressbarsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pascalwelsch.circularprogressbarsample.widget.HorizontalProgressBar;

/**
 *
 */
public class HorizontalBarActivity extends Activity {


    private HorizontalProgressBar mProgress;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_horizontal);

        mProgress = (HorizontalProgressBar) findViewById(R.id.HProgressBar);


        findViewById(R.id.tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setCurrentProgress(0);
                mProgress.setProgressWithAnima(100);
            }
        });
    }

}
