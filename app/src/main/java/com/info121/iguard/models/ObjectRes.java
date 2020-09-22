package com.info121.iguard.models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ObjectRes {
	@SerializedName("IsUnderMaintenance")
	@Expose
	private String isUnderMaintenance;

	@SerializedName("ProfileDetails")
	@Expose
	private List<ProfileDetails> profileDetails;

	@SerializedName("SiteDetails")
	@Expose
	private List<SiteDetail> siteDetails = null;

	@SerializedName("Version")
	@Expose
	private String version;
	@SerializedName("lastlogin")
	@Expose
	private String lastlogin;
	@SerializedName("responsemessage")
	@Expose
	private String responsemessage;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("token")
	@Expose
	private String token;

	public List<SiteDetail> getSiteDetails() {
		return siteDetails;
	}

	public void setSiteDetails(List<SiteDetail> siteDetails) {
		this.siteDetails = siteDetails;
	}

	public List<ProfileDetails> getProfileDetails() {
		return profileDetails;
	}

	public void setProfileDetails(List<ProfileDetails> profileDetails) {
		this.profileDetails = profileDetails;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	public String getResponsemessage() {
		return responsemessage;
	}

	public void setResponsemessage(String responsemessage) {
		this.responsemessage = responsemessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

    public String getIsUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setIsUnderMaintenance(String isUnderMaintenance) {
        this.isUnderMaintenance = isUnderMaintenance;
    }
}
