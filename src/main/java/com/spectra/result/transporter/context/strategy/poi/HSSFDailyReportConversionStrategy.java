package com.spectra.result.transporter.context.strategy.poi;

import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;
import com.spectra.result.transporter.businessobject.spring.poi.daily.DailyAbnReportWorkbookBo;
import com.spectra.result.transporter.context.strategy.PoiConversionStrategy;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

//import com.spectra.result.transporter.businessobject.spring.poi.DailyReportWorkbookBo;

@Slf4j
public class HSSFDailyReportConversionStrategy implements PoiConversionStrategy {
	//private Logger log = Logger.getLogger(HSSFDailyReportConversionStrategy.class);
	
	private RepositoryBoFactory repositoryBoFactory;
	
	public void setBoFactory(RepositoryBoFactory repositoryBoFactory){
		this.repositoryBoFactory = repositoryBoFactory;
	}
	
	@Override
	public Workbook convert(List<RepositoryResultDto> dtoList) {
		Workbook wb = null;
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				//DailyReportWorkbookBo bo = this.repositoryBoFactory.getDailyReportWorkbookBo();
				DailyAbnReportWorkbookBo bo = this.repositoryBoFactory.getDailyAbnReportWorkbookBo();
				if(bo != null){
					wb = bo.toWorkbook(dtoList);
					//Map<String, List<PatientRecord>> listMap = this.toPatientRecordListMap(dtoList);
					//log.debug("convert(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
					//wb = bo.toWorkbook(listMap);
				}
			}
		}
		return wb;
	}
	
	public Workbook convertDailyReport(List<PatientRecord> dtoList) {
		Workbook wb = null;
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				//DailyReportWorkbookBo bo = this.repositoryBoFactory.getDailyReportWorkbookBo();
				DailyAbnReportWorkbookBo bo = this.repositoryBoFactory.getDailyAbnReportWorkbookBo();
				if(bo != null){
					wb = bo.toWorkbookDailyReport(dtoList);
				}
			}
		}
		return wb;
	}
	
	public Workbook convertDailyReport(PatientRecord patientRecord){
		return null;
	}

/*
	public Map<String, List<PatientRecord>> toPatientRecordListMap(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				//String reqNo = dto.getOrderNumber();
				String patientName = dto.getPatientName();
				if(patientName != null){
					if(dtoListMap.containsKey(patientName)){
						List<RepositoryResultDto> reqListDto = dtoListMap.get(patientName);
						reqListDto.add(dto);
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(patientName, reqDtoList);
					}
				}
			}//for
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					RepositoryResultDto dto = resultDtoList.get(0);
					PatientRecord patientRecord = this.repositoryResultDtoToPatientRecord(dto);
					if(patientRecord != null){
						List<OBXRecord> obxList = new ArrayList<OBXRecord>();
						List<NTERecord> nteList = new ArrayList<NTERecord>();
						for(RepositoryResultDto resultDto : resultDtoList){
							OBXRecord obxRecord = this.repositoryResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = this.repositoryResultDtoToNTERecord(resultDto);
							nteList.add(nteRecord);
						}
						
						patientRecord.setObxList(obxList);
						patientRecord.setNteList(nteList);
						
						String facilityId = dto.getFacilityId();
						if(facilityId != null){
							if(listMap.containsKey(facilityId)){
								List<PatientRecord> patientRecList = listMap.get(facilityId);
								if(patientRecList != null){
									patientRecList.add(patientRecord);
								}
							}else{
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(facilityId, patientRecList);
							}
						}
					}
				}
			}//for
		}
		return listMap;
	}
*/	
}
