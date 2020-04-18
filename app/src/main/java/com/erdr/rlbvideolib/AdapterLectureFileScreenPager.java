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
    List<ListLectureList> listLectureLists;
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
        listLectureLists = new ArrayList<>();
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        listLectureLists.add(new ListLectureList("ada","ad","asd"));
        recyclerView = layoutScreen.findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
        adapterLecture = new AdapterLecture(mCtx, listLectureLists);
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