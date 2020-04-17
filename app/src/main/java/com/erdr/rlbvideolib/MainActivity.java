package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

        SecondPage();
    }

    private void SecondPage() {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("RLBvideodata").document("VIDEODATA");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        NowOPenTHis(document.getData());
                    } else {
                    }
                } else {
                }
            }
        });


    }

    private void NowOPenTHis(Map<String, Object> data) {
        Intent intent = new Intent(this, ClassPage.class);
        if(data.get("12Class") != null)  {
            intent.putExtra("12Class",data.get("12Class").toString());
        }
        else {
            intent.putExtra("12Class","NO");
        }
        if(data.get("11Class") != null)  {
            intent.putExtra("11Class",data.get("11Class").toString());
        }
        else {
            intent.putExtra("11Class","NO");
        }
        if(data.get("10Class") != null)  {
            intent.putExtra("10Class",data.get("10Class").toString());
        }
        else {
            intent.putExtra("10Class","NO");
        }
        if(data.get("9Class") != null)  {
            intent.putExtra("9Class",data.get("9Class").toString());
        }
        else {
            intent.putExtra("9Class","NO");
        }
        if(data.get("8Class") != null)  {
            intent.putExtra("8Class",data.get("8Class").toString());
        }
        else {
            intent.putExtra("8Class","NO");
        }
        if(data.get("7Class") != null)  {
            intent.putExtra("7Class",data.get("7Class").toString());
        }
        else {
            intent.putExtra("7Class","NO");
        }
        if(data.get("6Class") != null)  {
            intent.putExtra("6Class",data.get("6Class").toString());
        }
        else {
            intent.putExtra("6Class","NO");
        }
        if(data.get("5Class") != null)  {
            intent.putExtra("5Class",data.get("5Class").toString());
        }
        else {
            intent.putExtra("5Class","NO");
        }
        if(data.get("4Class") != null)  {
            intent.putExtra("4Class",data.get("4Class").toString());
        }
        else {
            intent.putExtra("4Class","NO");
        }
        if(data.get("3Class") != null)  {
            intent.putExtra("3Class",data.get("3Class").toString());
        }
        else {
            intent.putExtra("3Class","NO");
        }
        if(data.get("2Class") != null)  {
            intent.putExtra("2Class",data.get("2Class").toString());
        }
        else {
            intent.putExtra("2Class","NO");
        }
        if(data.get("1Class") != null)  {
            intent.putExtra("1Class",data.get("1Class").toString());
        }
        else {
            intent.putExtra("1Class","NO");
        }

        startActivity(intent);
    }
}
