package com.example.chirag.tennis;

public class Evento {

    String type;
    String date1;
    String btime;
    String etime;

    public Evento(String date1,String type, String btime, String etime) {
        this.type = type;
        this.date1 = date1;
        this.btime = btime;
        this.etime = etime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
