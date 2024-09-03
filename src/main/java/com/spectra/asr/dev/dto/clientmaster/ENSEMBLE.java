package com.spectra.asr.dev.dto.clientmaster;

import java.sql.Date;

public class ENSEMBLE {

    private String code;
    private String facility_num;
    private String hlab_num;
    private String location;
    private String facility_name;
    private String country;
    private String fmc_number;
    private String int_ext_study;
    private String order_system;
    private String type_of_service;
    private String account_category;
    private String corporate_acronym;
    private String corporate_group_name;
    private String account_status;
    private String primary_account;
    private String alt_acct_num;
    private String spectra_cid;
    private String korus_cid;
    private String account_type;
    private Date korus_upload_date;
    private Date korus_live_date;
    private Date date_change;
    private String phone;
    private String phone_comments;

    public ENSEMBLE(String code, String facility_num, String hlab_num, String location, String facility_name, String country, String fmc_number, String int_ext_study,
                    String order_system, String type_of_service, String account_category, String corporate_acronym, String corporate_group_name, String account_status,
                    String primary_account, String alt_acct_num, String spectra_cid, String korus_cid, String account_type, Date korus_upload_date, Date korus_live_date,
                    Date date_change, String phone, String phone_comments)
    {
        this.code = code;
        this.facility_num = facility_num;
        this.hlab_num = hlab_num;
        this.location = location;
        this.facility_name = facility_name;
        this.country = country;
        this.fmc_number = fmc_number;
        this.int_ext_study = int_ext_study;
        this.order_system = order_system;
        this.type_of_service = type_of_service;
        this.account_category = account_category;
        this.corporate_acronym = corporate_acronym;
        this.corporate_group_name = corporate_group_name;
        this.account_status = account_status;
        this.primary_account = primary_account;
        this.alt_acct_num = alt_acct_num;
        this.spectra_cid = spectra_cid;
        this.korus_cid = korus_cid;
        this.account_type = account_type;
        this.korus_upload_date = korus_upload_date;
        this.korus_live_date = korus_live_date;
        this.date_change = date_change;
        this.phone = phone;
        this.phone_comments = phone_comments;
    }

    public ENSEMBLE() {
    }

    public String getMetaData() {
        return code + "|" +
                facility_num + "|" +
                hlab_num + "|" +
                location + "|" +
                facility_name + "|" +
                country + "|" +
                fmc_number + "|" +
                int_ext_study + "|" +
                order_system + "|" +
                type_of_service + "|" +
                account_category + "|" +
                corporate_acronym + "|" +
                corporate_group_name + "|" +
                account_status + "|" +
                primary_account + "|" +
                alt_acct_num + "|" +
                spectra_cid + "|" +
                korus_cid + "|" +
                account_type + "|" +
                korus_upload_date + "|" +
                korus_live_date + "|" +
                date_change + "|" +
                phone + "|" +
                phone_comments + "|";

    }

    public String getResultHeader(){
        return "code |" +
                "facility_num |" +
                "hlab_num |" +
                "location |" +
                "facility_name |" +
                "country |" +
                "fmc_number |" +
                "int_ext_study |" +
                "order_system |" +
                "type_of_service |" +
                "account_category |" +
                "corporate_acronym |" +
                "corporate_group_name |" +
                "account_status |" +
                "primary_account |" +
                "alt_acct_num |" +
                "spectra_cid |" +
                "korus_cid |" +
                "account_type |" +
                "korus_upload_date |" +
                "korus_live_date |" +
                "date_change |" +
                "phone |" +
                "phone_comments |";
    }

    @Override
    public String toString() {
        return "ENSEMBLE{" +
                "code='" + code + '\'' +
                ", facility_num='" + facility_num + '\'' +
                ", hlab_num='" + hlab_num + '\'' +
                ", location='" + location + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", country='" + country + '\'' +
                ", fmc_number='" + fmc_number + '\'' +
                ", int_ext_study='" + int_ext_study + '\'' +
                ", order_system='" + order_system + '\'' +
                ", type_of_service='" + type_of_service + '\'' +
                ", account_category='" + account_category + '\'' +
                ", corporate_acronym='" + corporate_acronym + '\'' +
                ", corporate_group_name='" + corporate_group_name + '\'' +
                ", account_status='" + account_status + '\'' +
                ", primary_account='" + primary_account + '\'' +
                ", alt_acct_num='" + alt_acct_num + '\'' +
                ", spectra_cid='" + spectra_cid + '\'' +
                ", korus_cid='" + korus_cid + '\'' +
                ", account_type='" + account_type + '\'' +
                ", korus_upload_date=" + korus_upload_date +
                ", korus_live_date=" + korus_live_date +
                ", date_change=" + date_change +
                ", phone='" + phone + '\'' +
                ", phone_comments='" + phone_comments + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(String facility_num) {
        this.facility_num = facility_num;
    }

    public String getHlab_num() {
        return hlab_num;
    }

    public void setHlab_num(String hlab_num) {
        this.hlab_num = hlab_num;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFmc_number() {
        return fmc_number;
    }

    public void setFmc_number(String fmc_number) {
        this.fmc_number = fmc_number;
    }

    public String getInt_ext_study() {
        return int_ext_study;
    }

    public void setInt_ext_study(String int_ext_study) {
        this.int_ext_study = int_ext_study;
    }

    public String getOrder_system() {
        return order_system;
    }

    public void setOrder_system(String order_system) {
        this.order_system = order_system;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public void setType_of_service(String type_of_service) {
        this.type_of_service = type_of_service;
    }

    public String getAccount_category() {
        return account_category;
    }

    public void setAccount_category(String account_category) {
        this.account_category = account_category;
    }

    public String getCorporate_acronym() {
        return corporate_acronym;
    }

    public void setCorporate_acronym(String corporate_acronym) {
        this.corporate_acronym = corporate_acronym;
    }

    public String getCorporate_group_name() {
        return corporate_group_name;
    }

    public void setCorporate_group_name(String corporate_group_name) {
        this.corporate_group_name = corporate_group_name;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getPrimary_account() {
        return primary_account;
    }

    public void setPrimary_account(String primary_account) {
        this.primary_account = primary_account;
    }

    public String getAlt_acct_num() {
        return alt_acct_num;
    }

    public void setAlt_acct_num(String alt_acct_num) {
        this.alt_acct_num = alt_acct_num;
    }

    public String getSpectra_cid() {
        return spectra_cid;
    }

    public void setSpectra_cid(String spectra_cid) {
        this.spectra_cid = spectra_cid;
    }

    public String getKorus_cid() {
        return korus_cid;
    }

    public void setKorus_cid(String korus_cid) {
        this.korus_cid = korus_cid;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Date getKorus_upload_date() {
        return korus_upload_date;
    }

    public void setKorus_upload_date(Date korus_upload_date) {
        this.korus_upload_date = korus_upload_date;
    }

    public Date getKorus_live_date() {
        return korus_live_date;
    }

    public void setKorus_live_date(Date korus_live_date) {
        this.korus_live_date = korus_live_date;
    }

    public Date getDate_change() {
        return date_change;
    }

    public void setDate_change(Date date_change) {
        this.date_change = date_change;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_comments() {
        return phone_comments;
    }

    public void setPhone_comments(String phone_comments) {
        this.phone_comments = phone_comments;
    }
}
