package com.example.imageclassifier;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculateDuplicate2 extends IntentService {
    private ArrayList<String> fileList = new ArrayList<>();

    public CalculateDuplicate2() {
        super("CalculateDuplicate2");
    }
    private static double MSE(Mat img1, Mat img2) {
        int rows = img1.rows(); //Calculates number of rows
        int cols = img1.cols(); //Calculates number of columns
        System.out.println(img1.channels()); //Calculates number of channels (Grayscale: 1, RGB: 3, etc.)
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double data1[] = img1.get(i, j); //Stores element in an array
                double data2[] = img2.get(i, j);
				/*for (int k = 0; k < ch; k++) //Runs for the available number of channels
		         {
		             data[k] = data[k] * 2; //Pixel modification done here
		         }*/
                sum += Math.pow((data2[0] - data1[0]), 2);
            }
        }
        sum = (sum * 1.0) / (rows * cols);
        System.out.println(sum);
        return sum;
    }

    public ArrayList<String> getFile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (File file : listFile) {
                if (file.isDirectory()) {
                    getFile(file);
                }
                else {
                    if (file.getName().endsWith(".png")
                            || file.getName().endsWith(".jpg")
                            || file.getName().endsWith(".jpeg"))
                    {
                        String temp = file.getPath();
                        if (!fileList.contains(temp))
                            fileList.add(temp);
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * The IntentService calls this method from the default worker thread with
     * the intent that started the service. When this method returns, IntentService
     * stops the service, as appropriate.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastIntent = new Intent();
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        if(AppConstant.flag1==false) {
            String pathAcc = Environment.getExternalStorageDirectory().toString()+"/DuplicateData/";
            double mse[][];
            String[] actual = {pathAcc+"iii.jpeg",pathAcc+"vin4.jpeg",pathAcc+"dup_blue1.jpg",pathAcc+"dup_blue2.jpg",pathAcc+"school-1.jpg",pathAcc+"school.jpg",pathAcc+"IMG-20170319-WA0089.jpg",pathAcc+"IMG-20170319-WA0090.jpg",pathAcc+"IMG-2017-03-17-14-15-04-885.jpg",pathAcc+"IMG-20170318-WA0013.jpg",pathAcc+"IMG-20160311-WA0039.jpg",pathAcc+"IMG-20170319-WA0053.jpg",pathAcc+"IMG-20170301-WA0013.jpg",pathAcc+"IMG-20170301-WA0005.jpg",pathAcc+"IMG-20170927-WA0037.jpg",pathAcc+"IMG-20170927-WA0036.jpg",pathAcc+"2018-07-07-06-36-33-735.jpg",pathAcc+"2018-07-07-06-41-46-444.jpg",pathAcc+"IMG_20180905_172248.jpg",pathAcc+"IMG_20180905_172249.jpg",pathAcc+"1545908558188.jpg", pathAcc+"1546090136574.jpg" , pathAcc+"2018-03-10-15-43-06-814.jpg", pathAcc+"2018-03-10-15-43-14-034.jpg"};
            Log.v("my", Environment.getExternalStorageDirectory().getPath().toString());
            File root = new File(Environment.getExternalStorageDirectory().toString()+"/DuplicateData/");
            //String pathname = Environment.getExternalStorageDirectory().toString()+"/DatasetDup/";
            //File root = new File(Environment.getExternalStorageDirectory().toString()+"/DatasetDup/");
            //File root = new File(Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images/");
            //String pathname = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/Images/";
            //Log.e("my","pathname"+pathname);
            //String pathname = "C:/Users/hiral/Desktop/DuplicateData/";
            ArrayList<Mat> images = new ArrayList<Mat>();
            int count = 0;
            //double mse = 0;
            //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            // File file = new File(pathname);
            ArrayList<String> files = getFile(root);
            //Log.e("my","filelist:"+files.toString());
            //Log.d("my", String.valueOf(files == null));

            mse = new double[files.size()][files.size()];
            HashMap<String, Integer> accuracyMap = new HashMap<>();
            int countj = 0;
            for (String f2 : files) {
                //  Log.d("my", f2);
                //String f2 = root + f;

                if(Arrays.asList(actual).contains(f2))
                {
                    accuracyMap.put(f2,1);
                Log.e("my","Add "+countj++);}
                else
                    accuracyMap.put(f2,0);


                Mat image = Imgcodecs.imread(f2);

                //Creating an empty matrix to store the result
                Mat img2 = new Mat();
                Mat img = new Mat();

                //Scaling the Image
                Imgproc.resize(image, img2, new Size(300, 400), 0, 0, Imgproc.INTER_AREA);
                Imgproc.cvtColor(img2, img, Imgproc.COLOR_RGB2GRAY);
                //Writing the image
                //Imgcodecs.imwrite("C:/Users/hiral/Desktop/dup/1.jpg", img);
                images.add(img);

            }
            //System.out.println(images);

            HashMap<String, Integer> displayList = new HashMap<String, Integer>();
            int flag = 0;
            for (int i = 0; i < images.size(); i++) {
                Log.e("my","i: "+i);
                /* double doub = ((i+1)*1.0)/images.size();
                broadcastIntent.putExtra("prog",doub);
                Log.e("my","prog:"+doub);
                broadcastIntent.setAction("ACTIONNAME");
                sendBroadcast(broadcastIntent);
*/                for (int j = i + 1; j < images.size(); j++) {
                    String filei = files.get(i);
                    String filej=files.get(j);
                    mse[i][j] = MSE(images.get(i), images.get(j));
                    //Log.d("my", "In main loop... mse for image " + i + " and " + j + ": " + mse[i][j]);
                    if (mse[i][j] <= 2000) {
                        count += 1;
                        flag++;
                        if (!displayList.containsKey(filei))
                            if (displayList.containsKey(filej))
                                displayList.put(filei, displayList.get(filej));
                            else
                                displayList.put(filei, flag);
                        if (!displayList.containsKey(filej))
                            if (displayList.containsKey(filei))
                                displayList.put(filej, displayList.get(filei));
                            else
                                displayList.put(filej, flag);
                    }
                }
            }
            Log.d("my", "total pairs: " + count);
            List<Map.Entry<String, Integer>> list =
                    new LinkedList<Map.Entry<String, Integer>>(displayList.entrySet());

            // Sort the list
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> o1,
                                   Map.Entry<String, Integer> o2) {
                    return (o1.getValue()).compareTo(o2.getValue());
                }
            });

            int tp=0,tn=0,fp=0,fn=0;
            for (Map.Entry<String, Integer> entry : list) {
                String key = entry.getKey();
                if(accuracyMap.get(key) ==  1)
                    tp+=1;
                else if(accuracyMap.get(key) == 0)
                    fp+=1;
                accuracyMap.remove(key);
                AppConstant.dupList.add(key);
                // create custom string for each map element
                //String testString = key + "=" + value;
                //resultString +=  testString ;
            }
            for(Map.Entry<String, Integer> entry : accuracyMap.entrySet()){
                if(entry.getValue() == 0)
                    tn+=1;
                else if(entry.getValue() == 1)
                    fn+=1;
            }
            Log.d("Acc","True Positives: "+tp);
            Log.d("Acc","False Positives: "+fp);
            Log.d("Acc","True Negatives: "+tn);
            Log.d("Acc","False Negatives: "+fn);
            Log.d("Acc","Total:"+(tp+tn+fp+fn));
            double precision = (tp*1.0)/(tp+fp);
            Log.d("Acc","precision:"+precision);
            double recall = (tp*1.0)/(tp+fn);
            Log.d("Acc","recall:"+recall);
            double f1_score = (2*precision*recall*1.0)/(precision+recall);
            Log.d("Acc","f1-score:"+f1_score);
            double accuracy = ((tp+tn)*1.0)/(tp+tn+fp+fn);
            Log.d("Acc","accuracy:"+accuracy);
        }
        //Log.v("my", "flag2: "+AppConstant.flag2);
        AppConstant.flag1 = true;
        Log.v("my","displayList:" + AppConstant.dupList);
        Intent ntent = new Intent(this,DuplicateActivity.class);
        ntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //ntent.putExtra("dupList",dupList);
        startActivity(ntent);

        //broadcastIntent.setAction(DuplicateActivity.mBroadcastArrayListAction);
        //broadcastIntent.putExtra("Data", dupList);
        //sendBroadcast(broadcastIntent);


    }
}

