package com.spectra.asr.distributor.context;

import com.spectra.asr.distributor.strategy.DistributorStrategy;
import com.spectra.asr.dto.generator.GeneratorDto;

public interface DistributorContext<X> {
	void setFileName(String fileName);
	void setGeneratorDto(GeneratorDto generatorDto);
	void setStrategy(DistributorStrategy<X> distributorStrategy);
	DistributorStrategy<X> getStrategy();
	X executeStrategy();
}
