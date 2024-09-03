package com.spectra.daily;

import java.util.Date;

public class PATRESULT {

    private String REQUISITION_ID;
    private String EID;
    private String COUNTY;
    private String FACILITY_ID;
    private String CID;
    private String PATIENT_LAST_NAME;
    private String PATIENT_FIRST_NAME;
    private String PATIENT_MIDDLE_NAME;
    private Date DATE_OF_BIRTH;
    private String GENDER;
    private String PATIENT_SSN;
    private Long AGE;
    private String PATIENT_ACCOUNT_ADDRESS1;
    private String PATIENT_ACCOUNT_ADDRESS2;
    private String PATIENT_ACCOUNT_CITY;
    private String PATIENT_ACCOUNT_STATE;
    private String PATIENT_ACCOUNT_ZIP;
    private String PATIENT_HOME_PHONE;
    private String FACILITY_ADDRESS1;
    private String FACILITY_ADDRESS2;
    private String FACILITY_CITY;
    private String FACILITY_STATE;
    private String FACILITY_ZIP;
    private String FACILITY_PHONE;
    private String EAST_WEST_FLAG;
    private String INTERNAL_EXTERNAL_FLAG;
    private String FACILITY_ACCOUNT_STATUS;
    private String FACILITY_ACTIVE_FLAG;
    private String CLINICAL_MANAGER;
    private String MEDICAL_DIRECTOR;
    private String ACTI_FACILITY_ID;
    private String FMC_NUMBER;
    private String PATIENT_RACE;
    private String ETHNIC_GROUP;
    private String FACILITY_NAME;

    public PATRESULT(String REQUISITION_ID, String EID, String COUNTY, String FACILITY_ID, String CID, String PATIENT_LAST_NAME, String PATIENT_FIRST_NAME,
                     String PATIENT_MIDDLE_NAME, Date DATE_OF_BIRTH, String GENDER, String PATIENT_SSN, Long AGE, String PATIENT_ACCOUNT_ADDRESS1,
                     String PATIENT_ACCOUNT_ADDRESS2, String PATIENT_ACCOUNT_CITY, String PATIENT_ACCOUNT_STATE, String PATIENT_ACCOUNT_ZIP, String PATIENT_HOME_PHONE,
                     String FACILITY_ADDRESS1, String FACILITY_ADDRESS2, String FACILITY_CITY, String FACILITY_STATE, String FACILITY_ZIP, String FACILITY_PHONE,
                     String EAST_WEST_FLAG, String INTERNAL_EXTERNAL_FLAG, String FACILITY_ACCOUNT_STATUS, String FACILITY_ACTIVE_FLAG, String CLINICAL_MANAGER,
                     String MEDICAL_DIRECTOR, String ACTI_FACILITY_ID, String FMC_NUMBER, String PATIENT_RACE, String ETHNIC_GROUP, String FACILITY_NAME) {
        this.REQUISITION_ID = REQUISITION_ID;
        this.EID = EID;
        this.COUNTY = COUNTY;
        this.FACILITY_ID = FACILITY_ID;
        this.CID = CID;
        this.PATIENT_LAST_NAME = PATIENT_LAST_NAME;
        this.PATIENT_FIRST_NAME = PATIENT_FIRST_NAME;
        this.PATIENT_MIDDLE_NAME = PATIENT_MIDDLE_NAME;
        this.DATE_OF_BIRTH = DATE_OF_BIRTH;
        this.GENDER = GENDER;
        this.PATIENT_SSN = PATIENT_SSN;
        this.AGE = AGE;
        this.PATIENT_ACCOUNT_ADDRESS1 = PATIENT_ACCOUNT_ADDRESS1;
        this.PATIENT_ACCOUNT_ADDRESS2 = PATIENT_ACCOUNT_ADDRESS2;
        this.PATIENT_ACCOUNT_CITY = PATIENT_ACCOUNT_CITY;
        this.PATIENT_ACCOUNT_STATE = PATIENT_ACCOUNT_STATE;
        this.PATIENT_ACCOUNT_ZIP = PATIENT_ACCOUNT_ZIP;
        this.PATIENT_HOME_PHONE = PATIENT_HOME_PHONE;
        this.FACILITY_ADDRESS1 = FACILITY_ADDRESS1;
        this.FACILITY_ADDRESS2 = FACILITY_ADDRESS2;
        this.FACILITY_CITY = FACILITY_CITY;
        this.FACILITY_STATE = FACILITY_STATE;
        this.FACILITY_ZIP = FACILITY_ZIP;
        this.FACILITY_PHONE = FACILITY_PHONE;
        this.EAST_WEST_FLAG = EAST_WEST_FLAG;
        this.INTERNAL_EXTERNAL_FLAG = INTERNAL_EXTERNAL_FLAG;
        this.FACILITY_ACCOUNT_STATUS = FACILITY_ACCOUNT_STATUS;
        this.FACILITY_ACTIVE_FLAG = FACILITY_ACTIVE_FLAG;
        this.CLINICAL_MANAGER = CLINICAL_MANAGER;
        this.MEDICAL_DIRECTOR = MEDICAL_DIRECTOR;
        this.ACTI_FACILITY_ID = ACTI_FACILITY_ID;
        this.FMC_NUMBER = FMC_NUMBER;
        this.PATIENT_RACE = PATIENT_RACE;
        this.ETHNIC_GROUP = ETHNIC_GROUP;
        this.FACILITY_NAME = FACILITY_NAME;
    }

    public String getFACILITY_NAME() {
        return FACILITY_NAME;
    }

    public void setFACILITY_NAME(String FACILITY_NAME) {
        this.FACILITY_NAME = FACILITY_NAME;
    }

    @Override
    public String toString() {
        return "PATRESULT{" +
                "REQUISITION_ID='" + REQUISITION_ID + '\'' +
                ", EID='" + EID + '\'' +
                ", COUNTY='" + COUNTY + '\'' +
                ", FACILITY_ID='" + FACILITY_ID + '\'' +
                ", CID='" + CID + '\'' +
                ", PATIENT_LAST_NAME='" + PATIENT_LAST_NAME + '\'' +
                ", PATIENT_FIRST_NAME='" + PATIENT_FIRST_NAME + '\'' +
                ", PATIENT_MIDDLE_NAME='" + PATIENT_MIDDLE_NAME + '\'' +
                ", DATE_OF_BIRTH=" + DATE_OF_BIRTH +
                ", GENDER='" + GENDER + '\'' +
                ", PATIENT_SSN='" + PATIENT_SSN + '\'' +
                ", AGE=" + AGE +
                ", PATIENT_ACCOUNT_ADDRESS1='" + PATIENT_ACCOUNT_ADDRESS1 + '\'' +
                ", PATIENT_ACCOUNT_ADDRESS2='" + PATIENT_ACCOUNT_ADDRESS2 + '\'' +
                ", PATIENT_ACCOUNT_CITY='" + PATIENT_ACCOUNT_CITY + '\'' +
                ", PATIENT_ACCOUNT_STATE='" + PATIENT_ACCOUNT_STATE + '\'' +
                ", PATIENT_ACCOUNT_ZIP='" + PATIENT_ACCOUNT_ZIP + '\'' +
                ", PATIENT_HOME_PHONE='" + PATIENT_HOME_PHONE + '\'' +
                ", FACILITY_ADDRESS1='" + FACILITY_ADDRESS1 + '\'' +
                ", FACILITY_ADDRESS2='" + FACILITY_ADDRESS2 + '\'' +
                ", FACILITY_CITY='" + FACILITY_CITY + '\'' +
                ", FACILITY_STATE='" + FACILITY_STATE + '\'' +
                ", FACILITY_ZIP='" + FACILITY_ZIP + '\'' +
                ", FACILITY_PHONE='" + FACILITY_PHONE + '\'' +
                ", EAST_WEST_FLAG='" + EAST_WEST_FLAG + '\'' +
                ", INTERNAL_EXTERNAL_FLAG='" + INTERNAL_EXTERNAL_FLAG + '\'' +
                ", FACILITY_ACCOUNT_STATUS='" + FACILITY_ACCOUNT_STATUS + '\'' +
                ", FACILITY_ACTIVE_FLAG='" + FACILITY_ACTIVE_FLAG + '\'' +
                ", CLINICAL_MANAGER='" + CLINICAL_MANAGER + '\'' +
                ", MEDICAL_DIRECTOR='" + MEDICAL_DIRECTOR + '\'' +
                ", ACTI_FACILITY_ID='" + ACTI_FACILITY_ID + '\'' +
                ", FMC_NUMBER='" + FMC_NUMBER + '\'' +
                ", PATIENT_RACE='" + PATIENT_RACE + '\'' +
                ", ETHNIC_GROUP='" + ETHNIC_GROUP + '\'' +
                '}';
    }

    public String getREQUISITION_ID() {
        return REQUISITION_ID;
    }

    public String getEID() {
        return EID;
    }

    public String getCOUNTY() {
        return COUNTY;
    }

    public String getFACILITY_ID() {
        return FACILITY_ID;
    }

    public String getCID() {
        return CID;
    }

    public String getPATIENT_LAST_NAME() {
        return PATIENT_LAST_NAME;
    }

    public String getPATIENT_FIRST_NAME() {
        return PATIENT_FIRST_NAME;
    }

    public String getPATIENT_MIDDLE_NAME() {
        return PATIENT_MIDDLE_NAME;
    }

    public Date getDATE_OF_BIRTH() {
        if(DATE_OF_BIRTH == null) return new Date();

        return DATE_OF_BIRTH;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getPATIENT_SSN() {
        return PATIENT_SSN;
    }

    public Long getAGE() {
        return AGE;
    }

    public String getPATIENT_ACCOUNT_ADDRESS1() {
        return PATIENT_ACCOUNT_ADDRESS1;
    }

    public String getPATIENT_ACCOUNT_ADDRESS2() {
        return PATIENT_ACCOUNT_ADDRESS2;
    }

    public String getPATIENT_ACCOUNT_CITY() {
        return PATIENT_ACCOUNT_CITY;
    }

    public String getPATIENT_ACCOUNT_STATE() {
        return PATIENT_ACCOUNT_STATE;
    }

    public String getPATIENT_ACCOUNT_ZIP() {
        return PATIENT_ACCOUNT_ZIP;
    }

    public String getPATIENT_HOME_PHONE() {
        return PATIENT_HOME_PHONE;
    }

    public String getFACILITY_ADDRESS1() {
        return FACILITY_ADDRESS1;
    }

    public String getFACILITY_ADDRESS2() {
        return FACILITY_ADDRESS2;
    }

    public String getFACILITY_CITY() {
        return FACILITY_CITY;
    }

    public String getFACILITY_STATE() {
        return FACILITY_STATE;
    }

    public String getFACILITY_ZIP() {
        return FACILITY_ZIP;
    }

    public String getFACILITY_PHONE() {
        return FACILITY_PHONE;
    }

    public String getEAST_WEST_FLAG() {
        return EAST_WEST_FLAG;
    }

    public String getINTERNAL_EXTERNAL_FLAG() {
        return INTERNAL_EXTERNAL_FLAG;
    }

    public String getFACILITY_ACCOUNT_STATUS() {
        return FACILITY_ACCOUNT_STATUS;
    }

    public String getFACILITY_ACTIVE_FLAG() {
        return FACILITY_ACTIVE_FLAG;
    }

    public String getCLINICAL_MANAGER() {
        return CLINICAL_MANAGER;
    }

    public String getMEDICAL_DIRECTOR() {
        return MEDICAL_DIRECTOR;
    }

    public String getACTI_FACILITY_ID() {
        return ACTI_FACILITY_ID;
    }

    public String getFMC_NUMBER() {
        return FMC_NUMBER;
    }

    public String getPATIENT_RACE() {
        return PATIENT_RACE;
    }

    public String getETHNIC_GROUP() {
        return ETHNIC_GROUP;
    }
}
