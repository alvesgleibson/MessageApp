package com.alvesgleibson.messageappfamily.setting;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class SettingInstanceFirebase {

    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference;
    private static StorageReference storageReference;

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

    public static StorageReference getStorageReference(){

        if (storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }


}
