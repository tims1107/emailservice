package com.spectra.asr.app.abnormal;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.service.abnormal.AbnormalLocalService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AbnormalLocalApp {
	//private static Logger log = Logger.getLogger(AbnormalLocalApp.class);
	
	public static void main(String[] args){
		if(args.length == 2){
			String entity = args[0];
			String eastWest = args[1];
			if((entity != null) && (eastWest != null)){
				AbnormalLocalService abnormalLocalService = (AbnormalLocalService)AsrServiceFactory.getServiceImpl(AbnormalLocalService.class.getSimpleName());
				if(abnormalLocalService != null){
					AsrBo asrBo = AsrBoFactory.getAsrBo();
					if(asrBo != null){
						Boolean createdEast = null;
						Boolean createdWest = null;
						try{
							asrBo.callProcTrackingInsert(entity);
							
							if((eastWest.equalsIgnoreCase("E")) || (eastWest.equalsIgnoreCase("EW"))){
								createdEast = abnormalLocalService.createResults(entity, "East");
								if(createdEast != null){
									log.warn("main(): created EAST: " + (createdEast == null ? "NULL" : entity + " " + createdEast.toString()));
								}
							}
							
							if((eastWest.equalsIgnoreCase("W")) || (eastWest.equalsIgnoreCase("EW"))){
								createdWest = abnormalLocalService.createResults(entity, "West");
								if(createdWest != null){
									log.warn("main(): created WEST: " + (createdWest == null ? "NULL" : entity + " " + createdWest.toString()));
								}
							}
							
							//if((createdEast != null) && (createdWest != null)){
							if((createdEast != null) || (createdWest != null)){
								if(createdEast.booleanValue() || createdWest.booleanValue()){
									asrBo.callProcTrackingUpdate(entity);
								}
							}
						}catch(BusinessException be){
							log.error(String.valueOf(be));
							be.printStackTrace();
						}
					}
				}
			}
		}else{
			log.error("Usage: " + AbnormalLocalApp.class.getSimpleName() + " <entity>");
		}
	}
}
