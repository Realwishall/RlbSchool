package com.erdr.rlbvideolib;

public class ListLectureList {

    public String LectureUrl,LectureText,LectureChapter;
    public ListLectureList(){
    }
    public ListLectureList(String LectureUrl,String LectureText,String LectureChapter)
    {
        this.LectureUrl = LectureUrl;
        this.LectureText = LectureText;
        this.LectureChapter = LectureChapter;

    }

    public String getLectureChapter() {
        return LectureChapter;
    }

    public void setLectureChapter(String lectureChapter) {
        LectureChapter = lectureChapter;
    }

    public String getLectureUrl() {
        return LectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        LectureUrl = lectureUrl;
    }

    public String getLectureText() {
        return LectureText;
    }

    public void setLectureText(String lectureText) {
        LectureText = lectureText;
    }
}
