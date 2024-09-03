package com.spectra.asr.writer.context;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.writer.strategy.WriterStrategy;

public interface WriterContext<X, Y> {
	void setStrategy(WriterStrategy<X, Y> writerStrategy);
	WriterStrategy<X, Y> getStrategy();
	void setGeneratorDto(GeneratorDto generatorDto);
	X executeStrategy(Y content);
	void setFileName(String fileName);
}
