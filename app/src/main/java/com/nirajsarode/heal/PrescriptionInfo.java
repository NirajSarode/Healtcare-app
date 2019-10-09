package com.nirajsarode.heal;

public class PrescriptionInfo {
    String doctor;
    String date;
    String description;
    String location;

    public PrescriptionInfo(){

    }

    public PrescriptionInfo(String doctor, String date, String description, String location) {
        this.doctor = doctor;
        this.date = date;
        this.description = description;
        this.location = location;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
