package com.spectra.result.transporter.service.file;

import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;

public interface ResultFileTransportService {
	void setBoFactory(RepositoryBoFactory repositoryBoFactory);
	void run();
}
