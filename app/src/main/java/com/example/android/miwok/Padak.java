package com.example.android.miwok;


public class Padak {


    private Integer evC;
    private String typ;


    public Padak(Integer evC, String typ) {
        this.evC = evC;
        this.typ = typ;
    }
    public Padak(Integer evC) {
        this.evC = evC;

    }

    public Integer getEvC() {
        return evC;
    }

    public String getTyp() {
        return typ;
    }
}
