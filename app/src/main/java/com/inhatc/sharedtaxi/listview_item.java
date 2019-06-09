package com.inhatc.sharedtaxi;

import android.graphics.drawable.Drawable;
public class listview_item {

    private String room_num;//name
    private String where;//birth
    private String gender;
    private String type;
    private String id;
    private Drawable icon;
    private String title;
    private String time;

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getGender(){return gender;}

    public void setGender(String gender){this.gender=gender;}

    public String getType(){return type;}

    public void setType(String type){this.type=type;}

    public String getid(){return id;}

    public void setid(String id){this.id=id;}

    public Drawable getIcon(){return icon;}

    public void setIcon(Drawable icon){this.icon=icon;}

    public String getTitle(){return title;}

    public void setTitle(String title){this.title=title;}

    public String getTime(){return time;}

    public void setTime(String time){this.time=time;}
}
