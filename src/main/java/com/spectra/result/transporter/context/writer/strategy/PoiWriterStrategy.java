package com.spectra.result.transporter.context.writer.strategy;

import org.apache.poi.ss.usermodel.Workbook;

public interface PoiWriterStrategy extends WriterStrategy<Workbook> {
	Boolean write(Workbook resultBook);
	Boolean write(boolean hasDemo, Workbook resultBook);
	Boolean write(boolean hasDemo, String loc, Workbook resultBook);
	Boolean write(boolean hasDemo, String loc, String patientName, Workbook resultBook);
}
