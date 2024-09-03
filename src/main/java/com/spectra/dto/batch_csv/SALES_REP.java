package com.spectra.dto.batch_csv;

import com.opencsv.bean.CsvBindByPosition;

public class SALES_REP {

    //facilitynum|zone|facilityname|city|state|category|modality|hlabnum|salesrep

    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String facilitynum;
    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "corporate_acronym",required = true)
    private String zone;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "facility_name",required = true)
    private String facilityname;

    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "address1",required = true)
    private String city;

    @CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "city")
    private String state;

    @CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "facility_state")
    private String category;



    @CsvBindByPosition(position = 6)
    //@CsvBindByName(column = "phone",required = true)
    private String modality;

    @CsvBindByPosition(position = 7)
    //@CsvBindByName(column = "area_mgr",required = true)
    private String hlabnum;

    @CsvBindByPosition(position = 8)
    //@CsvBindByName(column = "regional_mgr",required = true)
    private String salesrep;

    public SALES_REP(String facilitynum, String zone, String facilityname, String city, String state, String category, String modality, String hlabnum, String salesrep) {
        this.facilitynum = facilitynum;
        this.zone = zone;
        this.facilityname = facilityname;
        this.city = city;
        this.state = state;
        this.category = category;
        this.modality = modality;
        this.hlabnum = hlabnum;
        this.salesrep = salesrep;
    }

    public SALES_REP() {
    }

    public String getFacilitynum() {
        return facilitynum;
    }

    public void setFacilitynum(String facilitynum) {
        this.facilitynum = facilitynum;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getHlabnum() {
        return hlabnum;
    }

    public void setHlabnum(String hlabnum) {
        this.hlabnum = hlabnum;
    }

    public String getSalesrep() {
        return salesrep;
    }

    public void setSalesrep(String salesrep) {
        this.salesrep = salesrep;
    }

    public int getSalesrepId(){

        switch (salesrep) {
            case "Brian Blackburn":
                return 16;
            case "Norma Maurer":
                return 9;
            default:
                return 10;

        }


    }

    @Override
    public String toString() {
        return "SALES_REP{" +
                "facilitynum='" + facilitynum + '\'' +
                ", zone='" + zone + '\'' +
                ", facilityname='" + facilityname + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", category='" + category + '\'' +
                ", modality='" + modality + '\'' +
                ", hlabnum='" + hlabnum + '\'' +
                ", salesrepid='" + getSalesrepId() + '\'' +
                ", salesrep='" + getSalesrep() + '\'' +
                '}';
    }
}
