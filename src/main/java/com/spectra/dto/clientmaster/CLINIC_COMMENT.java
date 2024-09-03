package com.spectra.dto.clientmaster;

import com.opencsv.bean.CsvBindByPosition;

public class CLINIC_COMMENT {
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
    //@CsvBindByName(column = "facility_num",required = true)
    private String facility_num;
    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "corporate_acronym",required = true)
    private String corporate_acronym;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "facility_name",required = true)
    private String facility_name;

    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "address1",required = true)
    private String address1;

    @CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "city")
    private String city;

    @CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "facility_state")
    private String facility_state;



    @CsvBindByPosition(position = 6)
    //@CsvBindByName(column = "phone",required = true)
    private String phone;

    @CsvBindByPosition(position = 7)
    //@CsvBindByName(column = "area_mgr",required = true)
    private String area_mgr;

    @CsvBindByPosition(position = 8)
    //@CsvBindByName(column = "regional_mgr",required = true)
    private String regional_mgr;

    @CsvBindByPosition(position = 9)
    //@CsvBindByName(column = "comment",required = true)
    private String comment;

    public CLINIC_COMMENT(String facility_num, String corporate_acronym, String facility_name, String address1, String city, String facility_state, String phone,
                          String area_mgr, String regional_mgr, String comment) {
        this.facility_num = facility_num;
        this.corporate_acronym = corporate_acronym;
        this.facility_name = facility_name;
        this.address1 = address1;
        this.facility_state = facility_state;
        this.city = city;
        this.phone = phone;
        this.area_mgr = area_mgr;
        this.regional_mgr = regional_mgr;
        this.comment = comment;
    }

    public CLINIC_COMMENT() {
    }

    public String getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(String facility_num) {
        this.facility_num = facility_num;
    }

    public String getCorporate_acronym() {
        return corporate_acronym;
    }

    public void setCorporate_acronym(String corporate_acronym) {
        this.corporate_acronym = corporate_acronym;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFacility_state() {
        return facility_state;
    }

    public void setFacility_state(String facility_state) {
        this.facility_state = facility_state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea_mgr() {
        return area_mgr;
    }

    public void setArea_mgr(String area_mgr) {
        this.area_mgr = area_mgr;
    }

    public String getRegional_mgr() {
        return regional_mgr;
    }

    public void setRegional_mgr(String regional_mgr) {
        this.regional_mgr = regional_mgr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CLINIC_COMMENT{" +
                "facility_num='" + facility_num + '\'' +
                ", corporate_acronym='" + corporate_acronym + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", address1='" + address1 + '\'' +
                ", facility_state='" + facility_state + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", area_mgr='" + area_mgr + '\'' +
                ", regional_mgr='" + regional_mgr + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
