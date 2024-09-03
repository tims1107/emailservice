package com.spectra.asr.enums.tables;

public enum M_CLINIC_MIGRATE {

    TABLEFLAG(1),
    HLAB_NUM(2),
    FACILITY_NUM(3),
    FACILITY_PREF(4),
    FACILITY_NAME(5),
    FMC_NUMBER(6),
    E_W_FLAG(7),
    INT_EXT_STUDY(8),
    CID(9),
    TLA(10),
    ADDRESS1(11),
    ADDRESS2(12),
    CITY(13),
    FACILITY_STATE(14),
    ZIP(15),
    COUNTRY(16),
    COUNTRY_CODE(17),
    PHONE(18),
    FAX(19),
    PHONE_COMMENTS(20),
    ACCOUNT_STATUS(21),
    ACCOUNT_TYPE(22),
    ACCOUNT_CATEGORY(23),
    TYPE_OF_SERVICE(24),
    PRIMARY_ACCOUNT(25),
    PRIMARY_NAME(26),
    PRIORITY_CALL(27),
    ORDER_SYSTEM(28),
    AMI_DATABASE(29),
    AMI_MARKET(30),
    PATIENT_REPORT(31),
    PATIENT_COUNT(32),
    HEMO_COUNT(33),
    PD_COUNT(34),
    COUR_ACCOUNT(35),
    COURIER_AREA(36),
    SPECIMEN_DEL(37),
    COMMENTS(38),
    COUR_SHIP_NUM(39),
    KORUS_CID(40),
    HH_COUNT(41),
    ALT_ACCT_NUM(42),
    COUR_SERVICE(43),
    ETA(44);

    private final int resultpos;

    M_CLINIC_MIGRATE(int resultpos) {
        this.resultpos = resultpos;
    }

    public int getResultpos() {
        return resultpos;
    }
}
