package com.spectra.asr.app.extract.eip;

import com.spectra.asr.service.eip.extract.EipResultsExtractService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EipResultsExtractApp {
	//private static Logger log = Logger.getLogger(EipResultsExtractApp.class);
	
	public static void main(String[] args){
		EipResultsExtractService eipResultsExtractService = (EipResultsExtractService)AsrServiceFactory.getServiceImpl(EipResultsExtractService.class.getSimpleName());
		if(eipResultsExtractService != null){
			Integer rowsExtracted = null;
			try{
				rowsExtracted = eipResultsExtractService.extractResults();
				if(rowsExtracted != null){
					log.warn("main(): rowsExtracted: " + (rowsExtracted == null ? "NULL" : rowsExtracted.toString()));
				}
			}catch(BusinessException be){
				log.error(String.valueOf(be));
				be.printStackTrace();
			}
		}
	}
}
