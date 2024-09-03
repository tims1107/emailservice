package com.spectra.asr.enums;

public enum AfterHourEnum {

    clinicafterhoursid (1, 2, "Integer"),
    clinicid (2, 2, "Integer"),
    phone (3, 12, "String"),
    ext (4, 12, "String"),
    contact (5, 12, "String"),
    hlabnumber (6, 12, "String"),
    servicelab (7, 12, "String");


    private final int col;
    private final int coltype;
    private final String coltypename;

    AfterHourEnum(int col, int coltype, String coltypename) {
        this.col = col;
        this.coltype = coltype;
        this.coltypename = coltypename;
    }



    public int getCol() {
        return col;
    }

    public int getColtype() {
        return coltype;
    }

    public String getColtypename() {
        return coltypename;
    }
}
