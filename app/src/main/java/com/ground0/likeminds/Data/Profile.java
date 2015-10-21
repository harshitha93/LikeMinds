package com.ground0.likeminds.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arjun on 05-09-2015.
 */
public class Profile {

//    String title, name, emailId, image;
//
    String id;

//    public Profile( String id, String title, String name, String image, List<ProfileListItem> personalDetails, List<ProfileListItem> emergencyDetails, List<ProfileListItem> familyDetails, List<ProfileListItem> eventDetails, List<ProfileListItem> orgData/*, List<ProfileListItem> addressDetails*/) {
//        this.title = title;
//        this.name = name;
//        this.image = image;
//        this.id = id;
//        this.personalDetails = personalDetails;
//        this.emergencyDetails = emergencyDetails;
//        this.familyDetails = familyDetails;
//        this.eventDetails = eventDetails;
//        this.orgData = orgData;
////        this.addressDetails = addressDetails;
//    }


    public Profile(String id, List<ProfileListItem> personalDetails, List<ProfileListItem> emergencyDetails, List<ProfileListItem> familyDetails, List<ProfileListItem> eventDetails, List<ProfileListItem> orgData) {
        this.id = id;
        this.personalDetails = personalDetails;
        this.emergencyDetails = emergencyDetails;
        this.familyDetails = familyDetails;
        this.eventDetails = eventDetails;
        this.orgData = orgData;
    }

    public Profile(List<ProfileListItem> orgData, List<ProfileListItem> personalDetails, List<ProfileListItem> emergencyDetails, List<ProfileListItem> familyDetails, List<ProfileListItem> eventDetails) {
        this.orgData = orgData;
        this.personalDetails = personalDetails;
        this.emergencyDetails = emergencyDetails;
        this.familyDetails = familyDetails;
        this.eventDetails = eventDetails;
    }

    public Profile() {
        ProfileListItem item = new ProfileListItem("Nothing to display","");
        List<ProfileListItem> list = new ArrayList<>();
        list.add(item);

        this.orgData = list;
        this.personalDetails = list;
        this.emergencyDetails = list;
        this.familyDetails = list;
        this.eventDetails = list;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    List<ProfileListItem> personalDetails;

//    List<ProfileListItem> addressDetails;

    List<ProfileListItem> emergencyDetails;


    List<ProfileListItem> familyDetails;


    List<ProfileListItem> eventDetails;

    public List<ProfileListItem> getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(List<ProfileListItem> personalDetails) {
        this.personalDetails = personalDetails;
    }

    public List<ProfileListItem> getEmergencyDetails() {
        return emergencyDetails;
    }

    public void setEmergencyDetails(List<ProfileListItem> emergencyDetails) {
        this.emergencyDetails = emergencyDetails;
    }

    public List<ProfileListItem> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(List<ProfileListItem> familyDetails) {
        this.familyDetails = familyDetails;
    }

    public List<ProfileListItem> getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(List<ProfileListItem> eventDetails) {
        this.eventDetails = eventDetails;
    }

//    public List<ProfileListItem> getAddress() {
//        return addressDetails;
//    }
//
//    public void setAddress(List<ProfileListItem> addressDetails) {
//        this.addressDetails = addressDetails;
//    }

    public List<ProfileListItem> getOrgData() {
        return orgData;
    }

    public void setOrgData(List<ProfileListItem> orgData) {
        this.orgData = orgData;
    }

    List<ProfileListItem> orgData;


}
