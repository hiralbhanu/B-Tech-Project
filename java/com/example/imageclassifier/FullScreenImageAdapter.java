package com.example.imageclassifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter {
    private Activity _activity;
    private ArrayList<String> _imagePaths;
    private LayoutInflater inflater;
    private int flag;

    // constructor
    public FullScreenImageAdapter(Activity activity, int flag) {
        this._activity = activity;
        this.flag = flag;
        if (flag==1)
            this._imagePaths = AppConstant.dupList;
        if (flag==2)
            this._imagePaths = AppConstant.plainList;
        if (flag==3)
            this._imagePaths = AppConstant.personList;
        if (flag==4)
            this._imagePaths = AppConstant.scanList;
        if (flag==5)
            this._imagePaths = AppConstant.knownList;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imgDisplay;
        Button delete;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
        delete = (Button) viewLayout.findViewById(R.id.btnDelete);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        imgDisplay.setImageBitmap(bitmap);

        // close button click event
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(_imagePaths.get(position));
                boolean deleted = file.delete();
/*
<intent-filter>
                <action android:name="android.intent.action.MEDIA_MOUNTED" />
                <data android:scheme="file" />
            </intent-filter>*/
                //_activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                if (AppConstant.dupList.contains(_imagePaths.get(position)))
                    AppConstant.dupList.remove(_imagePaths.get(position));
                if (AppConstant.plainList.contains(_imagePaths.get(position)))
                    AppConstant.plainList.remove(_imagePaths.get(position));
                if (AppConstant.personList.contains(_imagePaths.get(position)))
                    AppConstant.personList.remove(_imagePaths.get(position));
                if (AppConstant.scanList.contains(_imagePaths.get(position)))
                    AppConstant.scanList.remove(_imagePaths.get(position));
                if (AppConstant.knownList.contains(_imagePaths.get(position)))
                    AppConstant.knownList.remove(_imagePaths.get(position));


                Log.d("my","dupList"+AppConstant.dupList.toString());
                _activity.finish();

            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
