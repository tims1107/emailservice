package com.spectra.dto.audit;

import com.opencsv.bean.CsvBindByPosition;

public class KorusRecord {

    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String CORPORATION_NAME;

    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String CORPORATION_ACRONYM;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String FACILITY_NAME;

    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String ACCOUNT_NAME;

    @CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String ACCOUNT_TYPE;

    @CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "facilitynum",required = true)
    private String FMC_NUMBER;

    @CsvBindByPosition(position = 6)
    private String PRIMARY_ACCOUNT_NUMBER;

    @CsvBindByPosition(position = 7)
    private String HLAB_NUM;


    public KorusRecord() {
    }

    public KorusRecord(String CORPORATION_NAME, String CORPORATION_ACRONYM, String FACILITY_NAME, String ACCOUNT_NAME, String ACCOUNT_TYPE, String FMC_NUMBER, String PRIMARY_ACCOUNT_NUMBER, String HLAB_NUM) {
        this.CORPORATION_NAME = CORPORATION_NAME;
        this.CORPORATION_ACRONYM = CORPORATION_ACRONYM;
        this.FACILITY_NAME = FACILITY_NAME;
        this.ACCOUNT_NAME = ACCOUNT_NAME;
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
        this.FMC_NUMBER = FMC_NUMBER;
        this.PRIMARY_ACCOUNT_NUMBER = PRIMARY_ACCOUNT_NUMBER;
        this.HLAB_NUM = HLAB_NUM;
    }

    @Override
    public String toString() {
        return "KorusRecord{" +
                "CORPORATION_NAME='" + CORPORATION_NAME + '\'' +
                ", CORPORATION_ACRONYM='" + CORPORATION_ACRONYM + '\'' +
                ", FACILITY_NAME='" + FACILITY_NAME + '\'' +
                ", ACCOUNT_NAME='" + ACCOUNT_NAME + '\'' +
                ", ACCOUNT_TYPE='" + ACCOUNT_TYPE + '\'' +
                ", FMC_NUMBER='" + FMC_NUMBER + '\'' +
                ", PRIMARY_ACCOUNT_NUMBER='" + PRIMARY_ACCOUNT_NUMBER + '\'' +
                ", HLAB_NUM='" + HLAB_NUM + '\'' +
                '}';
    }

    public String getPRIMARY_ACCOUNT_NUMBER() {
        return PRIMARY_ACCOUNT_NUMBER;
    }

    public void setPRIMARY_ACCOUNT_NUMBER(String PRIMARY_ACCOUNT_NUMBER) {
        this.PRIMARY_ACCOUNT_NUMBER = PRIMARY_ACCOUNT_NUMBER;
    }

    public String getHLAB_NUM() {
        return HLAB_NUM;
    }

    public void setHLAB_NUM(String HLAB_NUM) {
        this.HLAB_NUM = HLAB_NUM;
    }

    public String getCORPORATION_NAME() {
        return CORPORATION_NAME;
    }

    public void setCORPORATION_NAME(String CORPORATION_NAME) {
        this.CORPORATION_NAME = CORPORATION_NAME;
    }

    public String getCORPORATION_ACRONYM() {
        return CORPORATION_ACRONYM;
    }

    public void setCORPORATION_ACRONYM(String CORPORATION_ACRONYM) {
        this.CORPORATION_ACRONYM = CORPORATION_ACRONYM;
    }

    public String getFACILITY_NAME() {
        return FACILITY_NAME;
    }

    public void setFACILITY_NAME(String FACILITY_NAME) {
        this.FACILITY_NAME = FACILITY_NAME;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String ACCOUNT_NAME) {
        this.ACCOUNT_NAME = ACCOUNT_NAME;
    }

    public String getACCOUNT_TYPE() {
        return ACCOUNT_TYPE;
    }

    public void setACCOUNT_TYPE(String ACCOUNT_TYPE) {
        this.ACCOUNT_TYPE = ACCOUNT_TYPE;
    }

    public String getFMC_NUMBER() {
        return FMC_NUMBER;
    }

    public void setFMC_NUMBER(String FMC_NUMBER) {
        this.FMC_NUMBER = FMC_NUMBER;
    }
}
