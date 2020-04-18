package com.erdr.rlbvideolib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterLectureFileScreenPager extends PagerAdapter {

    Context mCtx;
    List<ListStudentMcqSolution> screenItemsList;
    String display ="-";


    RecyclerView recyclerView;
    List<ListLectureList> listLectureListsLecture,listLectureListsAss,listLectureListsNotes;
    AdapterLecture adapterLecture;


    public AdapterLectureFileScreenPager(Context mCtx, List<ListStudentMcqSolution> screenItemsList) {
        this.mCtx = mCtx;
        this.screenItemsList = screenItemsList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layoutScreen = inflater.inflate(R.layout.scrren_page_lecture_rec,null);
        SettingTheQuestion(layoutScreen,position);
        container.addView(layoutScreen);
        return layoutScreen;
    }

    private void SettingTheQuestion(View layoutScreen, final int position) {
        switch (position){
            case 0:
                SettingTheLecture(layoutScreen);
                break;
            case 1:
                SettingTheAss(layoutScreen);
                break;
            case 2:
                SettingTheNote(layoutScreen);
                break;
        }


    }

    private void SettingTheNote(View layoutScreen) {
        int count = 1;
        listLectureListsNotes = new ArrayList<>();

        while (screenItemsList.get(0).document.get("NotesINFO" + String.valueOf(count)) != null && screenItemsList.get(0).document.get("NotesURL" + String.valueOf(count)) != null){
            listLectureListsNotes.add(new ListLectureList(screenItemsList.get(0).document.get("NotesURL" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("NotesINFO" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("ChapterName").toString()));
            count++;
        }

        recyclerView = layoutScreen.findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        adapterLecture = new AdapterLecture(mCtx, listLectureListsNotes);
        recyclerView.setAdapter(adapterLecture);

    }

    private void SettingTheAss(View layoutScreen) {
        int count = 1;
        listLectureListsAss = new ArrayList<>();

        while (screenItemsList.get(0).document.get("AssignmentINFO" + String.valueOf(count)) != null && screenItemsList.get(0).document.get("AssignmentURL" + String.valueOf(count)) != null){
            listLectureListsAss.add(new ListLectureList(screenItemsList.get(0).document.get("AssignmentURL" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("AssignmentINFO" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("ChapterName").toString()));
            count++;
        }

        recyclerView = layoutScreen.findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        adapterLecture = new AdapterLecture(mCtx, listLectureListsAss);
        recyclerView.setAdapter(adapterLecture);

    }

    private void SettingTheLecture(View layoutScreen) {
        int count = 1;
        listLectureListsLecture = new ArrayList<>();

        while (screenItemsList.get(0).document.get("VideoTEXT" + String.valueOf(count)) != null && screenItemsList.get(0).document.get("VideoURL" + String.valueOf(count)) != null){
            listLectureListsLecture.add(new ListLectureList(screenItemsList.get(0).document.get("VideoURL" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("VideoTEXT" + String.valueOf(count)).toString(),
                    screenItemsList.get(0).document.get("ChapterName").toString()));
        count++;
    }

        recyclerView = layoutScreen.findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        adapterLecture = new AdapterLecture(mCtx, listLectureListsLecture);
        recyclerView.setAdapter(adapterLecture);

    }

    @Override
    public int getCount() {
        return screenItemsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}