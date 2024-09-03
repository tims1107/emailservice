package com.spectra.dto.clientmaster;

import java.sql.Date;

public class MOREINFO_TABLE {

    private String FACILITY_NUM;
    private String ALT_PHONE1;
    private String ALT_PHONE2;
    private String ALT_PHONE3;
    private String ALT_COMMENTS1;
    private String ALT_COMMENTS2;
    private String ALT_COMMENTS3;
    private Date OPEN_TIMEMWF;
    private Date OPEN_TIMETTHS;
    private Date OPEN_TIMESAT;
    private Date CLOSE_TIMEMWF;
    private Date CLOSE_TIMETTHS;
    private Date CLOSE_TIMESAT;
    private Date LAST_SHIFTMWF;
    private Date LAST_SHIFTTTHS;
    private Date LAST_SHIFTSAT;
    private Date START_SUPPLY_DATE;
    private String START_COMMENTS;
    private String MED_DIR;
    private String CLINIC_MGR;
    private String ADMIN;
    private String DON;
    private String BUS_UNIT;
    private String REGION;
    private String AREA;
    private Date OPENSUN;
    private Date CLOSESUN;
    private Date PICKUPMWF;
    private Date PICKUPTTH;
    private Date PICKUPSAT;
    private String HOURS_COMMENTS;
    private String HLAB_NUM;
    private String SERVICELAB;
    private String TABLENAME;



    public MOREINFO_TABLE() {
    }

    public MOREINFO_TABLE(String FACILITY_NUM, String ALT_PHONE1, String ALT_PHONE2, String ALT_PHONE3, String ALT_COMMENTS1, String ALT_COMMENTS2, String ALT_COMMENTS3,
                          Date OPEN_TIMEMWF, Date OPEN_TIMETTHS, Date OPEN_TIMESAT, Date CLOSE_TIMEMWF, Date CLOSE_TIMETTHS, Date CLOSE_TIMESAT, Date LAST_SHIFTMWF,
                          Date LAST_SHIFTTTHS, Date LAST_SHIFTSAT, Date START_SUPPLY_DATE, String START_COMMENTS, String MED_DIR, String CLINIC_MGR, String ADMIN,
                          String DON, String BUS_UNIT, String REGION, String AREA, Date OPENSUN, Date CLOSESUN, Date PICKUPMWF, Date PICKUPTTH, Date PICKUPSAT,
                          String HOURS_COMMENTS, String HLAB_NUM, String SERVICELAB,String TABLENAME) {
        this.FACILITY_NUM = FACILITY_NUM;
        this.ALT_PHONE1 = ALT_PHONE1;
        this.ALT_PHONE2 = ALT_PHONE2;
        this.ALT_PHONE3 = ALT_PHONE3;
        this.ALT_COMMENTS1 = ALT_COMMENTS1;
        this.ALT_COMMENTS2 = ALT_COMMENTS2;
        this.ALT_COMMENTS3 = ALT_COMMENTS3;
        this.OPEN_TIMEMWF = OPEN_TIMEMWF;
        this.OPEN_TIMETTHS = OPEN_TIMETTHS;
        this.OPEN_TIMESAT = OPEN_TIMESAT;
        this.CLOSE_TIMEMWF = CLOSE_TIMEMWF;
        this.CLOSE_TIMETTHS = CLOSE_TIMETTHS;
        this.CLOSE_TIMESAT = CLOSE_TIMESAT;
        this.LAST_SHIFTMWF = LAST_SHIFTMWF;
        this.LAST_SHIFTTTHS = LAST_SHIFTTTHS;
        this.LAST_SHIFTSAT = LAST_SHIFTSAT;
        this.START_SUPPLY_DATE = START_SUPPLY_DATE;
        this.START_COMMENTS = START_COMMENTS;
        this.MED_DIR = MED_DIR;
        this.CLINIC_MGR = CLINIC_MGR;
        this.ADMIN = ADMIN;
        this.DON = DON;
        this.BUS_UNIT = BUS_UNIT;
        this.REGION = REGION;
        this.AREA = AREA;
        this.OPENSUN = OPENSUN;
        this.CLOSESUN = CLOSESUN;
        this.PICKUPMWF = PICKUPMWF;
        this.PICKUPTTH = PICKUPTTH;
        this.PICKUPSAT = PICKUPSAT;
        this.HOURS_COMMENTS = HOURS_COMMENTS;
        this.HLAB_NUM = HLAB_NUM;
        this.SERVICELAB = SERVICELAB;
        this.TABLENAME = TABLENAME;
    }

    @Override
    public String toString() {
        return "MOREINFO_TABLE{" +
                "FACILITY_NUM='" + FACILITY_NUM + '\'' +
                ", ALT_PHONE1='" + ALT_PHONE1 + '\'' +
                ", ALT_PHONE2='" + ALT_PHONE2 + '\'' +
                ", ALT_PHONE3='" + ALT_PHONE3 + '\'' +
                ", ALT_COMMENTS1='" + ALT_COMMENTS1 + '\'' +
                ", ALT_COMMENTS2='" + ALT_COMMENTS2 + '\'' +
                ", ALT_COMMENTS3='" + ALT_COMMENTS3 + '\'' +
                ", OPEN_TIMEMWF=" + OPEN_TIMEMWF +
                ", OPEN_TIMETTHS=" + OPEN_TIMETTHS +
                ", OPEN_TIMESAT=" + OPEN_TIMESAT +
                ", CLOSE_TIMEMWF=" + CLOSE_TIMEMWF +
                ", CLOSE_TIMETTHS=" + CLOSE_TIMETTHS +
                ", CLOSE_TIMESAT=" + CLOSE_TIMESAT +
                ", LAST_SHIFTMWF=" + LAST_SHIFTMWF +
                ", LAST_SHIFTTTHS=" + LAST_SHIFTTTHS +
                ", LAST_SHIFTSAT=" + LAST_SHIFTSAT +
                ", START_SUPPLY_DATE=" + START_SUPPLY_DATE +
                ", START_COMMENTS='" + START_COMMENTS + '\'' +
                ", MED_DIR='" + MED_DIR + '\'' +
                ", CLINIC_MGR='" + CLINIC_MGR + '\'' +
                ", ADMIN='" + ADMIN + '\'' +
                ", DON='" + DON + '\'' +
                ", BUS_UNIT='" + BUS_UNIT + '\'' +
                ", REGION='" + REGION + '\'' +
                ", AREA='" + AREA + '\'' +
                ", OPENSUN=" + OPENSUN +
                ", CLOSESUN=" + CLOSESUN +
                ", PICKUPMWF=" + PICKUPMWF +
                ", PICKUPTTH=" + PICKUPTTH +
                ", PICKUPSAT=" + PICKUPSAT +
                ", HOURS_COMMENTS='" + HOURS_COMMENTS + '\'' +
                ", HLAB_NUM='" + HLAB_NUM + '\'' +
                ", SERVICELAB='" + SERVICELAB + '\'' +
                ", TABLENAME='" + SERVICELAB + '\'' +
                '}';
    }

    public String getTABLENAME() {
        return TABLENAME;
    }

    public void setTABLENAME(String TABLENAME) {
        this.TABLENAME = TABLENAME;
    }

    public String getSERVICELAB() {
        return SERVICELAB;
    }

    public void setSERVICELAB(String SERVICELAB) {
        this.SERVICELAB = SERVICELAB;
    }

    public String getFACILITY_NUM() {
        return FACILITY_NUM;
    }

    public void setFACILITY_NUM(String FACILITY_NUM) {
        this.FACILITY_NUM = FACILITY_NUM;
    }

    public String getALT_PHONE1() {
        return ALT_PHONE1;
    }

    public void setALT_PHONE1(String ALT_PHONE1) {
        this.ALT_PHONE1 = ALT_PHONE1;
    }

    public String getALT_PHONE2() {
        return ALT_PHONE2;
    }

    public void setALT_PHONE2(String ALT_PHONE2) {
        this.ALT_PHONE2 = ALT_PHONE2;
    }

    public String getALT_PHONE3() {
        return ALT_PHONE3;
    }

    public void setALT_PHONE3(String ALT_PHONE3) {
        this.ALT_PHONE3 = ALT_PHONE3;
    }

    public String getALT_COMMENTS1() {
        return ALT_COMMENTS1;
    }

    public void setALT_COMMENTS1(String ALT_COMMENTS1) {
        this.ALT_COMMENTS1 = ALT_COMMENTS1;
    }

    public String getALT_COMMENTS2() {
        return ALT_COMMENTS2;
    }

    public void setALT_COMMENTS2(String ALT_COMMENTS2) {
        this.ALT_COMMENTS2 = ALT_COMMENTS2;
    }

    public String getALT_COMMENTS3() {
        return ALT_COMMENTS3;
    }

    public void setALT_COMMENTS3(String ALT_COMMENTS3) {
        this.ALT_COMMENTS3 = ALT_COMMENTS3;
    }

    public Date getOPEN_TIMEMWF() {
        return OPEN_TIMEMWF;
    }

    public void setOPEN_TIMEMWF(Date OPEN_TIMEMWF) {
        this.OPEN_TIMEMWF = OPEN_TIMEMWF;
    }

    public Date getOPEN_TIMETTHS() {
        return OPEN_TIMETTHS;
    }

    public void setOPEN_TIMETTHS(Date OPEN_TIMETTHS) {
        this.OPEN_TIMETTHS = OPEN_TIMETTHS;
    }

    public Date getOPEN_TIMESAT() {
        return OPEN_TIMESAT;
    }

    public void setOPEN_TIMESAT(Date OPEN_TIMESAT) {
        this.OPEN_TIMESAT = OPEN_TIMESAT;
    }

    public Date getCLOSE_TIMEMWF() {
        return CLOSE_TIMEMWF;
    }

    public void setCLOSE_TIMEMWF(Date CLOSE_TIMEMWF) {
        this.CLOSE_TIMEMWF = CLOSE_TIMEMWF;
    }

    public Date getCLOSE_TIMETTHS() {
        return CLOSE_TIMETTHS;
    }

    public void setCLOSE_TIMETTHS(Date CLOSE_TIMETTHS) {
        this.CLOSE_TIMETTHS = CLOSE_TIMETTHS;
    }

    public Date getCLOSE_TIMESAT() {
        return CLOSE_TIMESAT;
    }

    public void setCLOSE_TIMESAT(Date CLOSE_TIMESAT) {
        this.CLOSE_TIMESAT = CLOSE_TIMESAT;
    }

    public Date getLAST_SHIFTMWF() {
        return LAST_SHIFTMWF;
    }

    public void setLAST_SHIFTMWF(Date LAST_SHIFTMWF) {
        this.LAST_SHIFTMWF = LAST_SHIFTMWF;
    }

    public Date getLAST_SHIFTTTHS() {
        return LAST_SHIFTTTHS;
    }

    public void setLAST_SHIFTTTHS(Date LAST_SHIFTTTHS) {
        this.LAST_SHIFTTTHS = LAST_SHIFTTTHS;
    }

    public Date getLAST_SHIFTSAT() {
        return LAST_SHIFTSAT;
    }

    public void setLAST_SHIFTSAT(Date LAST_SHIFTSAT) {
        this.LAST_SHIFTSAT = LAST_SHIFTSAT;
    }

    public Date getSTART_SUPPLY_DATE() {
        return START_SUPPLY_DATE;
    }

    public void setSTART_SUPPLY_DATE(Date START_SUPPLY_DATE) {
        this.START_SUPPLY_DATE = START_SUPPLY_DATE;
    }

    public String getSTART_COMMENTS() {
        return START_COMMENTS;
    }

    public void setSTART_COMMENTS(String START_COMMENTS) {
        this.START_COMMENTS = START_COMMENTS;
    }

    public String getMED_DIR() {
        return MED_DIR;
    }

    public void setMED_DIR(String MED_DIR) {
        this.MED_DIR = MED_DIR;
    }

    public String getCLINIC_MGR() {
        return CLINIC_MGR;
    }

    public void setCLINIC_MGR(String CLINIC_MGR) {
        this.CLINIC_MGR = CLINIC_MGR;
    }

    public String getADMIN() {
        return ADMIN;
    }

    public void setADMIN(String ADMIN) {
        this.ADMIN = ADMIN;
    }

    public String getDON() {
        return DON;
    }

    public void setDON(String DON) {
        this.DON = DON;
    }

    public String getBUS_UNIT() {
        return BUS_UNIT;
    }

    public void setBUS_UNIT(String BUS_UNIT) {
        this.BUS_UNIT = BUS_UNIT;
    }

    public String getREGION() {
        return REGION;
    }

    public void setREGION(String REGION) {
        this.REGION = REGION;
    }

    public String getAREA() {
        return AREA;
    }

    public void setAREA(String AREA) {
        this.AREA = AREA;
    }

    public Date getOPENSUN() {
        return OPENSUN;
    }

    public void setOPENSUN(Date OPENSUN) {
        this.OPENSUN = OPENSUN;
    }

    public Date getCLOSESUN() {
        return CLOSESUN;
    }

    public void setCLOSESUN(Date CLOSESUN) {
        this.CLOSESUN = CLOSESUN;
    }

    public Date getPICKUPMWF() {
        return PICKUPMWF;
    }

    public void setPICKUPMWF(Date PICKUPMWF) {
        this.PICKUPMWF = PICKUPMWF;
    }

    public Date getPICKUPTTH() {
        return PICKUPTTH;
    }

    public void setPICKUPTTH(Date PICKUPTTH) {
        this.PICKUPTTH = PICKUPTTH;
    }

    public Date getPICKUPSAT() {
        return PICKUPSAT;
    }

    public void setPICKUPSAT(Date PICKUPSAT) {
        this.PICKUPSAT = PICKUPSAT;
    }

    public String getHOURS_COMMENTS() {
        return HOURS_COMMENTS;
    }

    public void setHOURS_COMMENTS(String HOURS_COMMENTS) {
        this.HOURS_COMMENTS = HOURS_COMMENTS;
    }

    public String getHLAB_NUM() {
        return HLAB_NUM;
    }

    public void setHLAB_NUM(String HLAB_NUM) {
        this.HLAB_NUM = HLAB_NUM;
    }
}
