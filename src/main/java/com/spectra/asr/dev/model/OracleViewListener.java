package com.spectra.asr.dev.model;

import com.spectra.asr.dev.dto.clientmaster.COHORT;
import com.spectra.asr.dev.dto.clientmaster.Facility;

import java.util.Map;

public interface OracleViewListener {

    public void loadFacilities(Map<Integer, Facility> facility);

    public void moveCohort(Map<String, COHORT> cohort);

}
