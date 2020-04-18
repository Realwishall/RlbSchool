package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SubjectPage extends AppCompatActivity {
    String NameOfClass;
    RecyclerView recyclerView;
    List<ListSubjectPage> listSubjectPages;
    AdapterSubjectPage adapterSubjectPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_page);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NameOfClass = getIntent().getStringExtra("NameOfClass");


        GetNameOfSubject();
        SetThisTestInTop(NameOfClass);
    }

    private void SetThisTestInTop(String setter) {
        TextView ChapterName = findViewById(R.id.ChapterName);
        ChapterName.setText(setter);
    }

    private void GetNameOfSubject() {
        String FullAddress = getString(R.string.schoolNamelectureFileAddress)+"/VIDEODATA/"+NameOfClass;
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db
                .collection(FullAddress)
                .document("SubjectName");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listSubjectPages  = new ArrayList<>();
                        int StartingNumber = 1;
                        while (document.get("Subject"+String.valueOf(StartingNumber)) != null){
                            listSubjectPages.add(new ListSubjectPage(document.get("Subject"+String.valueOf(StartingNumber)).toString()));
                            StartingNumber++;
                        }
                        StartTheAdapter();

                    } else {
                    }
                } else {
                }
            }
        });

    }

    private void StartTheAdapter() {
        adapterSubjectPage = new AdapterSubjectPage(this,listSubjectPages);
        recyclerView.setAdapter(adapterSubjectPage);
    }

    public void userItemClick(int adapterPosition) {
        Intent intent = new Intent(this, ChapterName.class);
        intent.putExtra("NameOfSubject",listSubjectPages.get(adapterPosition).getSubjectName());
        intent.putExtra("NameOfClass",NameOfClass);
        startActivity(intent);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}