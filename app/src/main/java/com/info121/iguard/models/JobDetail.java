package com.info121.iguard.models;

public class JobDetail {
    String id;
    String siteName;
    String address;
    String shift;
    String shiftDesc;
    String post;
    String postDesc;
    String date;
    String status;
    String officer;


    public JobDetail(String id, String siteName, String address,String shift, String shiftDesc, String post, String postDesc, String date, String status, String officer) {
        this.id = id;
        this.siteName = siteName;
        this.address = address;
        this.shift = shift;
        this.shiftDesc = shiftDesc;
        this.post = post;
        this.postDesc = postDesc;
        this.date = date;
        this.status = status;
        this.officer = officer;
    }

    public JobDetail(String id, String siteName, String shift, String post, String status, String officer) {
        this.id = id;
        this.siteName = siteName;
        this.shift = shift;
        this.post = post;
        this.status = status;
        this.officer = officer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getShiftDesc() {
        return shiftDesc;
    }

    public void setShiftDesc(String shiftDesc) {
        this.shiftDesc = shiftDesc;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
