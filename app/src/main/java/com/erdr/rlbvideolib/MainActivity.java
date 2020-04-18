package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        ImageView myimg = findViewById(R.id.myimg);
        Picasso.get().load(R.drawable.logo).into(myimg);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(getApplicationContext(),currentUser.getPhoneNumber(),Toast.LENGTH_LONG).show();
        SecondPage();
    }

    private void SecondPage() {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("USERDATA")
                .document(currentUser.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        AfterClassSelection(document.get("classInfo").toString());
                    } else {
                        SaveTheDetails();
                    }
                } else {
                }
            }
        });


    }

    private void SaveTheDetails() {
        Intent intent = new Intent(this, FullDetailsInfo.class);
        startActivity(intent);
    }

    private void AfterClassSelection(String classInfo) {
        Intent intent = new Intent(this, SubjectPage.class);
        intent.putExtra("NameOfClass",classInfo);
        startActivity(intent);
    }

}
