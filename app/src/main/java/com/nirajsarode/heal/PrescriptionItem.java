package com.nirajsarode.heal;

public class PrescriptionItem {

    String description;
    String medicine;
    int quantity;

    public PrescriptionItem(){

    }

    public PrescriptionItem(String description, String medicine, int quantity) {
        this.description = description;
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
