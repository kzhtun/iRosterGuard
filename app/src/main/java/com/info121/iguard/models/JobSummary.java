package com.info121.iguard.models;

public class JobSummary {
    String sector;
    String count;

    public JobSummary(String sector, String count) {
        this.sector = sector;
        this.count = count;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
