package com.spectra.asr.writer.context.hl7;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HL7WriterContextImpl implements WriterContext<Boolean, HL7Dto> {
	//private Logger log = Logger.getLogger(HL7WriterContextImpl.class);
	
	protected WriterStrategy<Boolean, HL7Dto> writerStrategy;
	protected GeneratorDto generatorDto;
	protected String fileName;
	
	public WriterStrategy<Boolean, HL7Dto> getStrategy() {
		return writerStrategy;
	}
	public void setStrategy(WriterStrategy<Boolean, HL7Dto> writerStrategy) {
		this.writerStrategy = writerStrategy;
	}
	public GeneratorDto getGeneratorDto() {
		return generatorDto;
	}
	public void setGeneratorDto(GeneratorDto generatorDto) {
		this.generatorDto = generatorDto;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Boolean executeStrategy(HL7Dto content){
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
