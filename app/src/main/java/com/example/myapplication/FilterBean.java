package com.example.myapplication;

public class FilterBean {
    private String value = "";
    private boolean bCHECK = false;
    public void setName(String name){
        this.value = name;
    }

    public String getName(){
        return this.value;
    }

    public void setSelected(boolean bCHECK){
        this.bCHECK = bCHECK;
    }

    public boolean isSelected(){
        return this.bCHECK;
    }

}
