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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LectureList extends AppCompatActivity {
    String NameOfSubject,NameOfClass,NameOFChapter;
    RecyclerView recyclerView;
    List<ListLectureList> listLectureLists;
    AdapterLecture adapterLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_list);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.top));
        }
        NameOFChapter = getIntent().getStringExtra("NameOFChapter");
        NameOfSubject = getIntent().getStringExtra("NameOfSubject");
        NameOfClass = getIntent().getStringExtra("NameOfClass");
        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toast.makeText(getApplicationContext(),"RLBvideodata/VIDEODATA/"+NameOfClass+"/SubjectName/"+NameOfSubject+"/ChapterName/"+NameOFChapter,Toast.LENGTH_LONG).show();
        FindInDataBase();
        SetThisTestInTop(NameOFChapter);
    }

    private void SetThisTestInTop(String setter) {
        TextView ChapterName = findViewById(R.id.ChapterName);
        ChapterName.setText(setter);
    }

    private void FindInDataBase() {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db
                .collection("RLBvideodata/VIDEODATA/"+NameOfClass+"/SubjectName/"+NameOfSubject+"/ChapterName/"+NameOFChapter)
                .document("Lecture");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listLectureLists = new ArrayList<>();
                        int StartingNumber = 1;
                        Toast.makeText(getApplicationContext(),document.getData().toString(),Toast.LENGTH_LONG).show();
                        while (document.get("VideoTEXT"+String.valueOf(StartingNumber)) != null && document.get("VideoURL"+String.valueOf(StartingNumber)) != null ){
                            listLectureLists.add(new ListLectureList(document.get("VideoURL"+String.valueOf(StartingNumber)).toString(),document.get("VideoTEXT"+String.valueOf(StartingNumber)).toString(),NameOFChapter));
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
        adapterLecture = new AdapterLecture(this, listLectureLists);
        recyclerView.setAdapter(adapterLecture);
    }

    public void userItemClick(int adapterPosition) {
        Intent intent = new Intent(this, ShowAllMyYoutubeVideo.class);
        intent.putExtra("myURL", listLectureLists.get(adapterPosition).getLectureUrl());
        startActivity(intent);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
