package com.spectra.result.transporter.context.poi;

import com.spectra.result.transporter.context.strategy.ConversionStrategy;
import com.spectra.result.transporter.context.strategy.PoiConversionStrategy;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class PoiConversionContextImpl implements PoiConversionContext {
	//private Logger log = Logger.getLogger(PoiConversionContextImpl.class);
	
	PoiConversionStrategy conversionStrategy;
	
	/*public void setStrategy(PoiConversionStrategy conversionStrategy) {
		this.conversionStrategy = conversionStrategy;
	}*/

	@Override
	public Workbook executeStrategy(List<RepositoryResultDto> dtoList){
		Workbook wbConvert = null;
		if(dtoList != null){
			if(this.conversionStrategy != null){
				wbConvert = this.conversionStrategy.convert(dtoList);
			}
		}
		return wbConvert;
	}

	@Override
	public Workbook executeDailyReportStrategy(List<PatientRecord> dtoList){
		Workbook wbConvert = null;
		if(dtoList != null){
			if(this.conversionStrategy != null){
				wbConvert = this.conversionStrategy.convertDailyReport(dtoList);
			}
		}
		return wbConvert;
	}
	
	public void setStrategy(ConversionStrategy<Workbook> conversionStrategy) {
		this.conversionStrategy = (PoiConversionStrategy)conversionStrategy;
	}
}
