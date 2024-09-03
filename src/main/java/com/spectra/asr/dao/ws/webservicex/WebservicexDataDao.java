package com.spectra.asr.dao.ws.webservicex;

import com.spectra.asr.dto.ws.webservicex.WebservicexDataContainerDto;

public interface WebservicexDataDao {
	WebservicexDataContainerDto getWebservicexDataContainerFromZip(String zip);
}
