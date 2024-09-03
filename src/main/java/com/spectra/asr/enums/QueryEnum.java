package com.spectra.asr.enums;

public enum QueryEnum {
    CLINIC_ORDER_SYSTEM (3)
    ,CLINIC_SERVICELAB (3)
    ,CLINIC_STATUS (3)
    ,CLINIC_ACCOUNT_CATEGORY (3)
    ,CLINIC_AFFILIATION (3)
    ,CLINIC_MODALITY_TYPE (3)
    ,CLINIC_COMMENT_TYPE (3)
    ,CLINIC_ECUBE_SERVER (3)
    ,CLINIC_FMCRELATIONSHIP (3)
    ,CLINIC_KIT_TYPE (3)
    ,CLINIC_ORDER_METHOD (3)
    ,CLINIC_SALES_REP (4)
    ,CLINIC_SUPPORT (4)
    ,CLINIC_TYPE_OF_SERVICE (3)
    ,CUSTOM_ALERT_LOOKUP (3)
    ,CUSTOM_EXCEPTION_LOOKUP (3)
    ,DAYS (1)
    ,DRAW_WEEK (3)
    ,HOURS_TYPE (1)
    ,FREQUENCY_TYPE (3)
    ,US_ZIP_TZ (4)
    ,STATE_LOOKUP (3)
    ,PRIORITYLIST_TYPE (3);

    private final int resulttype;

    QueryEnum(int resulttype) {
        this.resulttype = resulttype;
    }

    public int getResulttype() {
        return resulttype;
    }
    
}
