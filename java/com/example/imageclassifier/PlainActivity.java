package com.example.imageclassifier;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class PlainActivity extends AppCompatActivity {
    private Utils utils;
    private GridView gridView;
    private GridViewAdapter adapter;
    public static final String mBroadcastArrayListAction = "com.example.hiralb.imageclassifier.arraylist";
    private IntentFilter mIntentFilter;
    private int columnWidth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plain);
        Intent tent = getIntent();
        Log.d("my","In PlainActivity");
        ArrayList<String> plainArray = AppConstant.plainList;
        Log.d("plainArray:" , plainArray.toString());
        gridView = (GridView) findViewById(R.id.plainGrid);
        utils = new Utils(this);

        // Initilizing Grid View
        InitilizeGridLayout();

        adapter = new GridViewAdapter(PlainActivity.this,  columnWidth, 2);

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
