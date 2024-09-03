package com.spectra.dto.asr;

import com.opencsv.bean.CsvBindByPosition;


public class HL7_FILE {

    @CsvBindByPosition(position = 0)
    private  String segment;

    public HL7_FILE() {
    }

    public HL7_FILE(String segment, String field, String strvalue) {
        this.segment = segment;
        this.field = field;
        this.strvalue = strvalue;
    }

    @CsvBindByPosition(position = 1)
    private String field;

    @CsvBindByPosition(position = 2)
    private String strvalue;

    public  String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public  String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }



    public String getStrvalue() {
        return strvalue;
    }

    public void setStrvalue(String strvalue) {
        this.strvalue = strvalue;
    }
}
