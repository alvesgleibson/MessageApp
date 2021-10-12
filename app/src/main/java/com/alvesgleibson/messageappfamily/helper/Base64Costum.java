package com.alvesgleibson.messageappfamily.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;

public class Base64Costum {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeBase64(String st){
        return Base64.getEncoder().encodeToString(st.getBytes()).replaceAll("\\n|\\r","");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decodeBase64(String st){
        return new String(Base64.getDecoder().decode(st.getBytes()));
    }


}
