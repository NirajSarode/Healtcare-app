package com.nirajsarode.heal;

public class CartItem {
    String medicines;
    int quantity;
    String medicalid;
    int price;

    public CartItem() {

    }

    public CartItem(String medicines, int quantity, String medicalid, int price) {
        this.medicines = medicines;
        this.quantity = quantity;
        this.medicalid = medicalid;
        this.price = price;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedicalid() {
        return medicalid;
    }

    public void setMedicalid(String medicalid) {
        this.medicalid = medicalid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
