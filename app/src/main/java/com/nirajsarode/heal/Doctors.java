package com.nirajsarode.heal;

public class Doctors {
    String description;
    String location;
    String name;
    String specialization;

    public Doctors(){

    }
    public Doctors(String description, String location, String name, String specialization) {
        this.description = description;
        this.location = location;
        this.name = name;
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
