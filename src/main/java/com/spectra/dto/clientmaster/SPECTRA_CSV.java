package com.spectra.dto.clientmaster;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class SPECTRA_CSV {
//    DL ID,
//    DL Name,
//    Status,
//    Status Date,
//    FMS Clinical Manager,
//    Medical Director - In-Center,
//   Area ID,a
//    area Name,
//    Region ID,
//    Region
//    Name,Division ID,
//    Division Name,
//    OG Name

    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "DL ID",required = true)
    private String fmc_number;

    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "DL Name",required = true)
    private String facility_name;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "Status",required = true)
    private String status;

    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "Status Date",required = true)
    private String statusdate;

    @CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "FMS Clinical Manager")
    private String clinicalmgr;

    @CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "Medical Director - In-Center")
    private String meddir;

    @CsvBindByPosition(position = 6)
    //@CsvBindByName(column = "Area ID",required = true)
    private String areaid;

    @CsvBindByPosition(position = 7)
    //@CsvBindByName(column = "Area Name",required = true)
    private String areaname;

    @CsvBindByPosition(position = 8)
    //@CsvBindByName(column = "Region ID",required = true)
    private String regionid;

    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "Region Name",required = true)
    private String region;

    @CsvBindByPosition(position = 10)
    //@CsvBindByName(column = "Division ID",required = true)
    private String divisionid;

    @CsvBindByPosition(position = 11)
    //@CsvBindByName(column = "Division Name",required = true)
    private String divisionname;

    @CsvBindByPosition(position = 12)
    //@CsvBindByName(column = "OG Name",required = true)
    private String ogname;

    public SPECTRA_CSV(String fmc_number, String facility_name, String status, String statusdate, String clinicalmgr, String meddir, String areaid,
                       String areaname, String regionid, String region, String divisionid, String divisionname, String ogname) {
        this.fmc_number = fmc_number;
        this.facility_name = facility_name;
        this.status = status;
        this.statusdate = statusdate;
        this.clinicalmgr = clinicalmgr;
        this.meddir = meddir;
        this.areaid = areaid;
        this.areaname = areaname;
        this.regionid = regionid;
        this.region = region;
        this.divisionid = divisionid;
        this.divisionname = divisionname;
        this.ogname = ogname;
    }

    public SPECTRA_CSV() {
    }

    @Override
    public String toString() {
        return "SPECTRA_CSV{" +
                "fmc_number='" + fmc_number + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", status='" + status + '\'' +
                ", statusdate='" + statusdate + '\'' +
                ", clinicalmgr='" + clinicalmgr + '\'' +
                ", meddir='" + meddir + '\'' +
                ", areaid='" + areaid + '\'' +
                ", areaname='" + areaname + '\'' +
                ", regionid='" + regionid + '\'' +
                ", region='" + region + '\'' +
                ", divisionid='" + divisionid + '\'' +
                ", divisionname='" + divisionname + '\'' +
                ", ogname='" + ogname + '\'' +
                '}';
    }

    public String getFmc_number() {
        return fmc_number;
    }

    public void setFmc_number(String fmc_number) {
        this.fmc_number = fmc_number;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(String statusdate) {
        this.statusdate = statusdate;
    }

    public String getClinicalmgr() {
        return clinicalmgr;
    }

    public void setClinicalmgr(String clinicalmgr) {
        this.clinicalmgr = clinicalmgr;
    }

    public String getMeddir() {
        return meddir;
    }

    public void setMeddir(String meddir) {
        this.meddir = meddir;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getRegionid() {
        return regionid;
    }

    public void setRegionid(String regionid) {
        this.regionid = regionid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDivisionid() {
        return divisionid;
    }

    public void setDivisionid(String divisionid) {
        this.divisionid = divisionid;
    }

    public String getDivisionname() {
        return divisionname;
    }

    public void setDivisionname(String divisionname) {
        this.divisionname = divisionname;
    }

    public String getOgname() {
        return ogname;
    }

    public void setOgname(String ogname) {
        this.ogname = ogname;
    }
}
