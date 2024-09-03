package com.spectra.asr.dev.dto.clientmaster;

public class Facility {

    private final String tableflag;
    private final String hlab_num;
    private final String facility_num;
    private final String facility_pref;
    private final String facility_name;
    private final String fmc_number;
    private final String e_w_flag;
    private final String int_ext_study;
    private final String cid;
    private final String tla;
    private final String address1;
    private final String address2;
    private final String city;
    private final String facility_state;
    private final String zip;
    private final String country;
    private final String country_code;
    private final String phone;
    private final String fax;
    private final String phone_comments;
    private final String account_status;
    private final String account_type;
    private final String account_category;
    private final String type_of_service;
    private final String primary_account;
    private final String primary_name;
    private final int priority_call;
    private final String order_system;
    private final String ami_database;
    private final String ami_market;
    private final String patient_report;
    private final int patient_count;
    private final int hemo_count;
    private int pd_count;
    private final int cour_account;
    private final String courier_area;
    private final String specimen_del;
    private final String comments;
    private final String cour_ship_num;
    private final String korus_cid;
    private final int hh_count;
    private final String alt_acct_num;
    private final int cour_service;
    private final String ETA;
    private final String corporateacronym;
    private final String corporategroupname;
    private String customernum;

    public Facility(String tableflag, String hlab_num, String facility_num, String facility_pref, String facility_name, String fmc_number, String e_w_flag, String int_ext_study,
                    String cid, String tla, String address1, String address2, String city, String facility_state, String zip, String country, String country_code, String phone,
                    String fax, String phone_comments, String account_status, String account_type, String account_category, String type_of_service, String primary_account,
                    String primary_name, int priority_call, String order_system, String ami_database, String ami_market, String patient_report, int patient_count, int hemo_count,
                    int pd_count, int cour_account, String courier_area, String specimen_del, String comments, String cour_ship_num, String korus_cid, int hh_count,
                    String alt_acct_num, int cour_service, String ETA,String corporateacronym,String corporategroupname) {

        this.tableflag = tableflag;
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
        this.ETA = ETA;
        this.corporateacronym = corporateacronym;
        this.corporategroupname = corporategroupname;

    }

    public String getCustomernum() {
        return customernum;
    }

    public void setCustomernum(String customernum) {
        this.customernum = customernum;
    }

    public int getServicelabId(){
       switch (e_w_flag) {
            case "E":
              return 1;

           case "S":
               return 3;

           case "W":
               return 2;

           default:
               return 0;
        }
    }

    public String getCorporateacronym() {
        return corporateacronym;
    }

    public String getCorporategroupname() {
        return corporategroupname;
    }

    public String getTableflag() {
        return tableflag;
    }

    public String getHlab_num() {
        return hlab_num;
    }

    public String getFacility_num() {
        return facility_num;
    }

    public String getFacility_pref() {
        return facility_pref;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public String getFmc_number() {
        return fmc_number;
    }

    public String getE_w_flag() {
        return e_w_flag;
    }

    public String getInt_ext_study() {
        return int_ext_study;
    }

    public String getCid() {
        return cid;
    }

    public String getTla() {
        return tla;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getFacility_state() {
        return facility_state;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getPhone_comments() {
        return phone_comments;
    }

    public String getAccount_status() {
        return account_status;
    }

    public String getAccount_type() {
        return account_type;
    }

    public String getAccount_category() {
        return account_category;
    }

    public String getType_of_service() {
        return type_of_service;
    }

    public String getPrimary_account() {
        return primary_account;
    }

    public String getPrimary_name() {
        return primary_name;
    }

    public int getPriority_call() {
        return priority_call;
    }

    public String getOrder_system() {
        return order_system;
    }

    public String getAmi_database() {
        return ami_database;
    }

    public String getAmi_market() {
        return ami_market;
    }

    public String getPatient_report() {
        return patient_report;
    }

    public int getPatient_count() {
        return patient_count;
    }

    public int getHemo_count() {
        return hemo_count;
    }

    public int getPd_count() {
        return pd_count;
    }

    public int getCour_account() {
        return cour_account;
    }

    public String getCourier_area() {
        return courier_area;
    }

    public String getSpecimen_del() {
        return specimen_del;
    }

    public String getComments() {
        return comments;
    }

    public String getCour_ship_num() {
        return cour_ship_num;
    }

    public String getKorus_cid() {
        return korus_cid;
    }

    public int getHh_count() {
        return hh_count;
    }

    public String getAlt_acct_num() {
        return alt_acct_num;
    }

    public int getCour_service() {
        return cour_service;
    }

    public String getETA() {
        return ETA;
    }
}
