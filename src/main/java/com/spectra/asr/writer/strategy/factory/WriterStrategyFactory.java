package com.spectra.asr.writer.strategy.factory;

public class WriterStrategyFactory {
	private static WriterStrategyFactory writerStrategyFactory = null;
	
	private WriterStrategyFactory(){
		
	}
	
	public static synchronized WriterStrategyFactory getInstance(){
		if(writerStrategyFactory == null){
			writerStrategyFactory = new WriterStrategyFactory();
		}
		return writerStrategyFactory;
	}
	
	public static Object getStrategyImpl(final String strategyName){
		Object strategy = null;
		if(strategyName != null){
			strategy = WriterStrategyEnum.getStrategy(strategyName);
		}
		return strategy;
	}
}
