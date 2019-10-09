package com.nirajsarode.heal;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    ArrayList<CartItem> cart;
    com.google.firebase.Timestamp createdAt;

    public Order(){

    }

    public Order(ArrayList<CartItem> cart, com.google.firebase.Timestamp createdAt) {
        this.cart = cart;
        this.createdAt = createdAt;
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    public com.google.firebase.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(com.google.firebase.Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
