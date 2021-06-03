package com.example.imageclassifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class FullScreenViewActivity extends Activity {

        private ViewPager viewPager;
        private FullScreenImageAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fullscreen_view);

            viewPager = findViewById(R.id.pager);
            ArrayList<String> tentArray = new ArrayList<String>();
            Intent tent = getIntent();
           // ArrayList<String> tentArray = tent.getStringArrayListExtra("imagepaths");
            int flag = tent.getIntExtra("flag",0);
            if (flag == 1)
                tentArray = AppConstant.dupList;
            if (flag == 2)
                tentArray = AppConstant.plainList;
            if (flag == 3)
                tentArray = AppConstant.personList;
            if (flag == 4)
                tentArray = AppConstant.scanList;
            if (flag == 5)
                tentArray = AppConstant.knownList;
            adapter = new FullScreenImageAdapter(this, flag );
            viewPager.setAdapter(adapter);
        }

        @Override
        protected void onStart() {
            super.onStart();
            int pos = getIntent().getIntExtra("position",0);
            viewPager.setCurrentItem(pos);
        }
    }

