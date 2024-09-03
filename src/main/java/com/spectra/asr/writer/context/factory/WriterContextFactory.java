package com.spectra.asr.writer.context.factory;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.writer.context.WriterContext;
import com.spectra.asr.writer.strategy.WriterStrategy;
import com.spectra.asr.writer.strategy.factory.WriterStrategyFactory;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public final class WriterContextFactory {
	//private static Logger log = Logger.getLogger(WriterContextFactory.class);
	
	private static WriterContextFactory writerContextFactory = null;
	
	private WriterContextFactory(){
		
	}
	
	public static synchronized WriterContextFactory getInstance() {
		if(writerContextFactory == null){
			writerContextFactory = new WriterContextFactory();
		}
		return writerContextFactory;
	}
	
	//public static Object getContextImpl(final String ctxName){
	public static Object getContextImpl(final GeneratorDto generatorDto){
		Object ctx = null;
		if(generatorDto != null){
			String ctxName = generatorDto.getWriterContext();
			if(ctxName != null){
				log.warn("getContextImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
				ctx = WriterContextEnum.getCtx(ctxName);
				log.warn("getContextImpl(): ctx: " + (ctx == null ? "NULL" : ctx));
				((WriterContext)ctx).setGeneratorDto(generatorDto);
				String strategyName = generatorDto.getWriterStrategy();
				if(strategyName == null){
					strategyName = ctxName.substring(0, (ctxName.indexOf("Context")));
					strategyName += "Strategy";
				}
				Object strategy = WriterStrategyFactory.getStrategyImpl(strategyName);
				if(strategy != null){
					((WriterStrategy)strategy).setGeneratorDto(generatorDto);
					((WriterContext)ctx).setStrategy((WriterStrategy)strategy);
				}
			}
		}
		return ctx;
	}
}
