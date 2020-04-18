package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChapterName extends AppCompatActivity {
    String NameOfSubject,NameOfClass;
    RecyclerView recyclerView;
    List<ListChapterName> listChapterNames;
    AdapterChapterName adapterChapterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_name);
        NameOfSubject = getIntent().getStringExtra("NameOfSubject");
        NameOfClass = getIntent().getStringExtra("NameOfClass");
        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FindInDataBase();
        SetThisTestInTop(NameOfSubject);
    }

    private void SetThisTestInTop(String setter) {
        TextView ChapterName = findViewById(R.id.ChapterName);
        ChapterName.setText(setter);
    }

    private void FindInDataBase() {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db
                .collection(getString(R.string.schoolNamelectureFileAddress)+"/VIDEODATA/"+NameOfClass+"/SubjectName/"+NameOfSubject)
                .document("ChapterName");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listChapterNames = new ArrayList<>();
                        int StartingNumber = 1;
                        while (document.get("Chapter"+String.valueOf(StartingNumber)) != null){
                            listChapterNames.add(new ListChapterName(document.get("Chapter"+String.valueOf(StartingNumber)).toString()));
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
        adapterChapterName = new AdapterChapterName(this, listChapterNames);
        recyclerView.setAdapter(adapterChapterName);
    }

    public void userItemClick(int adapterPosition) {
        Intent intent = new Intent(this, LectureList.class);
        intent.putExtra("NameOFChapter",listChapterNames.get(adapterPosition).getChapterName());
        intent.putExtra("NameOfSubject",NameOfSubject);
        intent.putExtra("NameOfClass",NameOfClass);
        startActivity(intent);
    }

    public void goBack(View view) {
        super.onBackPressed();
    }
}
