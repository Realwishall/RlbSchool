package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import javax.security.auth.Subject;

public class ClassPage extends AppCompatActivity {
String Class12,Class11,Class10,Class9,Class8,Class7,Class6,Class5,Class4,Class3,Class2,Class1,Class;

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_page);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        Class12 = getIntent().getStringExtra("12Class");
        Class11 = getIntent().getStringExtra("11Class");
        Class10 = getIntent().getStringExtra("10Class");
        Class9 = getIntent().getStringExtra("9Class");
        Class8 = getIntent().getStringExtra("8Class");
        Class7 = getIntent().getStringExtra("7Class");
        Class6 = getIntent().getStringExtra("6Class");
        Class5 = getIntent().getStringExtra("5Class");
        Class4 = getIntent().getStringExtra("4Class");
        Class3 = getIntent().getStringExtra("3Class");
        Class2 = getIntent().getStringExtra("2Class");
        Class1 = getIntent().getStringExtra("1Class");
    ChangingVisibility();
    }

    private void ChangingVisibility() {
        CardView CardClass12 = findViewById(R.id.Class12);
        CardView CardClass11 = findViewById(R.id.Class11);
        CardView CardClass10 = findViewById(R.id.Class10);
        CardView CardClass9 = findViewById(R.id.Class9);
        CardView CardClass8 = findViewById(R.id.Class8);
        CardView CardClass7 = findViewById(R.id.Class7);
        CardView CardClass6 = findViewById(R.id.Class6);
        CardView CardClass5 = findViewById(R.id.Class5);
        CardView CardClass4 = findViewById(R.id.Class4);
        CardView CardClass3 = findViewById(R.id.Class3);
        CardView CardClass2 = findViewById(R.id.Class2);
        CardView CardClass1 = findViewById(R.id.Class1);
        if(Class12.equals("YES")){
            CardClass12.setVisibility(View.VISIBLE);
        }
        else {
            CardClass12.setVisibility(View.GONE);
        }

        if(Class11.equals("YES")){
            CardClass11.setVisibility(View.VISIBLE);
        }
        else {
            CardClass11.setVisibility(View.GONE);
        }

        if(Class10.equals("YES")){
            CardClass10.setVisibility(View.VISIBLE);
        }
        else {
            CardClass10.setVisibility(View.GONE);
        }

        if(Class9.equals("YES")){
            CardClass9.setVisibility(View.VISIBLE);
        }
        else {
            CardClass9.setVisibility(View.GONE);
        }


        if(Class8.equals("YES")){
            CardClass8.setVisibility(View.VISIBLE);
        }
        else {
            CardClass8.setVisibility(View.GONE);
        }


        if(Class7.equals("YES")){
            CardClass7.setVisibility(View.VISIBLE);
        }
        else {
            CardClass7.setVisibility(View.GONE);
        }


        if(Class6.equals("YES")){
            CardClass6.setVisibility(View.VISIBLE);
        }
        else {
            CardClass6.setVisibility(View.GONE);
        }


        if(Class5.equals("YES")){
            CardClass5.setVisibility(View.VISIBLE);
        }
        else {
            CardClass5.setVisibility(View.GONE);
        }


        if(Class4.equals("YES")){
            CardClass4.setVisibility(View.VISIBLE);
        }
        else {
            CardClass4.setVisibility(View.GONE);
        }


        if(Class3.equals("YES")){
            CardClass3.setVisibility(View.VISIBLE);
        }
        else {
            CardClass3.setVisibility(View.GONE);
        }


        if(Class2.equals("YES")){
            CardClass2.setVisibility(View.VISIBLE);
        }
        else {
            CardClass2.setVisibility(View.GONE);
        }


        if(Class1.equals("YES")){
            CardClass1.setVisibility(View.VISIBLE);
        }
        else {
            CardClass1.setVisibility(View.GONE);
        }

        CardClass12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class12");
            }
        });
        CardClass11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class11");
            }
        });
        CardClass10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class10");
            }
        });
        CardClass9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class9");
            }
        });
        CardClass8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class8");
            }
        });
        CardClass7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class7");
            }
        });
        CardClass6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class6");
            }
        });
        CardClass5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class5");
            }
        });
        CardClass4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class4");
            }
        });
        CardClass3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class3");
            }
        });
        CardClass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class2");
            }
        });
        CardClass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartClass("Class1");
            }
        });

    }

    private void StartClass(String classInfo) {

        Intent intent = new Intent(this, SubjectPage.class);
        intent.putExtra("NameOfClass",classInfo);
        startActivity(intent);

    }

}
