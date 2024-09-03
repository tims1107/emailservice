package com.spectra.result.transporter.context.writer.poi;

import com.spectra.result.transporter.context.writer.strategy.PoiWriterStrategy;
import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class PoiWriterContextImpl implements PoiWriterContext {
	//private Logger log = Logger.getLogger(PoiWriterContextImpl.class);
	
	PoiWriterStrategy writerStrategy;
	
	/*
	@Override
	public void setStrategy(PoiWriterStrategy writerStrategy) {
		this.writerStrategy = writerStrategy;
	}
	*/

	@Override
	public Boolean executeStrategy(Workbook resultBook){
		Boolean wrote = null;
		if(resultBook != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(resultBook);
			}
		}
		return wrote;
	}

	@Override
	public Boolean executeStrategy(boolean hasDemo, Workbook resultBook){
		Boolean wrote = null;
		if(resultBook != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(hasDemo, resultBook);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean executeStrategy(boolean hasDemo, String loc, Workbook resultBook){
		Boolean wrote = null;
		if(resultBook != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(hasDemo, loc, resultBook);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean executeStrategy(boolean hasDemo, String loc, String patientName, Workbook resultBook){
		Boolean wrote = null;
		if(resultBook != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(hasDemo, loc, patientName, resultBook);
			}
		}
		return wrote;
	}

	@Override
	public void setStrategy(WriterStrategy<Workbook> writerStrategy) {
		this.writerStrategy = (PoiWriterStrategy)writerStrategy;
		
	}

	public Boolean executeStrategy(String county, Workbook resultStr) {
		// TODO Auto-generated method stub
		return null;
	}
}
