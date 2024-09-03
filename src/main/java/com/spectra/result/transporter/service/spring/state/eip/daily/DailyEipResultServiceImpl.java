package com.spectra.result.transporter.service.spring.state.eip.daily;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.factory.ConversionContextFactory;
import com.spectra.result.transporter.context.poi.PoiConversionContext;
import com.spectra.result.transporter.context.writer.factory.WriterContextFactory;
import com.spectra.result.transporter.context.writer.poi.PoiWriterContext;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import com.spectra.result.transporter.service.spring.SpringServiceImpl;
import com.spectra.result.transporter.service.spring.rr.RepositoryService;
import com.spectra.result.transporter.utils.calendar.CalendarUtils;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;
@Slf4j
public class DailyEipResultServiceImpl extends SpringServiceImpl implements DailyEipResultService {
	///private Logger log = Logger.getLogger(DailyEipResultServiceImpl.class);

	protected ConfigService configService;
	protected ConversionContextFactory conversionContextFactory;
	protected ResultSchedulerServiceFactory resultSchedulerServiceFactory;
	protected WriterContextFactory writerContextFactory;

	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;

	}

	@Override
	public void setConversionContextFactory(ConversionContextFactory conversionContextFactory) {
		this.conversionContextFactory = conversionContextFactory;

	}

	@Override
	public void setResultSchedulerServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory) {
		this.resultSchedulerServiceFactory = resultSchedulerServiceFactory;

	}

	@Override
	public void setWriterContextFactory(WriterContextFactory writerContextFactory) {
		this.writerContextFactory = writerContextFactory;

	}

	@Override
	public boolean process() {
		boolean processed = false;
		if((this.configService != null) &&
				(this.conversionContextFactory != null) &&
				(this.resultSchedulerServiceFactory != null) &&
				(this.writerContextFactory != null)){
			try{
				String state = this.configService.getString("state.process");
				if(state != null){
					String conversionCtx = this.configService.getString("conversion.context");
					PoiConversionContext conversionContext = (PoiConversionContext)this.conversionContextFactory.getCtxImpl(conversionCtx);
					if(conversionContext != null){
						RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
						String procType = this.configService.getString("proc.type");
						if(repositoryService != null){
							String writerCtx = this.configService.getString("writer.context");
							PoiWriterContext writerContext = (PoiWriterContext)writerContextFactory.getCtxImpl(writerCtx);
							if(writerContext != null){
								List<RepositoryResultDto> dtoList = null;
								try{
									Calendar now = Calendar.getInstance();
									Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
									log.debug("testExecuteStrategy(): prevMonth: " + (prevMonth <= 0 ? "ZARO" : prevMonth.toString()));
									Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
									log.debug("testExecuteStrategy(): prevMonthYear: " + (prevMonthYear <= 0 ? "ZARO" : prevMonthYear.toString()));
									Integer prevDayOfMonth = CalendarUtils.getPrevDayOfMonthAsInteger(now);
									log.debug("testExecuteStrategy(): prevDayOfMonth: " + (prevDayOfMonth <= 0 ? "ZARO" : prevDayOfMonth.toString()));
									Integer currMonth = CalendarUtils.getCurrentMonthAsInteger(now);
									log.debug("testExecuteStrategy(): currMonth: " + (currMonth <= 0 ? "ZARO" : currMonth.toString()));

									String freq = this.configService.getString("run.frequency");
									Map paramMap = new HashMap();
									if(freq != null){
										if(freq.equals("monthly")){
											//paramMap.put("procType", "MRSA");
											//paramMap.put("state", "NJ");
											paramMap.put("month", prevMonth);
											paramMap.put("year", prevMonthYear);
											//paramMap.put("year", new Integer(2011));
											//List<RepositoryResultDto> dtoList = null;
											//paramMap.put("stateResults", dtoList);
										}else if(freq.equals("daily")){
											//paramMap.put("procType", "MRSA");
											//paramMap.put("state", "NJ");
											paramMap.put("day", prevDayOfMonth);
											paramMap.put("month", currMonth);
											paramMap.put("year", prevMonthYear);
											//paramMap.put("year", new Integer(2011));
											//List<RepositoryResultDto> dtoList = null;
											//paramMap.put("stateResults", dtoList);
										}
									}

									//demo
									/*if(procType.equals("MRSA")){
										dtoList = repositoryService.getMrsaResults(paramMap);
									}else if(procType.equals("CDIFF")){
										dtoList = repositoryService.getCdiffResults(paramMap);
									}else if(procType.equals("MSSA")){
										dtoList = repositoryService.getMssaResults(paramMap);
									}else if(procType.equals("CRE")){
										dtoList = repositoryService.getCreResults(paramMap);
									}*/
									if(procType.equals("CRE")){
										dtoList = repositoryService.getCreResults(paramMap);
									}
									log.debug("process(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
									Boolean processedDemo = Boolean.FALSE;
									Boolean processedCountyDemo = Boolean.FALSE;
									if((dtoList != null) && (dtoList.size() > 0)){
										String writerBy = this.configService.getString("writer.by");
										Map<String, List<RepositoryResultDto>> dtoListMap = null;

										//county
										if(writerBy.indexOf("county") != -1){
											dtoListMap = repositoryService.getStateResultsFromDtoListByCounty(dtoList);
											log.debug("process(): dtoListMap.keySet: " + (dtoListMap.keySet() == null ? "NULL" : dtoListMap.keySet().toString()));
											if(dtoListMap != null){
												Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
												for(Iterator<Map.Entry<String, List<RepositoryResultDto>>> it = entrySet.iterator(); it.hasNext();){
													Map.Entry<String, List<RepositoryResultDto>> entry = it.next();
													String key = entry.getKey();
													//key = key.substring(key.indexOf(".") + 1);
													List<RepositoryResultDto> mapDtoList = entry.getValue();
													processedCountyDemo = execStrategies(key, mapDtoList, true, procType, paramMap, repositoryService, conversionContext, writerContext);
													if(processedCountyDemo.equals(Boolean.FALSE)){
														StringBuilder errBuilder = new StringBuilder();
														errBuilder.append("process(): processedCountyDemo: ").append(processedCountyDemo.toString());
														errBuilder.append(" procType: ").append(procType);
														errBuilder.append(" state: ").append(state);
														errBuilder.append(" key: ").append(key);
														errBuilder.append(" conversionCtx: ").append(conversionCtx);
														errBuilder.append(" writerCtx: ").append(writerCtx);
														log.error(errBuilder.toString());
													}
												}
											}
										}
										//end county

										//state
										dtoListMap = repositoryService.getStateResultsFromDtoList(dtoList);
										if(dtoListMap != null){
											Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
											for(Iterator<Map.Entry<String, List<RepositoryResultDto>>> it = entrySet.iterator(); it.hasNext();){
												Map.Entry<String, List<RepositoryResultDto>> entry = it.next();
												String key = entry.getKey();
												//key = key.substring(key.indexOf(".") + 1);
												List<RepositoryResultDto> mapDtoList = entry.getValue();
												processedCountyDemo = execStrategies(key, mapDtoList, true, procType, paramMap, repositoryService, conversionContext, writerContext);
												if(processedCountyDemo.equals(Boolean.FALSE)){
													StringBuilder errBuilder = new StringBuilder();
													errBuilder.append("process(): processedCountyDemo: ").append(processedCountyDemo.toString());
													errBuilder.append(" procType: ").append(procType);
													errBuilder.append(" state: ").append(state);
													errBuilder.append(" key: ").append(key);
													errBuilder.append(" conversionCtx: ").append(conversionCtx);
													errBuilder.append(" writerCtx: ").append(writerCtx);
													log.error(errBuilder.toString());
												}
											}
										}
										//processedDemo = execStrategies(dtoList, true, procType, paramMap, repositoryService, conversionContext, writerContext);
										//end state

										//int rowsUpdated = insertEipResultProcessed(dtoList, procType);
										//log.debug("process(): rowsUpdated: " + String.valueOf(rowsUpdated));
									}
									//end demo

									//no demo
									paramMap.put("nodemo", "nodemo");
									/*if(procType.equals("MRSA")){
										dtoList = repositoryService.getMrsaResults(paramMap);
									}else if(procType.equals("CDIFF")){
										dtoList = repositoryService.getCdiffResults(paramMap);
									}else if(procType.equals("MSSA")){
										dtoList = repositoryService.getMssaResults(paramMap);
									}else if(procType.equals("CRE")){
										dtoList = repositoryService.getCreResults(paramMap);
									}*/
									if(procType.equals("CRE")){
										dtoList = repositoryService.getCreResults(paramMap);
									}
									log.debug("process(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
									Boolean processedNoDemo = Boolean.FALSE;
									if((dtoList != null) && (dtoList.size() > 0)){
										processedNoDemo = execStrategies(null, dtoList, false, procType, paramMap, repositoryService, conversionContext, writerContext);
									}
									//end no demo

									//processed = (processedDemo.booleanValue() && processedCountyDemo.booleanValue() && processedNoDemo.booleanValue());
									processed = (processedDemo.booleanValue() && processedCountyDemo.booleanValue());
								}catch(BusinessException be){
									log.error(be.getMessage());
									be.printStackTrace();
								}

								/*
								String writerBy = this.configService.getString("writer.by");
								if(writerBy.indexOf("county") != -1){
									Map<String, List<RepositoryResultDto>> dtoListMap = null;
									try{
										dtoListMap = repositoryService.getStateResultsByCounty(procType, state);
										if(dtoListMap != null){
											log.debug("testGetStateResultsByCounty_CA(): dtoListMap: " + (dtoListMap == null? "NULL" : String.valueOf(dtoListMap.size())));
											log.debug("testGetStateResultsByCounty_CA(): dtoListMap: " + (dtoListMap == null? "NULL" : dtoListMap.toString()));
											Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
											for(Iterator<Map.Entry<String, List<RepositoryResultDto>>> it = entrySet.iterator(); it.hasNext();){
												Map.Entry<String, List<RepositoryResultDto>> entry = it.next();
												String cnty = entry.getKey();
												List<RepositoryResultDto> dtoList = entry.getValue();
												if(dtoList != null){
													String resultStr = conversionContext.executeStrategy(dtoList);
													log.debug("process(): resultStr: " + (resultStr == null? "NULL" : resultStr));

													Boolean wrote = writerContext.executeStrategy(cnty, resultStr);
													log.debug("process(): wrote: " + (wrote == null? "NULL" : wrote.toString()));
													processed = wrote.booleanValue();
													if(processed == false){
														StringBuilder errBuilder = new StringBuilder();
														errBuilder.append("process(): wrote: ").append(String.valueOf(processed));
														errBuilder.append(" procType: ").append(procType);
														errBuilder.append(" state: ").append(state);
														errBuilder.append(" conversionCtx: ").append(conversionCtx);
														errBuilder.append(" writerCtx: ").append(writerCtx);
														log.error(errBuilder.toString());
													}
												}
											}//for
											List<RepositoryResultDto> collectionList = new ArrayList<RepositoryResultDto>();
											for(List<RepositoryResultDto> dtoList : dtoListMap.values()){
												if(dtoList != null){
													for(RepositoryResultDto dto : dtoList){
														collectionList.add(dto);
													}
												}
											}
											int rowsUpdated = this.updateResultsExtract(collectionList);
											log.debug("process(): rowsUpdated: " + String.valueOf(rowsUpdated));
										}
									}catch(BusinessException be){
										log.error(be);
										be.printStackTrace();
										processed = false;
									}
								}else if(writerBy.indexOf("state") != -1){
									List<RepositoryResultDto> dtoList = null;
									try{
										dtoList = repositoryService.getStateResults(procType, state);
										log.debug("process(): dtoList: " + (dtoList == null? "NULL" : String.valueOf(dtoList.size())));
										String resultStr = conversionContext.executeStrategy(dtoList);
										log.debug("process(): resultStr: " + (resultStr == null? "NULL" : resultStr));

										Boolean wrote = writerContext.executeStrategy(resultStr);
										log.debug("process(): wrote: " + (wrote == null? "NULL" : wrote.toString()));
										processed = wrote.booleanValue();
									}catch(BusinessException be){
										log.error(be);
										be.printStackTrace();
										processed = false;
									}
									int rowsUpdated = this.updateResultsExtract(dtoList);
									log.debug("process(): rowsUpdated: " + String.valueOf(rowsUpdated));
								}
								*/
							}
						}
					}
				}
			}catch(ConfigException ce){
				log.error(ce.getMessage());
				ce.printStackTrace();
				processed = false;
			}
		}
		return processed;
	}

	Boolean execStrategies(String loc, List<RepositoryResultDto> dtoList, boolean hasDemo, String procType, Map paramMap, RepositoryService repositoryService, PoiConversionContext conversionContext, PoiWriterContext writerContext) throws BusinessException{
		Boolean execd = Boolean.FALSE;
		if((dtoList != null) && (procType != null) && (paramMap != null) && (repositoryService != null)){
			if((dtoList != null) && (dtoList.size() > 0)){
				Workbook wb = conversionContext.executeStrategy(dtoList);
				if(wb != null){
					execd = writerContext.executeStrategy(hasDemo, loc, wb);
					/*
					if(hasDemo){
						execd = writerContext.executeStrategy(hasDemo, loc, wb);
					}else{
						execd = writerContext.executeStrategy(hasDemo, wb);
					}
					*/
				}
			}else{
				execd = Boolean.TRUE;
			}
		}//if
		return execd;
	}

	int insertEipResultProcessed(List<RepositoryResultDto> dtoList, String resultType){
		int rowsUpdated = 0;
		if((dtoList != null) && (resultType != null)){
			if(this.resultSchedulerServiceFactory != null){
				RepositoryService repositoryService = (RepositoryService)this.resultSchedulerServiceFactory.getContextService(RepositoryService.class.getSimpleName());
				if(repositoryService != null){
					try{
						for(RepositoryResultDto dto: dtoList){
							dto.setResultType(resultType);
							rowsUpdated += repositoryService.insertRsEipResultsProcessed(dto);
						}
					}catch(BusinessException be){
						log.error(be.getMessage());
						be.printStackTrace();
					}
				}
			}
		}
		return rowsUpdated;
	}
}
