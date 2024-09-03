package com.spectra.asr.distributor.strategy;

import com.spectra.asr.dto.generator.GeneratorDto;

public interface DistributorStrategy<X> {
	void setFileName(String fileName);
	void setGeneratorDto(GeneratorDto generatorDto);
	X distribute();
}
