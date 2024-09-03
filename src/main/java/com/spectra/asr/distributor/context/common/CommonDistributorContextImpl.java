package com.spectra.asr.distributor.context.common;

import com.spectra.asr.distributor.context.DistributorContext;
import com.spectra.asr.distributor.strategy.DistributorStrategy;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.generator.GeneratorDto;

public class CommonDistributorContextImpl implements DistributorContext<Boolean> {
	protected String fileName;
	protected GeneratorDto generatorDto;
	protected DistributorStrategy<Boolean> distributorStrategy;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public GeneratorDto getGeneratorDto() {
		return generatorDto;
	}
	public void setGeneratorDto(GeneratorDto generatorDto) {
		this.generatorDto = generatorDto;
	}
	public DistributorStrategy<Boolean> getStrategy() {
		return distributorStrategy;
	}
	public void setStrategy(DistributorStrategy<Boolean> distributorStrategy) {
		this.distributorStrategy = distributorStrategy;
	}
	
	public Boolean executeStrategy(){
		Boolean distributed = null;
		if(this.generatorDto != null){
			if(this.distributorStrategy != null){
				this.distributorStrategy.setGeneratorDto(this.generatorDto);
				//if(this.fileName != null){
					this.distributorStrategy.setFileName(fileName);
					distributed = this.distributorStrategy.distribute();
				//}
			}
		}
		return distributed;
	}


}
