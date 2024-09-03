package com.spectra.asr.utils.statements;

public class PrepStatement {

    public static String MORE_ACCOUNT_INFO_EAST =
            "select FACILITY_NUM,ALT_PHONE1,ALT_PHONE2,ALT_PHONE3,ALT_COMMENTS1,ALT_COMMENTS2,ALT_COMMENTS3,OPEN_TIMEMWF,OPEN_TIMETTHS, " +
                    " OPEN_TIMESAT,CLOSE_TIMEMWF,CLOSE_TIMETTHS,CLOSE_TIMESAT,LAST_SHIFTMWF,LAST_SHIFTTTHS,LAST_SHIFTSAT,START_SUPPLY_DATE, " +
                    " START_COMMENTS,MED_DIR,CLINIC_MGR,ADMIN,DON,BUS_UNIT,REGION,AREA,OPENSUN,CLOSESUN,PICKUPMWF,PICKUPTTH,PICKUPSAT,HOURS_COMMENTS,HLAB_NUM " +
                    "from " +
                    " more_account_info ";

    public static String MORE_ACCOUNT_INFO_WEST =
            "select FACILITY_NUM,ALT_PHONE1,ALT_PHONE2,ALT_PHONE3,ALT_COMMENTS1,ALT_COMMENTS2,ALT_COMMENTS3,OPEN_TIMEMWF,OPEN_TIMETTHS, " +
                    " OPEN_TIMESAT,CLOSE_TIMEMWF,CLOSE_TIMETTHS,CLOSE_TIMESAT,LAST_SHIFTMWF,LAST_SHIFTTTHS,LAST_SHIFTSAT,START_SUPPLY_DATE, " +
                    " START_COMMENTS,MED_DIR,CLINIC_MGR,ADMIN,DON,BUS_UNIT,REGION,AREA,OPENSUN,CLOSESUN,PICKUPMWF,PICKUPTTH,PICKUPSAT,HOURS_COMMENTS,HLAB_NUM " +
                    "from " +
                    " westmore_account_info ";

    public static final String GET_CLINIC_WITH_HLABNUM = "select hlabnumber,regexp_substr(value,'(-)(.)',1,1,null,2) servicelab from clinic c " +
            "   join clinic_detail cd ON cd.clinic_id = id " +
            "   join clinic_servicelab sl ON sl.servicelabid = cd.servicelabid " +
            "   where hlabnumber = ? " +
            "   and servicelabid = ?";

    public static final String GET_CLINIC_WITH_LAB = "select * from " +
            " (select hlabnumber,regexp_substr(value,'(-)(.)',1,1,null,2) servicelab,accountnumber from clinic c " +
            "   join clinic_detail cd ON cd.clinic_id = id " +
            "   join clinic_servicelab sl ON sl.servicelabid = cd.servicelabid ) t1 " +
            "   where hlabnumber = ? " +
            "   and accountnumber = ?" +
            "   and servicelab = ?";

    public static final String GET_CLINIC_WITH_HLABNUMLAB = "select * from " +
            " (select id,hlabnumber,accountnumber,regexp_substr(value,'(-)(.)',1,1,null,2) servicelab from clinic c " +
            "   join clinic_detail cd ON cd.clinic_id = id " +
            "   join clinic_servicelab sl ON sl.servicelabid = cd.servicelabid ) t1 " +
            "   where hlabnumber = ? " +
            "   and accountnumber = ?" +
            "   and servicelab IN (?,?)";

//            +
//            "   and regexp_substr(value,'(-)(.)',1,1,null,2) = ?";

}
