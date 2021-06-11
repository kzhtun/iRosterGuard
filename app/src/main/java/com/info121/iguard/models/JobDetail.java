package com.info121.iguard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetail {


    @SerializedName("ConfirmationHours")
    @Expose
    private String confirmationHours;

    @SerializedName("RequireAcknowledgement")
    @Expose
    private String requireAcknowledgement;

    @SerializedName("RequireConfirmation")
    @Expose
    private String requireConfirmation;

    @SerializedName("GuardGrade")
    @Expose
    private String guardGrade;
    @SerializedName("GuardGradeDesc")
    @Expose
    private String guardGradeDesc;
    @SerializedName("JobCode")
    @Expose
    private String jobCode;
    @SerializedName("SiteShift")
    @Expose
    private String siteShift;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("attendancecode")
    @Expose
    private String attendancecode;
    @SerializedName("attendancename")
    @Expose
    private String attendancename;
    @SerializedName("cluster")
    @Expose
    private String cluster;
    @SerializedName("endtime")
    @Expose
    private String endtime;
    @SerializedName("globalshift")
    @Expose
    private String globalshift;
    @SerializedName("jobdate")
    @Expose
    private String jobdate;
    @SerializedName("jobno")
    @Expose
    private String jobno;
    @SerializedName("sitecode")
    @Expose
    private String sitecode;
    @SerializedName("sitename")
    @Expose
    private String sitename;
    @SerializedName("starttime")
    @Expose
    private String starttime;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("checked")
    @Expose
    private Boolean checked = false;

    public String getConfirmationHours() {
        return confirmationHours;
    }

    public void setConfirmationHours(String confirmationHours) {
        this.confirmationHours = confirmationHours;
    }

    public String getRequireAcknowledgement() {
        return requireAcknowledgement;
    }

    public void setRequireAcknowledgement(String requireAcknowledgement) {
        this.requireAcknowledgement = requireAcknowledgement;
    }

    public String getRequireConfirmation() {
        return requireConfirmation;
    }

    public void setRequireConfirmation(String requireConfirmation) {
        this.requireConfirmation = requireConfirmation;
    }

    public String getGuardGrade() {
        return guardGrade;
    }

    public void setGuardGrade(String guardGrade) {
        this.guardGrade = guardGrade;
    }

    public String getGuardGradeDesc() {
        return guardGradeDesc;
    }

    public void setGuardGradeDesc(String guardGradeDesc) {
        this.guardGradeDesc = guardGradeDesc;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getSiteShift() {
        return siteShift;
    }

    public void setSiteShift(String siteShift) {
        this.siteShift = siteShift;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttendancecode() {
        return attendancecode;
    }

    public void setAttendancecode(String attendancecode) {
        this.attendancecode = attendancecode;
    }

    public String getAttendancename() {
        return attendancename;
    }

    public void setAttendancename(String attendancename) {
        this.attendancename = attendancename;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getGlobalshift() {
        return globalshift;
    }

    public void setGlobalshift(String globalshift) {
        this.globalshift = globalshift;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getJobno() {
        return jobno;
    }

    public void setJobno(String jobno) {
        this.jobno = jobno;
    }

    public String getSitecode() {
        return sitecode;
    }

    public void setSitecode(String sitecode) {
        this.sitecode = sitecode;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
