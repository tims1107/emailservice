package com.spectra.result.transporter.context.writer.poi;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.result.transporter.context.writer.strategy.PoiWriterStrategy;
import com.spectra.result.transporter.context.writer.WriterContext;

public interface PoiWriterContext extends WriterContext<Workbook> {
	//void setStrategy(PoiWriterStrategy writerStrategy);
	
	Boolean executeStrategy(Workbook resultBook);
	Boolean executeStrategy(boolean hasDemo, Workbook resultBook);
	Boolean executeStrategy(boolean hasDemo, String loc, Workbook resultBook);
	Boolean executeStrategy(boolean hasDemo, String loc, String patientName, Workbook resultBook);
}