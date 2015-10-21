package com.ground0.likeminds.Data;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Arjun on 25-08-2015.
 */
public class Feed {

    public static class Polls {

        List<String> answerList;
        HashMap<String, Integer> answers;
        boolean answered = false;


        public Polls(List<String> answerList, HashMap<String, Integer> answers)
        {
            this.answerList = answerList;
            this.answers = answers;
        }

        public List<String> getAnswerList() {
            return answerList;
        }

        public void setAnswerList(List<String> answerList) {
            this.answerList = answerList;
        }

        public HashMap<String, Integer> getAnswers() {
            return answers;
        }

        public void setAnswers(HashMap<String, Integer> answers) {
            this.answers = answers;
        }

        public boolean isAnswered() {
            return answered;
        }

        public void setAnswered(boolean answered) {
            this.answered = answered;
        }

    }

    Polls poll;



    public Polls getPoll() {
        return poll;
    }

    public void setPoll(Polls poll) {
        this.poll = poll;
    }

    String content,date,groupName,author,type;

    int nlike, ncomment, nshare ;






    public void setN(int nlike, int ncomment, int nshare)
    {
        this.nlike = nlike;
        this.ncomment = ncomment;
        this.nshare = nshare;
    }

    public int getNlike() {
        return nlike;
    }

    public void setNlike(int nlike) {
        this.nlike = nlike;
    }

    public int getNcomment() {
        return ncomment;
    }

    public void setNcomment(int ncomment) {
        this.ncomment = ncomment;
    }

    public int getNshare() {
        return nshare;
    }

    public void setNshare(int nshare) {
        this.nshare = nshare;
    }

    //Image Holder
    Drawable image;     //Open for change in type

    public Drawable getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Drawable profileImage) {
        this.profileImage = profileImage;
    }

    Drawable profileImage; // Open for change in type

    public Feed( String content, String date, String groupName, String author, @Nullable Drawable image)
    {

        this.author = author;
        this.content = content;
        this.date = date;
        this.groupName = groupName;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public Feed()
    {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
