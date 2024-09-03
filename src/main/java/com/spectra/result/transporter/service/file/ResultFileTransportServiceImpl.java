package com.spectra.result.transporter.service.file;

import com.spectra.result.transporter.businessobject.spring.file.ResultFileTransportBo;
import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;
import com.spectra.result.transporter.service.spring.SpringServiceImpl;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ResultFileTransportServiceImpl extends SpringServiceImpl implements ResultFileTransportService {
	//private Logger log = Logger.getLogger(ResultFileTransportServiceImpl.class);
	
	protected RepositoryBoFactory repositoryBoFactory;
	
	@Override
	public void setBoFactory(RepositoryBoFactory repositoryBoFactory) {
		this.repositoryBoFactory = repositoryBoFactory;
	}

	@Override
	public void run() {
		if(this.repositoryBoFactory != null){
			ResultFileTransportBo bo = this.repositoryBoFactory.getResultFileTransportBo();
			bo.run();
		}
	}

}
