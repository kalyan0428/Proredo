package com.sport.playsqorr.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WinInfo {

    @SerializedName("r1")
    @Expose
    private Integer r1;
    @SerializedName("r2")
    @Expose
    private Integer r2;
    @SerializedName("r3")
    @Expose
    private Integer r3;
    @SerializedName("c1")
    @Expose
    private Integer c1;
    @SerializedName("c2")
    @Expose
    private Integer c2;
    @SerializedName("c3")
    @Expose
    private Integer c3;
    @SerializedName("rC1")
    @Expose
    private Integer rC1;
    @SerializedName("rC2")
    @Expose
    private Integer rC2;

    public Integer getR1() {
        return r1;
    }

    public void setR1(Integer r1) {
        this.r1 = r1;
    }

    public Integer getR2() {
        return r2;
    }

    public void setR2(Integer r2) {
        this.r2 = r2;
    }

    public Integer getR3() {
        return r3;
    }

    public void setR3(Integer r3) {
        this.r3 = r3;
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }

    public Integer getC2() {
        return c2;
    }

    public void setC2(Integer c2) {
        this.c2 = c2;
    }

    public Integer getC3() {
        return c3;
    }

    public void setC3(Integer c3) {
        this.c3 = c3;
    }

    public Integer getrC1() {
        return rC1;
    }

    public void setrC1(Integer rC1) {
        this.rC1 = rC1;
    }

    public Integer getrC2() {
        return rC2;
    }

    public void setrC2(Integer rC2) {
        this.rC2 = rC2;
    }

}