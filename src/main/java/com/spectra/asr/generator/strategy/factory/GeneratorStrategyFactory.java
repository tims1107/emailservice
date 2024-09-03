package com.spectra.asr.generator.strategy.factory;

public class GeneratorStrategyFactory {
	private static GeneratorStrategyFactory generatorStrategyFactory = null;
	
	private GeneratorStrategyFactory(){
		
	}
	
	public static synchronized GeneratorStrategyFactory getInstance(){
		if(generatorStrategyFactory == null){
			generatorStrategyFactory = new GeneratorStrategyFactory();
		}
		return generatorStrategyFactory;
	}
	
	public static Object getStrategyImpl(final String strategyName){
		Object strategy = null;
		if(strategyName != null){
			strategy = GeneratorStrategyEnum.getStrategy(strategyName);
		}
		return strategy;
	}
}
