package com.erdr.rlbvideolib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class LectureList extends AppCompatActivity {
    String NameOfSubject,NameOfClass,NameOFChapter;

    List<ListStudentMcqSolution> screenItemForStudentMEQS;
    int LectureCount = 0,AssignCount = 0,NotesCount = 0;
    ArrayList<String> listOFURL;
    ArrayList<String> listOFTEXT;
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
                        listOFTEXT = new ArrayList<>();

                        while (document.get("VideoURL" +String.valueOf(LectureCount+1)) != null && document.get("VideoTEXT" +String.valueOf(LectureCount+1)) != null){
                            listOFURL.add(document.get("VideoURL" +String.valueOf(LectureCount+1)).toString());
                            listOFTEXT.add(document.get("VideoTEXT" +String.valueOf(LectureCount+1)).toString());
                            LectureCount++;
                        }

                        while (document.get("AssignmentINFO" +String.valueOf(AssignCount+1)) != null){
                            listOFTEXT.add(document.get("AssignmentINFO" +String.valueOf(AssignCount+1)).toString());

                            AssignCount++;
                        }

                        while (document.get("NotesINFO" +String.valueOf(NotesCount+1)) != null){
                            listOFTEXT.add(document.get("NotesINFO" +String.valueOf(NotesCount+1)).toString());

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
                //OPENPDF(listOFURL.get(adapterPosition +LectureCount),listOFTEXT.get(adapterPosition +LectureCount));
                download(listOFTEXT.get(adapterPosition +LectureCount));
                break;
            case 2:
                download(listOFTEXT.get(adapterPosition +LectureCount+AssignCount));
                //OPENPDF(listOFURL.get(adapterPosition +LectureCount+AssignCount),listOFTEXT.get(adapterPosition +LectureCount+AssignCount));
                break;
        }
    }

    private void OPENPDF(String s, String s1) {
//MyPDFFile
        Intent intent = new Intent(this, OpenMyPdf.class);
        intent.putExtra("MyPDFFile", s);
        intent.putExtra("PDF", s1);
        startActivity(intent);
    }

    private void OPENYOUTUBE(String s) {
        Intent intent = new Intent(this, ShowAllMyYoutubeVideo.class);
        intent.putExtra("myURL", s);
        startActivity(intent);
    }

    public void download(final String pdfName) {
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference = mStorageRef.child(pdfName+".pdf");

        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                downloadURL( pdfName,".pdf",DIRECTORY_DOWNLOADS,url);
            }
        });

    }

    private void downloadURL( String upploadee, String s, String directoryDownloads, String url) {
        Context mCtx = getApplicationContext();
        DownloadManager downloadManager = (DownloadManager) mCtx.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(mCtx,directoryDownloads,upploadee+s);
        downloadManager.enqueue(request);
    }
}
