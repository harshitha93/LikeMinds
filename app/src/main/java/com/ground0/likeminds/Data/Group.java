package com.ground0.likeminds.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Arjun on 26-08-2015.
 */
public class Group {

    String groupName, groupDescription;
    int background;

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    Drawable image; //open to change

    public Group(String groupName, String groupDescription, int background){
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.background = background;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
