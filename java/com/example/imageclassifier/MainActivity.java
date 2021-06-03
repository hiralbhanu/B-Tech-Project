package com.example.imageclassifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
   // private MyBroadRequestReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("opencv_java3");
        setContentView(R.layout.activity_main);
/*
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        IntentFilter filter = new IntentFilter("ACTIONNAME");
        receiver = new MyBroadRequestReceiver();
        registerReceiver( receiver, filter);
*/


    }

    /*public class MyBroadRequestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //update your progressbar here
            double d = getIntent().getDoubleExtra("prog",0);
            d = d* 100;
            Log.v("my","d:"+d);
            //Integer i =Integer.parseInt(String.valueOf(d));
            int i = (int)d;
            progressStatus = i;
            progressBar.setProgress(progressStatus);
            *//*new Thread(new Runnable() {
                @Override
                public void run() {
                    while(progressStatus < 100){
                        android.os.SystemClock.sleep(100);
                        progressStatus++;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progressStatus);
                            }
                        });
                    }
                }
            }).start();*//*
        }


    }
*/
    public void dupScrn(View v) {

        Intent intent = new Intent(this, CalculateDuplicate.class);
        startService(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }
    public void textScrn(View v) {

        Intent intent = new Intent(this, CalculateText.class);
        startService(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }

    public void knownScrn(View v) {

        Intent intent = new Intent(this, CalculateKnown.class);
        startService(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }
}
