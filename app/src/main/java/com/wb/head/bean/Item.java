package com.wb.head.bean;

/**
 * Created by Administrator on 2016/3/24.
 */
public class Item {

    private String maintitle;
    private String subtitle;
    private boolean active;

    public Item(String maintitle, String subtitle, boolean active){
        this.maintitle = maintitle;
        this.subtitle = subtitle;
        this.active = active;
    }

    public Item(String maintitle, String subtitle){
        this.maintitle = maintitle;
        this.subtitle = subtitle;
    }

    public String getMaintitle() {
        return maintitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public boolean isActive(){
        return active;
    }

}
