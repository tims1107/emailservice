package com.spectra.asr.enums;

public enum LegacyTableEnum {
    corporate_info ("A"),
    customer_num ("A"),
    facility ("E"),
    southfacility ("S"),
    westfacility ("W"),
    more_account_info ("E"),
    westmore_account_info ("W"),
    eastadd_info ("E"),
    southadd_info ("S"),
    westadd_info ("W"),
    eastalert_exception ("E"),
    southalert_exception ("S"),
    westalert_exception ("W"),
    eastemail ("E"),
    westemail ("W"),
    eastequipami_label ("E"),
    westequipami_label ("W"),
    eastequiphlab_report ("E"),
    westequiphlab_report ("W"),
    eastequipprint_manager ("E"),
    westequipprint_manager ("W"),
    Easthard_copy ("E"),
    southhard_copy ("S"),
    westhard_copy ("S"),
    eastpriority_list ("E"),
    southpriority_list ("S"),
    westpriority_list ("W"),
    eastspecial_instructions ("E"),
    southspecial_instructions ("S"),
    westspecial_instructions ("W"),
    eastafterhourscontact ("E"),
    westafterhourscontact ("W");

    private final String tableflag;

    LegacyTableEnum(String tableflag) {
        this.tableflag = tableflag;
    }

    public String getTableflag() {
        return tableflag;
    }
}
