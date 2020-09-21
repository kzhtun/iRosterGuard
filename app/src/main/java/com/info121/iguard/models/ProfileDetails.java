package com.info121.iguard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDetails {

    @SerializedName("HPNo")
    @Expose
    private String hPNo;
    @SerializedName("ThumbImage")
    @Expose
    private String thumbImage;
    @SerializedName("guardcode")
    @Expose
    private String guardcode;
    @SerializedName("guardname")
    @Expose
    private String guardname;
    @SerializedName("password")
    @Expose
    private String password;


    public String gethPNo() {
        return hPNo;
    }

    public void sethPNo(String hPNo) {
        this.hPNo = hPNo;
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getGuardcode() {
        return guardcode;
    }

    public void setGuardcode(String guardcode) {
        this.guardcode = guardcode;
    }

    public String getGuardname() {
        return guardname;
    }

    public void setGuardname(String guardname) {
        this.guardname = guardname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
