package com.spectra.asr.handler.sendout;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.demographic.AsrDemographicBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.notification.AbnormalResultsNotificationService;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public final class IntraLabsSendoutHandler {
	//private static Logger log = Logger.getLogger(IntraLabsSendoutHandler.class);
	
	public static List<StateResultDto> handle(ResultExtractDto resultExtractDto){
		List<StateResultDto> handledDtoList = null;
		//AsrBo asrBo = AsrBoFactory.getAsrBo();
		
		AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();

		String stateAbbreviation = resultExtractDto.getState();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("notifiedFlag", "N");
		paramMap.put("reportableState", stateAbbreviation);
		try{
			handledDtoList = asrDemographicBo.getIntraLabsNoDemo(paramMap);
			if(handledDtoList != null){
				for(StateResultDto handledDto : handledDtoList){
					String patientState = handledDto.getPatientAccountState();
					if(patientState == null){
						handledDto.setPatientAccountState(stateAbbreviation);
					}
				}
				//boolean notified = notifySendout(handledDtoList, resultExtractDto);
				//log.warn("handle(): notified: " + (notified ? "NOTIFIED" : "NOT NOTIFIED"));
			}
		}catch(BusinessException be){
			log.error(be.toString());
			be.printStackTrace();
		}
		return handledDtoList;
	}
	
	public static List<StateResultDto> handle(List<StateResultDto> dtoList, ResultExtractDto resultExtractDto){
		List<StateResultDto> handledDtoList = null;
		if(dtoList != null){
			//AsrBo asrBo = AsrBoFactory.getAsrBo();
			AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
			if((resultExtractDto == null) || (resultExtractDto.getFilterStateBy().length() == 0)){
				resultExtractDto.setFilterStateBy("pf");
			}
			String filterStateBy = resultExtractDto.getFilterStateBy();
			handledDtoList = new ArrayList<StateResultDto>();
			if(filterStateBy.equalsIgnoreCase("pf")){
				for(StateResultDto dto : dtoList){
					String sourceState = dto.getSourceState();
					if((sourceState.equalsIgnoreCase("patient")) || (sourceState.equalsIgnoreCase("facility"))){
						handledDtoList.add(dto);
					}
				}
			}else if(filterStateBy.equalsIgnoreCase("p")){
				for(StateResultDto dto : dtoList){
					String sourceState = dto.getSourceState();
					if(sourceState.equalsIgnoreCase("patient")){
						handledDtoList.add(dto);
					}
				}
			}else if(filterStateBy.equalsIgnoreCase("f")){
				for(StateResultDto dto : dtoList){
					String sourceState = dto.getSourceState();
					if(sourceState.equalsIgnoreCase("facility")){
						handledDtoList.add(dto);
					}
				}				
			}
			
			List<StateResultDto> sendoutDtoList = null;
			try{
				sendoutDtoList = new ArrayList<StateResultDto>();
				for(StateResultDto dto : dtoList){
					String sourceState = dto.getSourceState();
					if(sourceState.equalsIgnoreCase("sendout")){
						// add code to check if dto is in db; if in, add to list; if not, skip.
						Map<String, Object> paramMap = new HashMap<String, Object>();
						paramMap.put("orderNumber", dto.getOrderNumber());
						paramMap.put("notifiedFlag", "N");
						List<StateResultDto> nodemoDtoList = asrDemographicBo.getIntraLabsNoDemo(paramMap);
						if((nodemoDtoList == null) || (nodemoDtoList.size() == 0)){
							sendoutDtoList.add(dto);
						}
					}
				}
				boolean notified = notifySendout(sendoutDtoList, resultExtractDto);
			}catch(BusinessException be){
				log.error(be.toString());
				be.printStackTrace();
			}
		}
		return handledDtoList;
	}
	
	static boolean notifySendout(List<StateResultDto> sendoutDtoList, ResultExtractDto resultExtractDto) throws BusinessException {
		boolean notified = false;
		if(sendoutDtoList != null){
			if(resultExtractDto != null){
				AsrBo asrBo = AsrBoFactory.getAsrBo();
				AsrDemographicBo asrDemographicBo = AsrBoFactory.getAsrDemographicBo();
				GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
				AbnormalResultsNotificationService abnormalResultsNotificationService = (AbnormalResultsNotificationService)AsrServiceFactory.getServiceImpl(AbnormalResultsNotificationService.class.getSimpleName());
				
				//String gDtoState = gDto.getState();
				String gDtoStateAbbr = resultExtractDto.getState();
				String gDtoStateTarget = resultExtractDto.getGeneratorStateTarget();
				//String eastWestFlag = resultExtractDto.getEwFlag();
				String eastWestFlag = resultExtractDto.getEastWestFlag();
				log.warn("notifySendout(): gDtoStateAbbr: " + (gDtoStateAbbr == null ? "NULL" : gDtoStateAbbr));
				log.warn("notifySendout(): gDtoStateTarget: " + (gDtoStateTarget == null ? "NULL" : gDtoStateTarget));
				log.warn("notifySendout(): eastWestFlag: " + (eastWestFlag == null ? "NULL" : eastWestFlag));
				
				
				StateMasterDto smDto = new StateMasterDto();
				smDto.setStateAbbreviation(gDtoStateAbbr);
				List<StateMasterDto> smdList = asrBo.getStateMaster(smDto);
				log.warn("notifySendout(): smdList: " + (smdList == null ? "NULL" : smdList.toString()));
				if(smdList.size() == 1){
					smDto = smdList.get(0);
				}
				
				
				GeneratorDto gDto = new GeneratorDto();
				gDto.setStateFk(smDto.getStateMasterPk());
				gDto.setWriteBy("state");
				List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(gDto);
				if(generatorDtoList != null){
					gDto = generatorDtoList.get(0);
				}
				
				
				//DateFormat df = new SimpleDateFormat("yyyyMMdd");
				//String dt = df.format(new Date());
				//df = new SimpleDateFormat("yyyyMM");
				//String dtt = df.format(new Date());
				
				DateFormat df = new SimpleDateFormat("MMM dd yyyy hh:mm:ss aaa");
				String dtt = df.format(new Date());
				
				StringBuilder subjectBuilder = new StringBuilder();
				subjectBuilder.append(gDto.getStateAbbreviation()).append(" intra-labs sendout missing demographics: ").append(dtt);
				log.warn("notifySendout(): subjectBuilder: " + (subjectBuilder == null ? "NULL" : subjectBuilder.toString()));
				
				StringBuilder msgBuilder = new StringBuilder();
				msgBuilder.append(subjectBuilder.toString()).append("\n #ENCRYPT EMAIL#");
				log.warn("notifySendout(): msgBuilder: " + (msgBuilder == null ? "NULL" : msgBuilder.toString()));
				
				notified = abnormalResultsNotificationService.notify(sendoutDtoList, gDto, eastWestFlag, subjectBuilder.toString(), msgBuilder.toString());
				log.warn("notifySendout(): notified: " + (notified ? "TRUE" : "FALSE"));
				
				if(notified){
					int rowsUpdated = 0;
					for(StateResultDto sendoutDto : sendoutDtoList){
						sendoutDto.setNotifiedFlag("Y");
						rowsUpdated += asrDemographicBo.updateIntraLabsNoDemo(sendoutDto);
					}
					log.warn("notifySendout(): rowsUpdated: " + (rowsUpdated == 0 ? "ZARO" : String.valueOf(rowsUpdated)));
				}
/*				
				//HSSF_FLAVOR
				StateMasterDto stateMasterDto = new StateMasterDto();
				//stateMasterDto.setStateAbbreviation("MD");
				stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
				stateMasterDto.setStatus("active");
				List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
				log.warn("notifySendout(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
				if(stdList.size() == 1){
					stateMasterDto = stdList.get(0);
				}else{
					throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
				}
				
				GeneratorDto generatorDto = new GeneratorDto();
				generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
				generatorDto.setStatus("active");
				generatorDto.setWriteBy("state");
				List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
				log.warn("notifySendout(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
				if((generatorDtoList != null) && (generatorDtoList.size() > 0)){
					generatorDto = generatorDtoList.get(0);
					//generatorDto.setStateTarget(gDtoStateAbbr);
					generatorDto.setStateTarget(gDtoStateTarget);
				}
				// end HSSF_FLAVOR

				log.warn("notifySendout(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
				
				//GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(gDto);
				GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
				log.warn("notifySendout(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
				
				Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(sendoutDtoList);
				log.warn("notifySendout(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
				log.warn("notifySendout(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
*/				
				
			}
		}
		return notified;
	}
}
