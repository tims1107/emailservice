package com.spectra.dto.legacy;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HOURS {
    private int clinichoursid;
    private int clinicid;
    private int hourtypeid;
    private int dayid;
    private Timestamp hourofday;
    private String hlabnumber;
    private String zone;
    private final SimpleDateFormat sdf;
    private final SimpleDateFormat sdfsql;

    public HOURS(int clinichoursid, int clinicid, int hourtypeid, int dayid, Timestamp hourofday, String hlabnumber, String zone) {

        this.sdf = new SimpleDateFormat("hh:mm a");

        this.sdfsql = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

        this.clinichoursid = clinichoursid;
        this.clinicid = clinicid;
        this.hourtypeid = hourtypeid;
        this.dayid = dayid;
        this.hourofday = hourofday;
        this.hlabnumber = hlabnumber;
        this.zone = zone;
    }

    public String getLegacyTime(){
        Calendar cal = Calendar.getInstance();
        if (hourofday != null) {
            try {
                cal.setTime(hourofday);
                //System.out.println(sdf.format(cal.getTime()));
                return sdfsql.format(cal.getTime());
            } catch (Exception pe) {
                pe.printStackTrace();
            }
        }

        return null;




    }

    public String getZone() {
        return zone;
    }

    public String getHlabnumber() {
        return hlabnumber;
    }

    public int getClinichoursid() {
        return clinichoursid;
    }

    public int getClinicid() {
        return clinicid;
    }

    public int getHourtypeid() {
        return hourtypeid;
    }

    public int getDayid() {
        return dayid;
    }

    public Timestamp getHourofday() {
        return hourofday;
    }
}
