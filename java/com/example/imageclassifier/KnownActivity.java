package com.example.imageclassifier;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.GridView;

import java.util.ArrayList;

public class KnownActivity extends AppCompatActivity {
    private Utils utils;
    private GridView gridView;
    private GridViewAdapter adapter;
    public static final String mBroadcastArrayListAction = "com.example.hiralb.imageclassifier.arraylist";
    private IntentFilter mIntentFilter;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_known);
        //mIntentFilter.addAction(mBroadcastArrayListAction);
        Intent tent = getIntent();
        ArrayList<String> tentArray= AppConstant.knownList;
        Log.d("tentArray:" , tentArray.toString());
        gridView = (GridView) findViewById(R.id.knownGrid);


        utils = new Utils(this);

        // Initilizing Grid View
        InitilizeGridLayout();

        adapter = new GridViewAdapter(KnownActivity.this, columnWidth, 5);

        // setting grid view adapter
        gridView.setAdapter(adapter);
    }
    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }



}
