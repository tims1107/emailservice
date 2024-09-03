package com.spectra.dto.asr;

import com.opencsv.bean.CsvBindByPosition;


public class HL7_FILE2 {

    @CsvBindByPosition(position = 0)
    private String segment;

    public HL7_FILE2() {
    }

    public HL7_FILE2(String segment) {
        this.segment = segment;

    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        return "HL7_FILE2{" +
                "segment='" + segment + '\'' +
                '}';
    }
}
