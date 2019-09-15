package com.amuyu.melitos.ui.vender;

public class VentasItem {
    private int imgResId;
    private String title;
    private int price;

    public VentasItem(int imgResId, String title, int price) {
        this.imgResId = imgResId;
        this.title = title;
        this.price = price;
    }

    public int getImgResId() {
        return imgResId;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
