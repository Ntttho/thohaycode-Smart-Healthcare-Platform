package com.reptithcm.edu.hospital_website.ultility;


import java.util.Arrays;

public class Hashing {
    public static String encoding(String reString){
        StringBuilder result = new StringBuilder(reString);
        return result.reverse().toString();
    }
}
