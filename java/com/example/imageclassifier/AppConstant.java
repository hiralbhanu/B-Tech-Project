package com.example.imageclassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstant {
    public static final ArrayList<String> dupList = new ArrayList<>();
    public static final ArrayList<String> plainList = new ArrayList<>();
    public static final ArrayList<String> personList = new ArrayList<>();
    public static final ArrayList<String> scanList = new ArrayList<>();
    public static final ArrayList<String> knownList = new ArrayList<>();

    public static boolean flag1 = false;
    public static boolean flag2 = false;
    public static boolean flag3 = false;

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 2;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp



    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg",
            "png");
}
