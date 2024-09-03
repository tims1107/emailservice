package com.spectra.asr.app.eip;

import com.spectra.asr.service.eip.EipLocalService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class EipLocalApp {
	//private static Logger log = Logger.getLogger(EipLocalApp.class);
	
	public static void main(String[] args){
		if(args.length == 1){
			String entity = args[0];
			if(entity != null){
				EipLocalService eipLocalService = (EipLocalService)AsrServiceFactory.getServiceImpl(EipLocalService.class.getSimpleName());
				if(eipLocalService != null){
					Boolean created = null;
					try{
						created = eipLocalService.createResults(entity);
						if(created != null){
							log.warn("main(): created: " + (created == null ? "NULL" : entity + " " + created.toString()));
						}
					}catch(BusinessException be){
						log.error(String.valueOf(be));
						be.printStackTrace();
					}
				}
			}
		}else{
			log.error("Usage: " + EipLocalApp.class.getSimpleName() + " <entity>");
		}
	}
}
