package com.ground0.likeminds.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Arjun on 25-08-2015.
 */
public class Post {

    String senderName;
    String sendDate;
    String textContent;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    Drawable image; //Open to change in Type

    int nlike, ncomment, nshare ;

    public Post(String senderName, String sendDate, String textContent)
    {
        this.senderName = senderName;
        this.sendDate = sendDate;
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }



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


}
