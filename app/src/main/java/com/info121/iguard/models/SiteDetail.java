package com.info121.iguard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SiteDetail {
    @SerializedName("JobDetails")
    @Expose
    private List<JobDetail> jobDetails = null;
    @SerializedName("sitename")
    @Expose
    private String sitename;


    public List<JobDetail> getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(List<JobDetail> jobDetails) {
        this.jobDetails = jobDetails;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
}
