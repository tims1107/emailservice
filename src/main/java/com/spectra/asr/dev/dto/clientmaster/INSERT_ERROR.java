package com.spectra.asr.dev.dto.clientmaster;

public class INSERT_ERROR {
    private String hlabnumber;
    private String servicelab;
    private String tablename;
    private String facilitynum;

    public INSERT_ERROR(String hlabnumber, String servicelab, String tablename, String facilitynum) {
        this.hlabnumber = hlabnumber;
        this.servicelab = servicelab;
        this.tablename = tablename;
        this.facilitynum = facilitynum;
    }

    public INSERT_ERROR() {
    }

    @Override
    public String toString() {
        return "INSERT_ERROR{" +
                "hlabnumber='" + hlabnumber + '\'' +
                ", servicelab='" + servicelab + '\'' +
                ", tablename='" + tablename + '\'' +
                ", facilitynum='" + facilitynum + '\'' +
                '}';
    }

    public String getHlabnumber() {
        return hlabnumber;
    }

    public void setHlabnumber(String hlabnumber) {
        this.hlabnumber = hlabnumber;
    }

    public String getServicelab() {
        return servicelab;
    }

    public void setServicelab(String servicelab) {
        this.servicelab = servicelab;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getFacilitynum() {
        return facilitynum;
    }

    public void setFacilitynum(String facilitynum) {
        this.facilitynum = facilitynum;
    }
}
