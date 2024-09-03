package com.spectra.asr.dev.dto.clientmaster;

public class COHORT {
    private final int clinicid;
    private final String hlabnum;
    private final String facilitynum;
    private final String annual;
    private final String cohort;
    private final String startdate;
    private final String enddate;
    private final String zone;

    public COHORT(int clinicid, String hlabnum, String facilitynum, String annual, String cohort, String startdate, String enddate,String zone) {
        this.clinicid = clinicid;
        this.hlabnum = hlabnum;
        this.facilitynum = facilitynum;
        this.annual = annual;
        this.cohort = cohort;
        this.startdate = startdate;
        this.enddate = enddate;
        this.zone = zone;
    }

    public String getZone() {
        return zone;
    }

    public int getClinicid() {
        return clinicid;
    }

    public String getHlabnum() {
        return hlabnum;
    }

    public String getFacilitynum() {
        return facilitynum;
    }

    public String getAnnual() {
        return annual;
    }

    public String getCohort() {
        return cohort;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }
}
