package com.example.imageclassifier;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    public static final String mBroadcastArrayListAction = "com.example.hiralb.imageclassifier.arraylist";
    private IntentFilter mIntentFilter;
    ArrayList<String> plainArray = AppConstant.plainList;
    ArrayList<String> personArray = AppConstant.personList;
    ArrayList<String> scanArray = AppConstant.scanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text);
        //mIntentFilter.addAction(mBroadcastArrayListAction);
        Intent tent = getIntent();
        //plainArray = tent.getStringArrayListExtra("plainList");
        //personArray = tent.getStringArrayListExtra("personList");
        //scanArray = tent.getStringArrayListExtra("scanList");
       // Log.d("plainArray:", plainArray.toString());
       // Log.d("personArray:", personArray.toString());
        // Log.d("scanArray:", scanArray.toString());

    }
   public void plainScrn(View v) {
Log.d("my","In plainscrn");
        Intent intent = new Intent(getApplicationContext(), PlainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("plainList",plainArray);
        startActivity(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }
    public void personScrn(View v) {

        Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("personList",personArray);
        startActivity(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }
    public void scanScrn(View v) {

        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("scanList",scanArray);
        startActivity(intent);

        // Intent intent = new Intent(getApplicationContext(), DuplicateActivity.class);
        //startActivity(intent);
    }
}
