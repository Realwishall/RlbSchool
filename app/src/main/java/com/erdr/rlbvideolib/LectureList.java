package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import java.util.ArrayList;
import java.util.List;

public class LectureList extends AppCompatActivity {
    String NameOfSubject,NameOfClass,NameOFChapter;

    List<ListStudentMcqSolution> screenItemForStudentMEQS;
    int LectureCount = 0,AssignCount = 0,NotesCount = 0;
    ArrayList<String> listOFURL;
    ViewPager screenpger;
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

         FindInDataBase();
        SetThisTestInTop(NameOFChapter);
    }

    private void ScreenPagerTRY() {


        AdapterLectureFileScreenPager adapterLectureFileScreenPager;

        screenpger = findViewById(R.id.screenvipager);
        adapterLectureFileScreenPager = new AdapterLectureFileScreenPager(this, screenItemForStudentMEQS);
        screenpger.setAdapter(adapterLectureFileScreenPager);
        TabLayout tabLayout = findViewById(R.id.tabL);
        TabLayoutHelper mTabLayoutHelper = new TabLayoutHelper(tabLayout, screenpger);
        tabLayout.getTabAt(0).setText("Lecture");
        tabLayout.getTabAt(1).setText("Assignment");
        tabLayout.getTabAt(2).setText("Notes");

        mTabLayoutHelper.setAutoAdjustTabModeEnabled(true);
    }

    private void SetThisTestInTop(String setter) {
        TextView ChapterName = findViewById(R.id.ChapterName);
        ChapterName.setText(setter);
    }


    private void FindInDataBase() {

        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db
                .collection(getString(R.string.schoolNamelectureFileAddress)+"/VIDEODATA/"+NameOfClass+"/SubjectName/"+NameOfSubject+"/ChapterName/"+NameOFChapter)
                .document("Lecture");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listOFURL = new ArrayList<>();
                        while (document.get("VideoURL" +String.valueOf(LectureCount+1)) != null){
                            listOFURL.add(document.get("VideoURL" +String.valueOf(LectureCount+1)).toString());

                            LectureCount++;
                        }

                        while (document.get("AssignmentURL" +String.valueOf(AssignCount+1)) != null){
                           listOFURL.add(document.get("AssignmentURL" +String.valueOf(AssignCount+1)).toString());

                            AssignCount++;
                        }

                        while (document.get("NotesURL" +String.valueOf(NotesCount+1)) != null){
                            listOFURL.add(document.get("NotesURL" +String.valueOf(NotesCount+1)).toString());

                            NotesCount++;
                        }

                        screenItemForStudentMEQS = new ArrayList<>();
                        screenItemForStudentMEQS.add(new ListStudentMcqSolution(document));
                        screenItemForStudentMEQS.add(new ListStudentMcqSolution(document));
                        screenItemForStudentMEQS.add(new ListStudentMcqSolution(document));

                        ScreenPagerTRY();
                    } else {
                    }
                } else {
                }
            }
        });

    }

    public void goBack(View view) {
        super.onBackPressed();
    }

    public void userItemClick(int adapterPosition) {
        switch (screenpger.getCurrentItem()){
            case 0:
                OPENYOUTUBE(listOFURL.get(adapterPosition));
                break;
            case 1:
                OPENPDF(listOFURL.get(adapterPosition +LectureCount));

                break;
            case 2:
                OPENPDF(listOFURL.get(adapterPosition +LectureCount+AssignCount));
                break;
        }
    }

    private void OPENPDF(String s) {
//MyPDFFile
        Intent intent = new Intent(this, OpenMyPdf.class);
        intent.putExtra("MyPDFFile", s);
        startActivity(intent);
    }

    private void OPENYOUTUBE(String s) {
        Intent intent = new Intent(this, ShowAllMyYoutubeVideo.class);
        intent.putExtra("myURL", s);
        startActivity(intent);
    }
}
