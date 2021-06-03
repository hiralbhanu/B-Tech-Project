package com.example.imageclassifier;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CalculateText2 extends IntentService {
    private ArrayList<String> fileList = new ArrayList<>();

    public CalculateText2() {
        super("CalculateText2");
    }
    private void checkFile(File dir , String datapath) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles(datapath);
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(datapath);
            }
        }
    }
    private void checkFile2(File dir , String datapath2) {
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles2(datapath2);
        }
        if(dir.exists()) {
            //String datafilepath1 = datapath2 + "haarcascade_frontalface_default.xml";
            String datafilepath1 = datapath2 + "haarcascade_frontalface_alt.xml";
            String datafilepath2 = datapath2 + "haarcascade_profileface.xml";
            File datafile1 = new File(datafilepath1);
            File datafile2 = new File(datafilepath2);
            if (!datafile1.exists()) {
                copyFiles2(datapath2);
            }
            if (!datafile2.exists()) {
                copyFiles2(datapath2);
            }
        }
    }
    private void copyFiles(String datapath) {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();
            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void copyFiles2(String datapath2) {
        try {
            //String filepath1 = datapath2 + "haarcascade_frontalface_default.xml";
            String filepath1 = datapath2 + "haarcascade_frontalface_alt.xml";
            String filepath2 = datapath2 + "haarcascade_profileface.xml";
            AssetManager assetManager = getAssets();
            InputStream instream1 = assetManager.open("haarcascades/haarcascade_frontalface_alt.xml");
            //InputStream instream1 = assetManager.open("haarcascades/haarcascade_frontalface_default.xml");
            InputStream instream2 = assetManager.open("haarcascades/haarcascade_profileface.xml");
            OutputStream outstream1 = new FileOutputStream(filepath1);
            OutputStream outstream2 = new FileOutputStream(filepath2);
            byte[] buffer1 = new byte[1024];
            int read1;
            while ((read1 = instream1.read(buffer1)) != -1) {
                outstream1.write(buffer1, 0, read1);
            }
            outstream1.flush();
            outstream1.close();
            instream1.close();
            File file1 = new File(filepath1);
            if (!file1.exists()) {
                throw new FileNotFoundException();
            }


            byte[] buffer2 = new byte[1024];
            int read2;
            while ((read2 = instream2.read(buffer2)) != -1) {
                outstream2.write(buffer2, 0, read2);
            }
            outstream2.flush();
            outstream2.close();
            instream2.close();
            File file2 = new File(filepath2);
            if (!file2.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    protected void onHandleIntent(Intent intent) {
        if(AppConstant.flag2 == false) {
            ArrayList<String> ll1 = new ArrayList<>();
            ArrayList<String> ll2 = new ArrayList<>();
            ArrayList<String> ll3 = new ArrayList<>();
            String pathAcc = Environment.getExternalStorageDirectory().toString() + "/TextData/";
            String[] plain = {pathAcc+"IMG-20190410-WA0000.jpg",pathAcc+"as.jpg",pathAcc+"clk.jpg",pathAcc+"default.jpg",pathAcc+"df.jpg",pathAcc+"images.jpg",pathAcc+"images1.jpg",pathAcc+"FB_IMG.jpg",pathAcc+"end.jpg",pathAcc+"img6.jpg",pathAcc+"smi.jpg",pathAcc+"sd.jpg",pathAcc+"school.jpg",pathAcc+"mou.jpg",pathAcc+"zin.jpg"};
            String[] person = {pathAcc+"text.jpg",pathAcc+"cou.jpg",pathAcc+"images2.jpg",pathAcc+"download.jpg",pathAcc+"koli.jpg",pathAcc+"modi.jpg",pathAcc+"mlk.jpg",pathAcc+"text.jpg"};
            String[] scanned = {pathAcc+"IMG-20190408-WA0005.jpg",pathAcc+"IMG-20190404-WA0000.jpg",pathAcc+"IMG-20190409-WA0000.jpg",pathAcc+"IMG-20190402-WA0000.jpg",pathAcc+"IMG-20180606-WA0017.jpg",pathAcc+"downloadd.jpg",pathAcc+"imagesdown.jpg",pathAcc+"imagest.jpg",pathAcc+"imagestext.jpg",pathAcc+"skim.jpg"};

            //File root = new File(Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Images/");
            File root = new File(Environment.getExternalStorageDirectory().toString() + "/TextData/");
            int cou = 0;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 4;
            TessBaseAPI baseApi = new TessBaseAPI();
            baseApi.setDebug(true);

            //baseApi.init(pathname,"eng");
            // baseApi.init("C:\\tess\\tess-two\\jni\\com_googlecode_tesseract_android\\src\\", "eng");
            //String datapath = Environment.getExternalStorageDirectory() + "/tesseract/";
            String datapath = getFilesDir() + "/tesseract/";
            String language = "eng";

            checkFile(new File(datapath + "tessdata/"), datapath);
            File textdir = new File(getFilesDir() + File.separator + "TextData1");
            if (!textdir.exists()) {
                textdir.mkdirs();
            }
            String datapath2 = getFilesDir() + "/haarcascades/";

            checkFile2(new File(datapath2), datapath2);
            ArrayList<String> files = getFile(root);

            CascadeClassifier faceDetector = new CascadeClassifier(datapath2 + "haarcascade_frontalface_alt.xml");
            //CascadeClassifier faceDetector11 = new CascadeClassifier(datapath2 + "haarcascade_frontalface_alt2.xml");
            //CascadeClassifier faceDetector = new CascadeClassifier(datapath2 + "haarcascade_frontalface_default.xml");
            CascadeClassifier pfaceDetector = new CascadeClassifier(datapath2 + "haarcascade_profileface.xml");
            //String[] files = new File(pathname).list();
            Log.v("my", "timestart:" + SystemClock.currentThreadTimeMillis());
            HashMap<String, Integer> plainMap = new HashMap<>();
            HashMap<String, Integer> personMap = new HashMap<>();
            HashMap<String, Integer> scanMap = new HashMap<>();


            for (String file_name : files) {
                cou = cou + 1;
                Log.e("my", "Count " + cou);
                Log.e("my", "File " + file_name);
                //Imgcodecs imageCodecs = new Imgcodecs();
                //Reading the Image from the file
                //String file ="C:/EXAMPLES/OpenCV/sample.jpg";

                if(Arrays.asList(plain).contains(file_name))
                    plainMap.put(file_name,1);
                else
                    plainMap.put(file_name,0);
                if(Arrays.asList(person).contains(file_name))
                    personMap.put(file_name,1);
                else
                    personMap.put(file_name,0);
                if(Arrays.asList(scanned).contains(file_name))
                    scanMap.put(file_name,1);
                else
                    scanMap.put(file_name,0);

                Mat img = Imgcodecs.imread(file_name);
                // Log.d("my", "Image Loaded");
                int rows = img.rows();

                int cols = img.cols();
                int tot = rows * cols;
                //tot = 400 * 300;
                //Log.d("my", "rows: " + rows);
                //Log.d("my", "cols: " + cols);
                //Log.d("my", "tot: " + tot);
                Mat img_final = Imgcodecs.imread(file_name);
                //Mat resizeimage = new Mat();
                //Size resizeImg = new Size(400,300);
                //Imgproc.resize(img, resizeimage, resizeImg , 0, 0, Imgproc.INTER_AREA);
                Mat img2gray = new Mat();
                Imgproc.cvtColor(img, img2gray, Imgproc.COLOR_BGR2GRAY);
                Mat thresImg = new Mat();
                Imgproc.threshold(img2gray, thresImg, 180, 255, Imgproc.THRESH_BINARY);
                Mat andImg = new Mat();
                Core.bitwise_and(thresImg, thresImg, andImg);
                Mat adaptiveImg = new Mat();
                Imgproc.adaptiveThreshold(andImg, adaptiveImg, 125, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 11, 2);
                //nnew_img = cv2.adaptiveThreshold(image_final, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY_INV, 11, 2)
                //Mat dilImg = new Mat();
                // Preparing the kernel matrix object
                Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
                // Applying dilate on the Image
                //Imgproc.dilate(adaptiveImg, dilImg, kernel);
                List<MatOfPoint> contours = new ArrayList<>();
                Imgproc.findContours(adaptiveImg, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
                //Log.d("my", "contour length:" + contours.size());
                Iterator<MatOfPoint> iterator = contours.iterator();
                double area = 0;
                //Log.d("my", "imgMat width and heeight:" + dilImg.rows() + " " + dilImg.cols());


                while (iterator.hasNext()) {
                    MatOfPoint contour = iterator.next();

                    Rect r = Imgproc.boundingRect(contour);
                    if (r.width < 35 && r.height < 35) {
                        // Log.d("my", "<35");
                        continue;
                    }
                    //Log.d("my", "Rect width and height:" + r.width + " " + r.height);
                    Mat image_roi = new Mat(adaptiveImg, r);
                    Imgcodecs.imwrite(getFilesDir() + "/TextData1/crop.jpg", image_roi);
                    //File croppedFile = new File(Environment.getExternalStorageDirectory().toString()+"/TextData1/crop.jpg");
                    /*ITesseract instance = new Tesseract();
                    String result = "";
                    try {
                        result = instance.doOCR(croppedFile);
                        //return result;
                    } catch (TesseractException e) {
                        System.err.println(e.getMessage());
                        //return "Error while reading image";
                    }*/

                    //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + "/TextData1/crop.jpg", bmOptions);
                    Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/TextData1/crop.jpg", bmOptions);

                    baseApi.init(datapath, language);
                    baseApi.setImage(bitmap);
                    String recognizedText = baseApi.getUTF8Text();
                    //Log.v("my", "recognized Text:" + recognizedText);

                    baseApi.end();
                    if (recognizedText.length() >= 2) {
                        //  Log.d("my", "Length:" + recognizedText.length());
                        int pro = r.width * r.height;
                        if (pro == tot && area == 0)
                            area += pro;
                        if (pro < tot && area < tot)
                            area += pro;
                        //area += pro;
                        //Log.d("my", "area: " + area);
                    }
                    //croppedFile.delete();
                }
                //String base = Environment.getExternalStorageDirectory() + "/haarcascades";

                MatOfRect faceVectors = new MatOfRect();
                faceDetector.detectMultiScale(img, faceVectors);
                MatOfRect pfaceVectors = new MatOfRect();
                pfaceDetector.detectMultiScale(img, pfaceVectors);
                Log.e("my", "\nfaceVector: " + (faceVectors).toArray().length);
                Log.e("my", "\npfaceVector: " + (pfaceVectors).toArray().length);
                //if((faceVectors).toArray().length > 0 ||(pfaceVectors).toArray().length > 0)
                double rat = (area * 1.0) / tot;
                Log.d("my", "ratio: " + rat);
                if(rat < 0.05 || rat == 1){
                    Imgproc.adaptiveThreshold(andImg, adaptiveImg, 125, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 11, 2);
                    //nnew_img = cv2.adaptiveThreshold(image_final, 255, cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY_INV, 11, 2)
                    //dilImg = new Mat();
                    // Preparing the kernel matrix object
                    kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(3, 3));
                    // Applying dilate on the Image
                    //Imgproc.dilate(adaptiveImg, dilImg, kernel);
                    contours = new ArrayList<>();
                    Imgproc.findContours(adaptiveImg, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
                    //  Log.d("my", "contour length:" + contours.size());
                    iterator = contours.iterator();
                    area = 0;
                    //Log.d("my", "imgMat width and heeight:" + dilImg.rows() + " " + dilImg.cols());


                    while (iterator.hasNext()) {
                        MatOfPoint contour = iterator.next();

                        Rect r = Imgproc.boundingRect(contour);
                        if (r.width < 35 && r.height < 35) {
                            //Log.d("my", "<35");
                            continue;
                        }
                        //  Log.d("my", "Rect width and height:" + r.width + " " + r.height);
                        Mat image_roi = new Mat(adaptiveImg, r);
                        Imgcodecs.imwrite(getFilesDir() + "/TextData1/crop.jpg", image_roi);
                        //File croppedFile = new File(Environment.getExternalStorageDirectory().toString()+"/TextData1/crop.jpg");
                    /*ITesseract instance = new Tesseract();
                    String result = "";
                    try {
                        result = instance.doOCR(croppedFile);
                        //return result;
                    } catch (TesseractException e) {
                        System.err.println(e.getMessage());
                        //return "Error while reading image";
                    }*/

                        //Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + "/TextData1/crop.jpg", bmOptions);
                        Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir() + "/TextData1/crop.jpg", bmOptions);

                        baseApi.init(datapath, language);
                        baseApi.setImage(bitmap);
                        String recognizedText = baseApi.getUTF8Text();
                        //Log.v("my", "recognized Text:" + recognizedText);

                        baseApi.end();
                        if (recognizedText.length() >= 2) {
                            //  Log.d("my", "Length:" + recognizedText.length());
                            int pro = r.width * r.height;
                            if (pro == tot && area == 0)
                                area += pro;
                            if (pro < tot && area < tot)
                                area += pro;
                            //area += pro;
                            //Log.d("my", "area: " + area);
                        }
                        //croppedFile.delete();
                    }
                    //String base = Environment.getExternalStorageDirectory() + "/haarcascades";

                    //Log.d("my", "\nfaceVector: " + (faceVectors).toArray().length);
                    //Log.d("my", "\npfaceVector: " + (pfaceVectors).toArray().length);
                    //if((faceVectors).toArray().length > 0 ||(pfaceVectors).toArray().length > 0)
                    rat = (area * 1.0) / tot;
                    Log.e("my", "ratio: " + rat);
                }
                int faceV = faceVectors.toArray().length;
                int sideProfileV = (pfaceVectors).toArray().length;
                int totalF = faceV + sideProfileV;
                if (totalF>0 && totalF<3 && rat > 0.05 && rat <=0.89 ) {
                    ll1.add(file_name);
                    Log.e("my", "Person: " + rat);
                } else if (rat > 0.03 && rat <= 0.43 && (faceVectors).toArray().length == 0 && (pfaceVectors).toArray().length ==0) {
                    ll2.add(file_name);
                    Log.e("my", "Plain: " + rat);
                } else if (rat > 0.43) {
                    ll3.add(file_name);
                    Log.e("my", "Scanned: " + rat);
                } else
                    Log.e("my", "#####Not classified area/tot: " + rat);
            }
            Log.v("my", "Personality:" + ll1);
            Log.v("my", "Plain:" + ll2);
            Log.v("my", "Scanned:" + ll3);
            //int i = 0;
            for (String s1 : ll2)
                AppConstant.plainList.add(s1);
            for (String s2 : ll1)
                AppConstant.personList.add(s2);
            for (String s3 : ll3)
                AppConstant.scanList.add(s3);

            for(int i=0; i<3; i++){
                int tp=0,tn=0,fp=0,fn=0;
                if(i==0)
                {
                    for (String key : ll1) {
                        if(personMap.get(key) ==  1)
                            tp+=1;
                        else if(personMap.get(key) == 0)
                            fp+=1;
                        personMap.remove(key);
                    }
                    for(Map.Entry<String, Integer> entry : personMap.entrySet()){
                        if(entry.getValue() == 0)
                            tn+=1;
                        else if(entry.getValue() == 1)
                            fn+=1;
                    }
                    Log.d("Acc","For Personality Quotes......");
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
                if(i==1)
                {
                    for (String key : ll2) {
                        if(plainMap.get(key) ==  1)
                            tp+=1;
                        else if(plainMap.get(key) == 0)
                            fp+=1;
                        plainMap.remove(key);
                    }
                    for(Map.Entry<String, Integer> entry : plainMap.entrySet()){
                        if(entry.getValue() == 0)
                            tn+=1;
                        else if(entry.getValue() == 1)
                            fn+=1;
                    }
                    Log.d("Acc","For Plain Quotes......");
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
                if(i==2)
                {
                    for (String key : ll3) {
                        if(scanMap.get(key) ==  1)
                            tp+=1;
                        else if(scanMap.get(key) == 0)
                            fp+=1;
                        scanMap.remove(key);
                    }
                    for(Map.Entry<String, Integer> entry : scanMap.entrySet()){
                        if(entry.getValue() == 0)
                            tn+=1;
                        else if(entry.getValue() == 1)
                            fn+=1;
                    }
                    Log.d("Acc","For Scanned Documents......");
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

            }
        }
        Log.v("my", "flag2: "+AppConstant.flag2);
        AppConstant.flag2 = true;
        Log.v("my","timestop:"+ SystemClock.currentThreadTimeMillis());
        Intent n2tent = new Intent(this,TextActivity.class);
        n2tent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // n2tent.putExtra("plainList",ll2);
        // n2tent.putExtra("personList",ll1);
        //n2tent.putExtra("scanList",ll3);
        startActivity(n2tent);
    }
}

