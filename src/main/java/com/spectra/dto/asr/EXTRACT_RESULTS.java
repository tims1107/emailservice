package com.spectra.dto.asr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EXTRACT_RESULTS {
    private String ACCESSION_NUMBER;
    private String FACILITY_ID;
    private String CID;
    private String ETHNIC_GROUP;
    private String PATIENT_RACE;
    private String EXTERNAL_MRN;
    private String PATIENT_LAST_NAME;
    private String PATIENT_FIRST_NAME;
    private String PATIENT_MIDDLE_NAME;
    private Date DATE_OF_BIRTH;
    private String GENDER;
    private String PATIENT_SSN;
    private String NPI;
    private String ORDERING_PHYSICIAN_NAME;
    private String REPORT_NOTES;
    private String SPECIMEN_RECEIVE_DATE;
    private Date COLLECTION_DATE;
    private String COLLECTION_TIME;
    private Date COLLECTION_DATE_TIME;
    private String DRAW_FREQ;
    private String RES_RPRT_STATUS_CHNG_DT_TIME;
    private String ORDER_DETAIL_STATUS;
    private String ORDER_TEST_CODE;
    private String ORDER_TEST_NAME;
    private String RESULT_TEST_CODE;
    private String RESULT_TEST_NAME;
    private String RESULT_STATUS;
    private String TEXTUAL_RESULT;
    private String TEXTUAL_RESULT_FULL;
    private Double NUMERIC_RESULT;
    private String UNITS;
    private String REFERENCE_RANGE;
    private String ABNORMAL_FLAG;
    private String RELEASE_DATE_TIME;
    private String RESULT_COMMENTS;
    private String PERFORMING_LAB_ID;
    private String ORDER_METHOD;
    private String SPECIMEN_SOURCE;
    private String ORDER_NUMBER;
    private String LOGGING_SITE;
    private int AGE;
    private String FACILITY_NAME;
    private String COND_CODE;
    private String PATIENT_TYPE;
    private String SOURCE_OF_COMMENT;
    private String PATIENT_ID;
    private String ALTERNATE_PATIENT_ID;
    private String REQUISITION_STATUS;
    private String FACILITY_ADDRESS1;
    private String FACILITY_ADDRESS2;
    private String FACILITY_CITY;
    private String FACILITY_STATE;
    private String FACILITY_ZIP;
    private String FACILITY_PHONE;
    private String PATIENT_ACCOUNT_ADDRESS1;
    private String PATIENT_ACCOUNT_ADDRESS2;
    private String PATIENT_ACCOUNT_CITY;
    private String PATIENT_ACCOUNT_STATE;
    private String PATIENT_ACCOUNT_ZIP;
    private String PATIENT_HOME_PHONE;
    private String LOINC_CODE;
    private String LOINC_NAME;
    private String VALUE_TYPE;
    private String EAST_WEST_FLAG;
    private String INTERNAL_EXTERNAL_FLAG;
    private Timestamp LAST_UPDATE_TIME;
    private String SEQUENCE_NO;
    private String FACILITY_ACCOUNT_STATUS;
    private String FACILITY_ACTIVE_FLAG;
    private String MICRO_ISOLATE;
    private String MICRO_ORGANISM_NAME;
    private String LAB_FK;
    private String CLINICAL_MANAGER;
    private String MEDICAL_DIRECTOR;
    private String ACTI_FACILITY_ID;
    private String FMC_NUMBER;
    private String REPORTABLE_STATE;
    private String SOURCE_STATE;
    private String DEVICE_NAME;

}
