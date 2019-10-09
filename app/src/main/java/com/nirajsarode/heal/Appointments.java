package com.nirajsarode.heal;

public class Appointments {
    String date;
    String docid;
    String doctor;
    int time;
    String userid;
    String user;

    public Appointments(){

    }

    public Appointments(String date, String docid, String doctor, int time, String userid, String user) {
        this.date = date;
        this.docid = docid;
        this.doctor = doctor;
        this.time = time;
        this.userid = userid;
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
