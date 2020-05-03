package com.info121.iroster.models;

public class JobDetail {
    String id;
    String siteName;
    String shift;
    String post;
    String status;
    String officer;

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
}
