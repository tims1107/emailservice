package com.spectra.asr.dev.model;


import com.spectra.asr.dev.dto.clientmaster.FACILITY_TABLE;
import com.spectra.asr.dev.dto.clientmaster.Facility;
import com.spectra.asr.dev.dto.clientmaster.LEGACY_FACILITY;
import com.spectra.asr.dev.dto.clientmaster.MOREINFO_TABLE;

import java.util.List;
import java.util.Map;

public interface SqlServerViewListener {
    public void findFacility(Map<Integer, Facility> results);
    public void insertFacility(List<LEGACY_FACILITY> list);
    public void insertFacilityTable(List<FACILITY_TABLE> list);
    public void insertMoreAccountInfo(List<MOREINFO_TABLE> list);

}
