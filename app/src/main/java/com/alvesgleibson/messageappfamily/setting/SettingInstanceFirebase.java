package com.alvesgleibson.messageappfamily.setting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingInstanceFirebase {

    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;

    public static FirebaseAuth getInstanceFirebaseAuth() {

        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static DatabaseReference getDatabaseReference(){

        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;

    }


}
