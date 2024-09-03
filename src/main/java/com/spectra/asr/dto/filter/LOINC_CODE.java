package com.spectra.asr.dto.filter;

public class LOINC_CODE {

    private long organismfilterpk;
    private String organismname;
    private String loinccode;
    private String loincname;
    private String valuetype;

    public LOINC_CODE(long organismfilterpk, String organismname, String loinccode, String loincname, String valuetype) {
        this.organismfilterpk = organismfilterpk;
        this.organismname = organismname;
        this.loinccode = loinccode;
        this.loincname = loincname;
        this.valuetype = valuetype;
    }

    public LOINC_CODE() {
    }

    @Override
    public String toString() {
        return "LOINC_CODE{" +
                "organismfilterpk=" + organismfilterpk +
                ", organismname='" + organismname + '\'' +
                ", loinccode='" + loinccode + '\'' +
                ", loincname='" + loincname + '\'' +
                ", valuetype='" + valuetype + '\'' +
                '}';
    }

    public long getOrganismfilterpk() {
        return organismfilterpk;
    }

    public void setOrganismfilterpk(long organismfilterpk) {
        this.organismfilterpk = organismfilterpk;
    }

    public String getOrganismname() {
        return organismname;
    }

    public void setOrganismname(String organismname) {
        this.organismname = organismname;
    }

    public String getLoinccode() {
        return loinccode;
    }

    public void setLoinccode(String loinccode) {
        this.loinccode = loinccode;
    }

    public String getLoincname() {
        return loincname;
    }

    public void setLoincname(String loincname) {
        this.loincname = loincname;
    }

    public String getValuetype() {
        return valuetype;
    }

    public void setValuetype(String valuetype) {
        this.valuetype = valuetype;
    }
}
