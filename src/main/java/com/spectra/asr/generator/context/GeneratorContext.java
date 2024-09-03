package com.spectra.asr.generator.context;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.generator.strategy.GeneratorStrategy;

public interface GeneratorContext<X, Y> {
	void setStrategy(GeneratorStrategy<X, Y> generatorStrategy);
	GeneratorStrategy<X, Y> getStrategy();
	void setGenerator(GeneratorDto generatorDto);
	Map<String, List<X>> executeStrategy(List<Y> dtoList);
}
