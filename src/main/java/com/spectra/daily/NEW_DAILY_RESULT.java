package com.spectra.daily;

import java.util.Date;


public class NEW_DAILY_RESULT {

    private String ORDER_NUMBER;
    private String PATIENT_TYPE;
    private String LOINC_CODE;
    private String LOINC_NAME;
    private String ACCESSION_NUMBER;
    private String EXTERNAL_MRN;
    private Date RELEASE_DATE_TIME;
    private String ORDER_TEST_CODE;
    private String ORDER_TEST_NAME;
    private String RESULT_TEST_CODE;
    private String RESULT_TEST_NAME;
    private Date COLLECTION_DATE_TIME;
    private Date COLLECTION_DATE;
    private String COLLECTION_TIME;
    private String RESULT_STATUS;
    private String SOURCE_LAB_SYSTEM;
    private String DEVICE_ID;
    private String RESULT_COMMENTS;
    private String VALUE_TYPE;
    private float NUMERIC_RESULT;
    private String TEXTUAL_RESULT_FULL;
    private String TEXTUAL_RESULT;
    private String REFERENCE_RANGE;
    private String ABNORMAL_FLAG;
    private String PERFORMING_LAB_ID;
    private String ORDER_METHOD;
    private String SPECIMEN_SOURCE;
    private String ORDER_DETAIL_STATUS;
    private String UNITS;
    private String NPI;
    private String ORDERING_PHYSICIAN_NAME;
    private String LOGGING_SITE;
    private String PATIENT_ID;
    private String REQUISITION_STATUS;

    public NEW_DAILY_RESULT() {
    }

    public NEW_DAILY_RESULT(String ORDER_NUMBER, String PATIENT_TYPE, String LOINC_CODE, String LOINC_NAME, String ACCESSION_NUMBER, String EXTERNAL_MRN,
                            Date RELEASE_DATE_TIME, String ORDER_TEST_CODE, String ORDER_TEST_NAME, String RESULT_TEST_CODE, String RESULT_TEST_NAME, Date COLLECTION_DATE_TIME,
                            Date COLLECTION_DATE, String COLLECTION_TIME, String RESULT_STATUS, String SOURCE_LAB_SYSTEM, String DEVICE_ID, String RESULT_COMMENTS, String VALUE_TYPE,
                            float NUMERIC_RESULT, String TEXTUAL_RESULT_FULL, String TEXTUAL_RESULT, String REFERENCE_RANGE, String ABNORMAL_FLAG, String PERFORMING_LAB_ID,
                            String ORDER_METHOD, String SPECIMEN_SOURCE, String ORDER_DETAIL_STATUS, String UNITS, String NPI, String ORDERING_PHYSICIAN_NAME, String LOGGING_SITE,
                            String PATIENT_ID, String REQUISITION_STATUS) {
        this.ORDER_NUMBER = ORDER_NUMBER;
        this.PATIENT_TYPE = PATIENT_TYPE;
        this.LOINC_CODE = LOINC_CODE;
        this.LOINC_NAME = LOINC_NAME;
        this.ACCESSION_NUMBER = ACCESSION_NUMBER;
        this.EXTERNAL_MRN = EXTERNAL_MRN;
        this.RELEASE_DATE_TIME = RELEASE_DATE_TIME;
        this.ORDER_TEST_CODE = ORDER_TEST_CODE;
        this.ORDER_TEST_NAME = ORDER_TEST_NAME;
        this.RESULT_TEST_CODE = RESULT_TEST_CODE;
        this.RESULT_TEST_NAME = RESULT_TEST_NAME;
        this.COLLECTION_DATE_TIME = COLLECTION_DATE_TIME;
        this.COLLECTION_DATE = COLLECTION_DATE;
        this.COLLECTION_TIME = COLLECTION_TIME;
        this.RESULT_STATUS = RESULT_STATUS;
        this.SOURCE_LAB_SYSTEM = SOURCE_LAB_SYSTEM;
        this.DEVICE_ID = DEVICE_ID;
        this.RESULT_COMMENTS = RESULT_COMMENTS;
        this.VALUE_TYPE = VALUE_TYPE;
        this.NUMERIC_RESULT = NUMERIC_RESULT;
        this.TEXTUAL_RESULT_FULL = TEXTUAL_RESULT_FULL;
        this.TEXTUAL_RESULT = TEXTUAL_RESULT;
        this.REFERENCE_RANGE = REFERENCE_RANGE;
        this.ABNORMAL_FLAG = ABNORMAL_FLAG;
        this.PERFORMING_LAB_ID = PERFORMING_LAB_ID;
        this.ORDER_METHOD = ORDER_METHOD;
        this.SPECIMEN_SOURCE = SPECIMEN_SOURCE;
        this.ORDER_DETAIL_STATUS = ORDER_DETAIL_STATUS;
        this.UNITS = UNITS;
        this.NPI = NPI;
        this.ORDERING_PHYSICIAN_NAME = ORDERING_PHYSICIAN_NAME;
        this.LOGGING_SITE = LOGGING_SITE;
        this.PATIENT_ID = PATIENT_ID;
        this.REQUISITION_STATUS = REQUISITION_STATUS;
    }

    @Override
    public String toString() {
        return "NEW_DAILY_RESULT{" +
                "ORDER_NUMBER='" + ORDER_NUMBER + '\'' +
                ", PATIENT_TYPE='" + PATIENT_TYPE + '\'' +
                ", LOINC_CODE='" + LOINC_CODE + '\'' +
                ", LOINC_NAME='" + LOINC_NAME + '\'' +
                ", ACCESSION_NUMBER='" + ACCESSION_NUMBER + '\'' +
                ", EXTERNAL_MRN='" + EXTERNAL_MRN + '\'' +
                ", RELEASE_DATE_TIME=" + RELEASE_DATE_TIME +
                ", ORDER_TEST_CODE='" + ORDER_TEST_CODE + '\'' +
                ", ORDER_TEST_NAME='" + ORDER_TEST_NAME + '\'' +
                ", RESULT_TEST_CODE='" + RESULT_TEST_CODE + '\'' +
                ", RESULT_TEST_NAME=" + RESULT_TEST_NAME +
                ", COLLECTION_DATE_TIME=" + COLLECTION_DATE_TIME +
                ", COLLECTION_DATE=" + COLLECTION_DATE +
                ", COLLECTION_TIME='" + COLLECTION_TIME + '\'' +
                ", RESULT_STATUS='" + RESULT_STATUS + '\'' +
                ", SOURCE_LAB_SYSTEM='" + SOURCE_LAB_SYSTEM + '\'' +
                ", DEVICE_ID='" + DEVICE_ID + '\'' +
                ", RESULT_COMMENTS='" + RESULT_COMMENTS + '\'' +
                ", VALUE_TYPE='" + VALUE_TYPE + '\'' +
                ", NUMERIC_RESULT='" + NUMERIC_RESULT + '\'' +
                ", TEXTUAL_RESULT_FULL='" + TEXTUAL_RESULT_FULL + '\'' +
                ", TEXTUAL_RESULT='" + TEXTUAL_RESULT + '\'' +
                ", REFERENCE_RANGE='" + REFERENCE_RANGE + '\'' +
                ", ABNORMAL_FLAG='" + ABNORMAL_FLAG + '\'' +
                ", PERFORMING_LAB_ID='" + PERFORMING_LAB_ID + '\'' +
                ", ORDER_METHOD='" + ORDER_METHOD + '\'' +
                ", SPECIMEN_SOURCE='" + SPECIMEN_SOURCE + '\'' +
                ", ORDER_DETAIL_STATUS='" + ORDER_DETAIL_STATUS + '\'' +
                ", UNITS='" + UNITS + '\'' +
                ", NPI='" + NPI + '\'' +
                ", ORDERING_PHYSICIAN_NAME='" + ORDERING_PHYSICIAN_NAME + '\'' +
                ", LOGGING_SITE='" + LOGGING_SITE + '\'' +
                ", PATIENT_ID='" + PATIENT_ID + '\'' +
                ", REQUISITION_STATUS='" + REQUISITION_STATUS + '\'' +
                '}';
    }

    public String getORDER_NUMBER() {
        return ORDER_NUMBER;
    }

    public void setORDER_NUMBER(String ORDER_NUMBER) {
        this.ORDER_NUMBER = ORDER_NUMBER;
    }

    public String getPATIENT_TYPE() {
        return PATIENT_TYPE;
    }

    public void setPATIENT_TYPE(String PATIENT_TYPE) {
        this.PATIENT_TYPE = PATIENT_TYPE;
    }

    public String getLOINC_CODE() {
        return LOINC_CODE;
    }

    public void setLOINC_CODE(String LOINC_CODE) {
        this.LOINC_CODE = LOINC_CODE;
    }

    public String getLOINC_NAME() {
        return LOINC_NAME;
    }

    public void setLOINC_NAME(String LOINC_NAME) {
        this.LOINC_NAME = LOINC_NAME;
    }

    public String getACCESSION_NUMBER() {
        return ACCESSION_NUMBER;
    }

    public void setACCESSION_NUMBER(String ACCESSION_NUMBER) {
        this.ACCESSION_NUMBER = ACCESSION_NUMBER;
    }

    public String getEXTERNAL_MRN() {
        return EXTERNAL_MRN;
    }

    public void setEXTERNAL_MRN(String EXTERNAL_MRN) {
        this.EXTERNAL_MRN = EXTERNAL_MRN;
    }

    public Date getRELEASE_DATE_TIME() {
        return RELEASE_DATE_TIME;
    }

    public void setRELEASE_DATE_TIME(Date RELEASE_DATE_TIME) {
        this.RELEASE_DATE_TIME = RELEASE_DATE_TIME;
    }

    public String getORDER_TEST_CODE() {
        return ORDER_TEST_CODE;
    }

    public void setORDER_TEST_CODE(String ORDER_TEST_CODE) {
        this.ORDER_TEST_CODE = ORDER_TEST_CODE;
    }

    public String getORDER_TEST_NAME() {
        return ORDER_TEST_NAME;
    }

    public void setORDER_TEST_NAME(String ORDER_TEST_NAME) {
        this.ORDER_TEST_NAME = ORDER_TEST_NAME;
    }

    public String getRESULT_TEST_CODE() {
        return RESULT_TEST_CODE;
    }

    public void setRESULT_TEST_CODE(String RESULT_TEST_CODE) {
        this.RESULT_TEST_CODE = RESULT_TEST_CODE;
    }

    public String getRESULT_TEST_NAME() {
        return RESULT_TEST_NAME;
    }

    public void setRESULT_TEST_NAME(String RESULT_TEST_NAME) {
        this.RESULT_TEST_NAME = RESULT_TEST_NAME;
    }

    public Date getCOLLECTION_DATE_TIME() {
        return COLLECTION_DATE_TIME;
    }

    public void setCOLLECTION_DATE_TIME(Date COLLECTION_DATE_TIME) {
        this.COLLECTION_DATE_TIME = COLLECTION_DATE_TIME;
    }

    public Date getCOLLECTION_DATE() {
        return COLLECTION_DATE;
    }

    public void setCOLLECTION_DATE(Date COLLECTION_DATE) {
        this.COLLECTION_DATE = COLLECTION_DATE;
    }

    public String getCOLLECTION_TIME() {
        return COLLECTION_TIME;
    }

    public void setCOLLECTION_TIME(String COLLECTION_TIME) {
        this.COLLECTION_TIME = COLLECTION_TIME;
    }

    public String getRESULT_STATUS() {
        return RESULT_STATUS;
    }

    public void setRESULT_STATUS(String RESULT_STATUS) {
        this.RESULT_STATUS = RESULT_STATUS;
    }

    public String getSOURCE_LAB_SYSTEM() {
        return SOURCE_LAB_SYSTEM;
    }

    public void setSOURCE_LAB_SYSTEM(String SOURCE_LAB_SYSTEM) {
        this.SOURCE_LAB_SYSTEM = SOURCE_LAB_SYSTEM;
    }

    public String getDEVICE_ID() {
        return DEVICE_ID;
    }

    public void setDEVICE_ID(String DEVICE_ID) {
        this.DEVICE_ID = DEVICE_ID;
    }

    public String getRESULT_COMMENTS() {
        return RESULT_COMMENTS;
    }

    public void setRESULT_COMMENTS(String RESULT_COMMENTS) {
        this.RESULT_COMMENTS = RESULT_COMMENTS;
    }

    public String getVALUE_TYPE() {
        return VALUE_TYPE;
    }

    public void setVALUE_TYPE(String VALUE_TYPE) {
        this.VALUE_TYPE = VALUE_TYPE;
    }

    public float getNUMERIC_RESULT() {
        return NUMERIC_RESULT;
    }

    public void setNUMERIC_RESULT(float NUMERIC_RESULT) {
        this.NUMERIC_RESULT = NUMERIC_RESULT;
    }

    public String getTEXTUAL_RESULT_FULL() {
        return TEXTUAL_RESULT_FULL;
    }

    public void setTEXTUAL_RESULT_FULL(String TEXTUAL_RESULT_FULL) {
        this.TEXTUAL_RESULT_FULL = TEXTUAL_RESULT_FULL;
    }

    public String getTEXTUAL_RESULT() {
        return TEXTUAL_RESULT;
    }

    public void setTEXTUAL_RESULT(String TEXTUAL_RESULT) {
        this.TEXTUAL_RESULT = TEXTUAL_RESULT;
    }

    public String getREFERENCE_RANGE() {
        return REFERENCE_RANGE;
    }

    public void setREFERENCE_RANGE(String REFERENCE_RANGE) {
        this.REFERENCE_RANGE = REFERENCE_RANGE;
    }

    public String getABNORMAL_FLAG() {
        return ABNORMAL_FLAG;
    }

    public void setABNORMAL_FLAG(String ABNORMAL_FLAG) {
        this.ABNORMAL_FLAG = ABNORMAL_FLAG;
    }

    public String getPERFORMING_LAB_ID() {
        return PERFORMING_LAB_ID;
    }

    public void setPERFORMING_LAB_ID(String PERFORMING_LAB_ID) {
        this.PERFORMING_LAB_ID = PERFORMING_LAB_ID;
    }

    public String getORDER_METHOD() {
        return ORDER_METHOD;
    }

    public void setORDER_METHOD(String ORDER_METHOD) {
        this.ORDER_METHOD = ORDER_METHOD;
    }

    public String getSPECIMEN_SOURCE() {
        return SPECIMEN_SOURCE;
    }

    public void setSPECIMEN_SOURCE(String SPECIMEN_SOURCE) {
        this.SPECIMEN_SOURCE = SPECIMEN_SOURCE;
    }

    public String getORDER_DETAIL_STATUS() {
        return ORDER_DETAIL_STATUS;
    }

    public void setORDER_DETAIL_STATUS(String ORDER_DETAIL_STATUS) {
        this.ORDER_DETAIL_STATUS = ORDER_DETAIL_STATUS;
    }

    public String getUNITS() {
        return UNITS;
    }

    public void setUNITS(String UNITS) {
        this.UNITS = UNITS;
    }

    public String getNPI() {
        return NPI;
    }

    public void setNPI(String NPI) {
        this.NPI = NPI;
    }

    public String getORDERING_PHYSICIAN_NAME() {
        return ORDERING_PHYSICIAN_NAME;
    }

    public void setORDERING_PHYSICIAN_NAME(String ORDERING_PHYSICIAN_NAME) {
        this.ORDERING_PHYSICIAN_NAME = ORDERING_PHYSICIAN_NAME;
    }

    public String getLOGGING_SITE() {
        return LOGGING_SITE;
    }

    public void setLOGGING_SITE(String LOGGING_SITE) {
        this.LOGGING_SITE = LOGGING_SITE;
    }

    public String getPATIENT_ID() {
        return PATIENT_ID;
    }

    public void setPATIENT_ID(String PATIENT_ID) {
        this.PATIENT_ID = PATIENT_ID;
    }

    public String getREQUISITION_STATUS() {
        return REQUISITION_STATUS;
    }

    public void setREQUISITION_STATUS(String REQUISITION_STATUS) {
        this.REQUISITION_STATUS = REQUISITION_STATUS;
    }
}
