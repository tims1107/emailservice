package com.spectra.result.transporter.context.strategy.poi;

import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;
import com.spectra.result.transporter.businessobject.spring.poi.WorkbookBo;
import com.spectra.result.transporter.context.strategy.PoiConversionStrategy;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

@Slf4j
public class HSSFConversionStrategy implements PoiConversionStrategy {
	//private Logger log = Logger.getLogger(HSSFConversionStrategy.class);

	private RepositoryBoFactory repositoryBoFactory;
	
	public void setBoFactory(RepositoryBoFactory repositoryBoFactory){
		this.repositoryBoFactory = repositoryBoFactory;
	}
	
	@Override
	public Workbook convert(List<RepositoryResultDto> dtoList) {
		Workbook wb = null;
		if(dtoList != null){
			if(this.repositoryBoFactory != null){
				WorkbookBo bo = this.repositoryBoFactory.getWorkbookBo();
				if(bo != null){
					wb = bo.toWorkbook(dtoList);
				}
			}
		}
		return wb;
	}

	@Override
	public Workbook convertDailyReport(List<PatientRecord> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

	public Workbook convertDailyReport(PatientRecord patientRecord){
		return null;
	}
}
