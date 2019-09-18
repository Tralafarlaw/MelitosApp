package com.amuyu.melitos.ui.vender;

import com.amuyu.melitos.C;

import java.util.Map;

public class VentasItem {
    private String imgResId;
    private String title;
    private int price;

    public VentasItem (Map<String, Object> data){
        this.imgResId = (String) data.get(C.cPURi);
        this.title = (String) data.get(C.cPName);
        this.price = (Integer) data.get(C.cPPrice);
    }

    public String getImgResId() {
        return imgResId;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
