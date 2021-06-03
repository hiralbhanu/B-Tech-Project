package com.example.imageclassifier;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class CalculateKnown extends IntentService {
    private ArrayList<String> fileList = new ArrayList<>();

    public CalculateKnown() {
        super("CalculateKnown");
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
        Intent broadcastIntent = new Intent();
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        if (AppConstant.flag3 == false) {

String pathname1= Environment.getExternalStorageDirectory().toString() + "/Training/";
String pathname2= Environment.getExternalStorageDirectory().toString() + "/Testing/";
            File root1 = new File(pathname1);
            File files1[] = root1.listFiles();
            int l1=files1.length;
            File root2 = new File(pathname2);

            File files2[] = root2.listFiles();
            int l2=files2.length;
            try {
                Log.e("my","Hiiiiii");
            Socket s = new Socket("192.168.43.131", 40132);
            Log.e("my","Hiiiiii");
            //DataInputStream din = new DataInputStream(new BufferedInputStream(s.getInputStream()));
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

           /* ServerSocket s1 = new ServerSocket(50000);
            Socket ss= s1.accept();*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));


            dos.writeInt(l1);
            dos.writeInt(l2);
            for (File f1 : files1) {
                int type = 0;
                String f= f1.getName();
                String name = pathname1+f;
                Log.e("my","Path : "+name);
                Log.e("my","Name : "+f);

                Log.e("my","Image: "+name);


                FileInputStream fis = new FileInputStream(name);
                Log.e("my","fl iteration");
                int size = fis.available();

                byte[] data = new byte[size];
                fis.read(data);
                dos.writeInt(size);
                dos.writeInt(f.length());
                dos.writeInt(type);
                dos.writeBytes(f);
                Log.e("my","fl iteration1");
                dos.write(data);


                dos.flush();
                    //dos.close();
                fis.close();


            }

                for (File f2 : files2) {
                    int type = 1;
                    String f= f2.getName();
                    String name = pathname2+f;



                    //DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                    FileInputStream fis = new FileInputStream(name);
                    int size = fis.available();

                    byte[] data = new byte[size];
                    fis.read(data);
                    dos.writeInt(size);
                    dos.writeInt(f.length());
                    dos.writeInt(type);
                    dos.writeBytes(f);
                    dos.write(data);


                    dos.flush();
                    fis.close();



                }
                s.shutdownOutput();
                String st = reader.readLine();
                String[] returned = st.split("!");
                int knownlen = Integer.parseInt(returned[0]);
                Log.e("my","Length "+knownlen);
                Log.e("my","Hiiiiiii");
                for(int i=1; i<returned.length;i++) {
                    String known = returned[i];

                    AppConstant.knownList.add(pathname2+known);
                }
                s.close();
                //din.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        AppConstant.flag3 = true;
        Log.e("my","Known: "+AppConstant.knownList);
        Intent ntent = new Intent(this,KnownActivity.class);
        ntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ntent);
    }



}
