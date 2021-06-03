package com.example.imageclassifier;
import com.example.imageclassifier.AppConstant;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CalculateDuplicate extends IntentService {
    private ArrayList<String> fileList = new ArrayList<>();

    public CalculateDuplicate() {
        super("CalculateDuplicate");
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
            double mse[][];
            Log.v("my", Environment.getExternalStorageDirectory().getPath().toString());
            //String pathname = Environment.getExternalStorageDirectory().toString()+"/DatasetDup/";
            File root = new File(Environment.getExternalStorageDirectory().toString()+"/DatasetDup/");
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
            for (String f2 : files) {
              //  Log.d("my", f2);
                //String f2 = root + f;

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

            for (Map.Entry<String, Integer> entry : list) {
                String key = entry.getKey();
                AppConstant.dupList.add(key);
                // create custom string for each map element
                //String testString = key + "=" + value;
                //resultString +=  testString ;
            }
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
