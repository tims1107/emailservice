package com.spectra.dto.clientmaster;

public class LEGACY_FACILITY
{

    private String location;
    private String hlab_num;
    private String primary_account;
    private String primary_name;
    private String facilityname;
    private String typeofservice;
    private String status;
    private String corpacronym;
    private String corpacronymname;


    public LEGACY_FACILITY() {
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getTypeofservice() {
        return typeofservice;
    }

    public void setTypeofservice(String typeofservice) {
        this.typeofservice = typeofservice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorpacronym() {
        return corpacronym;
    }

    public void setCorpacronym(String corpacronym) {
        this.corpacronym = corpacronym;
    }

    public String getCorpacronymname() {
        return corpacronymname;
    }

    public void setCorpacronymname(String corpacronymname) {
        this.corpacronymname = corpacronymname;
    }

    public String getPrimary_account() {
        return primary_account;
    }

    public void setPrimary_account(String primary_account) {
        this.primary_account = primary_account;
    }

    public String getPrimary_name() {
        return primary_name;
    }

    public void setPrimary_name(String primary_name) {
        this.primary_name = primary_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHlab_num() {
        return hlab_num;
    }

    public void setHlab_num(String hlab_num) {
        this.hlab_num = hlab_num;
    }
}
