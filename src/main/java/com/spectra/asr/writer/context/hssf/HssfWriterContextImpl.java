package com.spectra.asr.writer.context.hssf;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
@Slf4j
public class HssfWriterContextImpl implements WriterContext<Boolean, Workbook> {
	//private Logger log = Logger.getLogger(HssfWriterContextImpl.class);
	
	protected WriterStrategy<Boolean, Workbook> writerStrategy;
	protected GeneratorDto generatorDto;
	protected String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void setStrategy(WriterStrategy<Boolean, Workbook> writerStrategy) {
		this.writerStrategy = writerStrategy;
		
	}

	@Override
	public WriterStrategy<Boolean, Workbook> getStrategy() {
		return this.writerStrategy;
	}

	@Override
	public void setGeneratorDto(GeneratorDto generatorDto) {
		this.generatorDto = generatorDto;
		
	}

	@Override
	public Boolean executeStrategy(Workbook content) {
		Boolean isWritten = null;
		if(content != null){
			if(this.generatorDto != null){
				if(this.fileName != null){
					this.writerStrategy.setFileName(fileName);
					isWritten = this.writerStrategy.write(content);
				}
			}
		}
		return isWritten;
	}

}
