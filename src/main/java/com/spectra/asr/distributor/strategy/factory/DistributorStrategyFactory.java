package com.spectra.asr.distributor.strategy.factory;

public class DistributorStrategyFactory {
	private static DistributorStrategyFactory distributorStrategyFactory = null;
	
	private DistributorStrategyFactory(){
		
	}
	
	public static synchronized DistributorStrategyFactory getInstance(){
		if(distributorStrategyFactory == null){
			distributorStrategyFactory = new DistributorStrategyFactory();
		}
		return distributorStrategyFactory;
	}
	
	public static Object getStrategyImpl(final String strategyName){
		Object strategy = null;
		if(strategyName != null){
			strategy = DistributorStrategyEnum.getStrategy(strategyName);
		}
		return strategy;
	}
}
