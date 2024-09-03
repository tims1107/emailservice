package com.spectra.asr.dev.dto.clientmaster;

public class FACILITY_TABLE {

    private String hlab_num;
    private String facility_num;
    private String facility_pref;
    private String facility_name;
    private String fmc_number;
    private String e_w_flag;
    private String int_ext_study;
    private String cid;
    private String tla;
    private String address1;
    private String address2;
    private String city;
    private String facility_state;
    private String zip;
    private String country;
    private String country_code;
    private String phone;
    private String fax;
    private String phone_comments;
    private String account_status;
    private String account_type;
    private String account_category;
    private String type_of_service;
    private String primary_account;
    private String primary_name;
    private int priority_call;
    private String order_system;
    private String ami_database;
    private String ami_market;
    private String patient_report;
    private int patient_count;
    private int hemo_count;
    private int pd_count;
    private int cour_account;
    private String courier_area;
    private String specimen_del;
    private String comments;
    private String cour_ship_num;
    private String korus_cid;
    private int hh_count;
    private String alt_acct_num;
    private int cour_service;
    private String eta;

    public FACILITY_TABLE(String hlab_num, String facility_num, String facility_pref, String facility_name, String fmc_number, String e_w_flag,
                          String int_ext_study, String cid, String tla, String address1, String address2, String city, String facility_state,
                          String zip, String country, String country_code, String phone, String fax, String phone_comments, String account_status,
                          String account_type, String account_category, String type_of_service, String primary_account, String primary_name,
                          int priority_call, String order_system, String ami_database, String ami_market, String patient_report, int patient_count,
                          int hemo_count, int pd_count, int cour_account, String courier_area, String specimen_del, String comments,
                          String cour_ship_num, String korus_cid, int hh_count, String alt_acct_num, int cour_service, String eta) {

        this.hlab_num = hlab_num;
        this.facility_num = facility_num;
        this.facility_pref = facility_pref;
        this.facility_name = facility_name;
        this.fmc_number = fmc_number;
        this.e_w_flag = e_w_flag;
        this.int_ext_study = int_ext_study;
        this.cid = cid;
        this.tla = tla;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.facility_state = facility_state;
        this.zip = zip;
        this.country = country;
        this.country_code = country_code;
        this.phone = phone;
        this.fax = fax;
        this.phone_comments = phone_comments;
        this.account_status = account_status;
        this.account_type = account_type;
        this.account_category = account_category;
        this.type_of_service = type_of_service;
        this.primary_account = primary_account;
        this.primary_name = primary_name;
        this.priority_call = priority_call;
        this.order_system = order_system;
        this.ami_database = ami_database;
        this.ami_market = ami_market;
        this.patient_report = patient_report;
        this.patient_count = patient_count;
        this.hemo_count = hemo_count;
        this.pd_count = pd_count;
        this.cour_account = cour_account;
        this.courier_area = courier_area;
        this.specimen_del = specimen_del;
        this.comments = comments;
        this.cour_ship_num = cour_ship_num;
        this.korus_cid = korus_cid;
        this.hh_count = hh_count;
        this.alt_acct_num = alt_acct_num;
        this.cour_service = cour_service;
        this.eta = eta;
    }

    public FACILITY_TABLE() {
    }

    @Override
    public String toString() {
        return "FACILITY_TABLE{" +
                "hlab_num='" + hlab_num + '\'' +
                ", facility_num='" + facility_num + '\'' +
                ", facility_pref='" + facility_pref + '\'' +
                ", facility_name='" + facility_name + '\'' +
                ", fmc_number='" + fmc_number + '\'' +
                ", e_w_flag='" + e_w_flag + '\'' +
                ", int_ext_study='" + int_ext_study + '\'' +
                ", cid='" + cid + '\'' +
                ", tla='" + tla + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", facility_state='" + facility_state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                ", country_code='" + country_code + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", phone_comments='" + phone_comments + '\'' +
                ", account_status='" + account_status + '\'' +
                ", account_type='" + account_type + '\'' +
                ", account_category='" + account_category + '\'' +
                ", type_of_service='" + type_of_service + '\'' +
                ", primary_account='" + primary_account + '\'' +
                ", primary_name='" + primary_name + '\'' +
                ", priority_call=" + priority_call +
                ", order_system='" + order_system + '\'' +
                ", ami_database='" + ami_database + '\'' +
                ", ami_market='" + ami_market + '\'' +
                ", patient_report='" + patient_report + '\'' +
                ", patient_count=" + patient_count +
                ", hemo_count=" + hemo_count +
                ", pd_count=" + pd_count +
                ", cour_account=" + cour_account +
                ", courier_area='" + courier_area + '\'' +
                ", specimen_del='" + specimen_del + '\'' +
                ", comments='" + comments + '\'' +
                ", cour_ship_num='" + cour_ship_num + '\'' +
                ", korus_cid='" + korus_cid + '\'' +
                ", hh_count=" + hh_count +
                ", alt_acct_num='" + alt_acct_num + '\'' +
                ", cour_service=" + cour_service +
                ", eta='" + eta + '\'' +
                '}';
    }

    public String getHlab_num() {
        return hlab_num;
    }

    public void setHlab_num(String hlab_num) {
        this.hlab_num = hlab_num;
    }

    public String getFacility_num() {
        return facility_num;
    }

    public void setFacility_num(String facility_num) {
        this.facility_num = facility_num;
    }

    public String getFacility_pref() {
        return facility_pref;
    }

    public void setFacility_pref(String facility_pref) {
        this.facility_pref = facility_pref;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public String getFmc_number() {
        return fmc_number;
    }

    public void setFmc_number(String fmc_number) {
        this.fmc_number = fmc_number;
    }

    public String getE_w_flag() {
        return e_w_flag;
    }

    public void setE_w_flag(String e_w_flag) {
        this.e_w_flag = e_w_flag;
    }

    public String getInt_ext_study() {
        return int_ext_study;
    }

    public void setInt_ext_study(String int_ext_study) {
        this.int_ext_study = int_ext_study;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTla() {
        return tla;
    }

    public void setTla(String tla) {
        this.tla = tla;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPhone_comments() {
        return phone_comments;
    }

    public void setPhone_comments(String phone_comments) {
        this.phone_comments = phone_comments;
    }

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_category() {
        return account_category;
    }

    public void setAccount_category(String account_category) {
        this.account_category = account_category;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public void setType_of_service(String type_of_service) {
        this.type_of_service = type_of_service;
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

    public int getPriority_call() {
        return priority_call;
    }

    public void setPriority_call(int priority_call) {
        this.priority_call = priority_call;
    }

    public String getOrder_system() {
        return order_system;
    }

    public void setOrder_system(String order_system) {
        this.order_system = order_system;
    }

    public String getAmi_database() {
        return ami_database;
    }

    public void setAmi_database(String ami_database) {
        this.ami_database = ami_database;
    }

    public String getAmi_market() {
        return ami_market;
    }

    public void setAmi_market(String ami_market) {
        this.ami_market = ami_market;
    }

    public String getPatient_report() {
        return patient_report;
    }

    public void setPatient_report(String patient_report) {
        this.patient_report = patient_report;
    }

    public int getPatient_count() {
        return patient_count;
    }

    public void setPatient_count(int patient_count) {
        this.patient_count = patient_count;
    }

    public int getHemo_count() {
        return hemo_count;
    }

    public void setHemo_count(int hemo_count) {
        this.hemo_count = hemo_count;
    }

    public int getPd_count() {
        return pd_count;
    }

    public void setPd_count(int pd_count) {
        this.pd_count = pd_count;
    }

    public int getCour_account() {
        return cour_account;
    }

    public void setCour_account(int cour_account) {
        this.cour_account = cour_account;
    }

    public String getCourier_area() {
        return courier_area;
    }

    public void setCourier_area(String courier_area) {
        this.courier_area = courier_area;
    }

    public String getSpecimen_del() {
        return specimen_del;
    }

    public void setSpecimen_del(String specimen_del) {
        this.specimen_del = specimen_del;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCour_ship_num() {
        return cour_ship_num;
    }

    public void setCour_ship_num(String cour_ship_num) {
        this.cour_ship_num = cour_ship_num;
    }

    public String getKorus_cid() {
        return korus_cid;
    }

    public void setKorus_cid(String korus_cid) {
        this.korus_cid = korus_cid;
    }

    public int getHh_count() {
        return hh_count;
    }

    public void setHh_count(int hh_count) {
        this.hh_count = hh_count;
    }

    public String getAlt_acct_num() {
        return alt_acct_num;
    }

    public void setAlt_acct_num(String alt_acct_num) {
        this.alt_acct_num = alt_acct_num;
    }

    public int getCour_service() {
        return cour_service;
    }

    public void setCour_service(int cour_service) {
        this.cour_service = cour_service;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }
}
