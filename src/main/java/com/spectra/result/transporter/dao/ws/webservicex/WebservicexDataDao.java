package com.spectra.result.transporter.dao.ws.webservicex;

import com.spectra.result.transporter.dto.ws.webservicex.WebservicexDataContainerDto;

public interface WebservicexDataDao {
	WebservicexDataContainerDto getWebservicexDataContainerFromZip(String zip);
}
