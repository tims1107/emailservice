package com.spectra.asr.dto.snomed;

import java.sql.Timestamp;

public class SNOMED_MASTER {

    private long snomedid;
    private String snomedcode;
    private String preferredname;
    private String localname;
    private Timestamp last_updated_date;
    private String specimen_source_code;
    private String specimen_source_desc;
    private String snomed_type;

    public SNOMED_MASTER(long snomedid, String snomedcode, String preferredname, String localname, Timestamp last_updated_date, String specimen_source_code, String specimen_source_desc, String snomed_type) {
        this.snomedid = snomedid;
        this.snomedcode = snomedcode;
        this.preferredname = preferredname;
        this.localname = localname;
        this.last_updated_date = last_updated_date;
        this.specimen_source_code = specimen_source_code;
        this.specimen_source_desc = specimen_source_desc;
        this.snomed_type = snomed_type;
    }

    public SNOMED_MASTER() {
    }

    public long getSnomedid() {
        return snomedid;
    }

    public void setSnomedid(long snomedid) {
        this.snomedid = snomedid;
    }

    public String getSnomedcode() {
        return snomedcode;
    }

    public void setSnomedcode(String snomedcode) {
        this.snomedcode = snomedcode;
    }

    public String getPreferredname() {
        return preferredname;
    }

    public void setPreferredname(String preferredname) {
        this.preferredname = preferredname;
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname;
    }

    public Timestamp getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(Timestamp last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public String getSpecimen_source_code() {
        return specimen_source_code;
    }

    public void setSpecimen_source_code(String specimen_source_code) {
        this.specimen_source_code = specimen_source_code;
    }

    public String getSpecimen_source_desc() {
        return specimen_source_desc;
    }

    public void setSpecimen_source_desc(String specimen_source_desc) {
        this.specimen_source_desc = specimen_source_desc;
    }

    public String getSnomed_type() {
        return snomed_type;
    }

    public void setSnomed_type(String snomed_type) {
        this.snomed_type = snomed_type;
    }

    @Override
    public String toString() {
        return "SNOMED_MASTER{" +
                "snomedid=" + snomedid +
                ", snomedcode='" + snomedcode + '\'' +
                ", preferredname='" + preferredname + '\'' +
                ", localname='" + localname + '\'' +
                ", last_updated_date=" + last_updated_date +
                ", specimen_source_code='" + specimen_source_code + '\'' +
                ", specimen_source_desc='" + specimen_source_desc + '\'' +
                ", snomed_type='" + snomed_type + '\'' +
                '}';
    }


}
