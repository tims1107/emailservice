package com.spectra.asr.dev.model;


import com.spectra.asr.dev.dto.clientmaster.Facility;

import java.util.Map;

public interface CMListener {
    public void findFacility(Map<Integer, Facility> results);
}
