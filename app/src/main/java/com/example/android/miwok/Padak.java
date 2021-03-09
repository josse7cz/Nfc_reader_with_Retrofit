package com.example.android.miwok;


import com.google.gson.annotations.SerializedName;

public class Padak {


    private Integer evC;
    private String typ;


    public Padak(Integer evC, String typ) {
        this.evC = evC;
        this.typ = typ;

    }

    public Integer getEvC() {
        return evC;
    }

    public String getTyp() {
        return typ;
    }
}
