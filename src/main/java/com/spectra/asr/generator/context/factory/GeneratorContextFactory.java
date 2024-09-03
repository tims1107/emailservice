package com.spectra.asr.generator.context.factory;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import com.spectra.asr.generator.strategy.factory.GeneratorStrategyFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class GeneratorContextFactory {
	//private static Logger log = Logger.getLogger(GeneratorContextFactory.class);
	
	private static GeneratorContextFactory generatorContextFactory = null;
	
	private GeneratorContextFactory(){
		
	}
	
	public static synchronized GeneratorContextFactory getInstance() {
		if(generatorContextFactory == null){
			generatorContextFactory = new GeneratorContextFactory();
		}
		return generatorContextFactory;
	}
	
	//public static Object getContextImpl(final String ctxName){
	public static Object getContextImpl(final GeneratorDto generatorDto){
		log.warn("getContextImpl(): generatorDto: " + (generatorDto.getEastWestFlag() == null ? "NULL" : generatorDto.getEastWestFlag()));
		Object ctx = null;
		if(generatorDto != null){
			String ctxName = generatorDto.getConversionContext();
			if(ctxName != null){
				log.warn("getContextImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
				ctx = GeneratorContextEnum.getCtx(ctxName);
				log.warn("getContextImpl(): ctx: " + (ctx == null ? "NULL" : ctx));
				((GeneratorContext)ctx).setGenerator(generatorDto);
				String strategyName = generatorDto.getConversionStrategy();
				if(strategyName == null){
					strategyName = ctxName.substring(0, (ctxName.indexOf("Context")));
					strategyName += "Strategy";
				}
				Object strategy = GeneratorStrategyFactory.getStrategyImpl(strategyName);
				if(strategy != null){
					((GeneratorStrategy)strategy).setGenerator(generatorDto);
					((GeneratorContext)ctx).setStrategy((GeneratorStrategy)strategy);
				}
			}
		}
		return ctx;
	}
}
