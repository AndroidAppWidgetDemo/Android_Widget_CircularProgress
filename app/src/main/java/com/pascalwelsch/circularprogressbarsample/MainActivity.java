package com.pascalwelsch.circularprogressbarsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pascalwelsch.circularprogressbarsample.widget.HorizontalProgressBar;

/**
 *
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //
        findViewById(R.id.button01).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CircularBarActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        //
        findViewById(R.id.button02).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HorizontalBarActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
