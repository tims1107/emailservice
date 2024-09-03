package com.spectra.asr.writer.context.hssf.county;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.asr.writer.context.hssf.HssfWriterContextImpl;

public class CountyHssfWriterContextImpl extends HssfWriterContextImpl {
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public Boolean executeStrategy(Workbook content) {
		Boolean isWritten = null;
		if(content != null){
			if(this.fileName != null){
				this.writerStrategy.setFileName(this.fileName);
				isWritten = this.writerStrategy.write(content);
			}
		}
		return isWritten;
	}
}
