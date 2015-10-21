package com.ground0.likeminds.Data;

import android.media.Image;

/**
 * Created by Arjun on 05-09-2015.
 */
public class ProfileListItem {

    String data, title;

    Image icon;

    public ProfileListItem(String data, String title)
    {
        this.title = title;
        this.data = data;
    }

    public ProfileListItem(String data, String title, Image icon) {
        this.data = data;
        this.title = title;
        this.icon = icon;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
