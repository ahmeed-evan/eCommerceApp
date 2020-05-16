package com.example.ecommerceappexample.Model;

import androidx.annotation.NonNull;

public class Product {
    private String itemTextView;
    private int itemImageView;

    public Product(String itemTextView, int itemImageView) {
        this.itemTextView = itemTextView;
        this.itemImageView = itemImageView;
    }

    public String getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(String itemTextView) {
        this.itemTextView = itemTextView;
    }

    public int getItemImageView() {
        return itemImageView;
    }

    public void setItemImageView(int itemImageView) {
        this.itemImageView = itemImageView;
    }

    @Override
    public String toString() {
        return "Product{" +
                "itemTextView='" + itemTextView + '\'' +
                ", itemImageView=" + itemImageView +
                '}';
    }
}
