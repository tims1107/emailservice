package com.spectra.asr.dev.dto.asr;

import java.sql.Timestamp;

public class RESULTS {

//    --RESULTS_PK	NUMBER
//    --LAB_ORDER_FK	NUMBER
//    LAB_ORDER_DETAILS_FK	NUMBER
//    LAB_FK	NUMBER
//    RESULT_TEST_FK	NUMBER
//    --REQUISITION_ID	VARCHAR2(50 BYTE)
//    ACCESSION_NUMBER	VARCHAR2(30 BYTE)
//    COLLECTION_DATE_TIME	DATE
//    COLLECTION_DATE	DATE
//    COLLECTION_TIME	VARCHAR2(10 BYTE)
//    RELEASE_DATE_TIME	DATE
//    ORDER_TEST_CODE	VARCHAR2(50 BYTE)
//    ORDER_TEST_NAME	VARCHAR2(100 BYTE)
//    RESULT_TEST_CODE	VARCHAR2(50 BYTE)
//    RESULT_TEST_NAME	VARCHAR2(100 BYTE)
//    RESULT_SEQUENCE	NUMBER
//    PERFORMING_LAB	VARCHAR2(50 BYTE)
//    ABNORMAL_FLAG	VARCHAR2(20 BYTE)
//    RESULT_STATUS	VARCHAR2(10 BYTE)
//    REFERENCE_RANGE	VARCHAR2(25 BYTE)
//    RESULT_COMMENT	CLOB
//    MODALITY_CODE	VARCHAR2(50 BYTE)
//    TEXTUAL_RESULT_FULL	VARCHAR2(4000 BYTE)
//    TEXTUAL_RESULT	VARCHAR2(4000 BYTE)
//    NUMERIC_RESULT	NUMBER
//    CONVERTED_RESULT	NUMBER
//    UNIT_OF_MEASURE	VARCHAR2(20 BYTE)
//    MICRO_ISOLATE	VARCHAR2(100 BYTE)
//    MICRO_ORGANISM_NAME	VARCHAR2(4000 BYTE)
//    MICRO_SENSITIVITY_NAME	VARCHAR2(4000 BYTE)
//    MICRO_SENSITIVITY_FLAG	CHAR(1 BYTE)
//    VALUE_TYPE	VARCHAR2(10 BYTE)
//    LOINC_CODE	VARCHAR2(100 BYTE)
//    LOINC_NAME	VARCHAR2(500 BYTE)
//    FINAL_RESULT_LOADED_DATE	DATE
//    MCI	NUMBER
//    SESSION_ID	NUMBER
//    BODY_ID	NUMBER
//    MESSAGE_DATE_TIME	NUMBER
//    CREATED_DATE	TIMESTAMP(6)
//    CREATED_BY	VARCHAR2(50 BYTE)
//    --LAST_UPDATED_DATE	TIMESTAMP(6)
//    LAST_UPDATED_BY	VARCHAR2(50 BYTE)
//    TEST_CATEGORY	VARCHAR2(25 BYTE)
//    SOURCE_LAB_SYSTEM	VARCHAR2(25 BYTE)
//    OBSERVATION_METHOD	VARCHAR2(50 BYTE)
//    DERIVED_ABNORMAL_FLAG	VARCHAR2(10 BYTE)
//    RESPONSIBLE_OBSERVER_ID	VARCHAR2(100 BYTE)
//    POSITIVE_CULTURE_FLAG	VARCHAR2(1 BYTE)
//    SENSITIVITY_PERFORMED_FLAG	VARCHAR2(2 BYTE)

    private long results_pk;
    private long lab_order_fk;
    private String requisition_id;
    private Timestamp last_updated_date;
    private String test_category;

    public RESULTS() {
    }

    public long getResults_pk() {
        return results_pk;
    }

    public void setResults_pk(long results_pk) {
        this.results_pk = results_pk;
    }

    public long getLab_order_fk() {
        return lab_order_fk;
    }

    public void setLab_order_fk(long lab_order_fk) {
        this.lab_order_fk = lab_order_fk;
    }

    public String getRequisition_id() {
        return requisition_id;
    }

    public void setRequisition_id(String requisition_id) {
        this.requisition_id = requisition_id;
    }

    public Timestamp getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(Timestamp last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public String getTest_category() {
        return test_category;
    }

    public void setTest_category(String test_category) {
        this.test_category = test_category;
    }
}
