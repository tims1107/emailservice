package com.spectra.result.transporter.context;

import com.spectra.result.transporter.context.strategy.ConversionStrategy;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ConversionContextImpl implements ConversionContext<String> {
	//private Logger log = Logger.getLogger(ConversionContextImpl.class);
	
	ConversionStrategy<String> conversionStrategy;
	
	@Override
	public void setStrategy(ConversionStrategy<String> conversionStrategy) {
		this.conversionStrategy = conversionStrategy;
	}

	@Override
	public String executeStrategy(List<RepositoryResultDto> dtoList){
		String strConvert = null;
		if(dtoList != null){
			if(this.conversionStrategy != null){
				strConvert = this.conversionStrategy.convert(dtoList);
			}
		}
		return strConvert;
	}
}
