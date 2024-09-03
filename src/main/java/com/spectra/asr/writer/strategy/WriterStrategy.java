package com.spectra.asr.writer.strategy;

import com.spectra.asr.dto.generator.GeneratorDto;

public interface WriterStrategy<X, Y> {
	void setGeneratorDto(GeneratorDto generatorDto);
	X write(Y content);
	void setFileName(String fileName);
}
