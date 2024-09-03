package com.spectra.result.transporter.context.writer;

import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class WriterContextImpl implements WriterContext<String> {
	//private Logger log = Logger.getLogger(WriterContextImpl.class);
	
	WriterStrategy writerStrategy;
	
	@Override
	public void setStrategy(WriterStrategy writerStrategy) {
		this.writerStrategy = writerStrategy;
	}

	@Override
	public Boolean executeStrategy(String resultStr){
		Boolean wrote = null;
		if(resultStr != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(resultStr);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean executeStrategy(String county, String resultStr){
		Boolean wrote = null;
		if(resultStr != null){
			if(this.writerStrategy != null){
				wrote = this.writerStrategy.write(county, resultStr);
			}
		}
		return wrote;
	}
}
