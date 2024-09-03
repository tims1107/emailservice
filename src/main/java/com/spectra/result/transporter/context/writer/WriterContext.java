package com.spectra.result.transporter.context.writer;

import java.util.List;

import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;

public interface WriterContext<T extends Object> {
	void setStrategy(WriterStrategy<T> writerStrategy);
	
	Boolean executeStrategy(T resultStr);
	Boolean executeStrategy(String county, T resultStr);
}