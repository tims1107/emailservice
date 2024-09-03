package com.spectra.result.transporter.context.writer.strategy;

import java.util.List;

public interface WriterStrategy<T extends Object> {
	Boolean write(T resultStr);
	Boolean write(String county, T resultStr);
}
