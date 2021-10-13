package com.alvesgleibson.messageappfamily.helper;

import android.util.Base64;

public class Base64Costum {


    public static String encodeBase64(String st){
       return Base64.encodeToString(st.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r","");
    }

    public static String decodeBase64(String st){
        return new String(Base64.decode(st, Base64.DEFAULT));
    }


}
