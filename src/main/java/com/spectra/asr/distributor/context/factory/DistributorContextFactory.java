package com.spectra.asr.distributor.context.factory;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.distributor.context.DistributorContext;
import com.spectra.asr.distributor.strategy.DistributorStrategy;
import com.spectra.asr.distributor.strategy.factory.DistributorStrategyFactory;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public final class DistributorContextFactory {
	//private static Logger log = Logger.getLogger(DistributorContextFactory.class);
	
	private static DistributorContextFactory distributorContextFactory = null;
	
	private DistributorContextFactory(){
		
	}
	
	public static synchronized DistributorContextFactory getInstance() {
		if(distributorContextFactory == null){
			distributorContextFactory = new DistributorContextFactory();
		}
		return distributorContextFactory;
	}
	
	//public static Object getContextImpl(final String ctxName){
	public static Object getContextImpl(final DistributorDto distributorDto){
		Object ctx = null;
		if(distributorDto != null){
			String ctxName = distributorDto.getDistributionContext();
			if(ctxName != null){
				log.warn("getContextImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
				ctx = DistributorContextEnum.getCtx(ctxName);
				log.warn("getContextImpl(): ctx: " + (ctx == null ? "NULL" : ctx));
				
				GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
				GeneratorDto generatorDto = new GeneratorDto();
				generatorDto.setGeneratorPk(distributorDto.getGeneratorFk());
				generatorDto.setStateFk(distributorDto.getStateFk());
				generatorDto.setState(distributorDto.getState());
				generatorDto.setStateAbbreviation(distributorDto.getStateAbbreviation());
				generatorDto.setStatus(distributorDto.getStatus());
				try{
					List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
					if(generatorDtoList != null){
						generatorDto = generatorDtoList.get(0);
					}
				}catch(BusinessException be){
					log.error(String.valueOf(be));
					be.printStackTrace();
				}
				
				((DistributorContext<Boolean>)ctx).setGeneratorDto(generatorDto);
				/*
				String strategyName = distributorDto.getWriterStrategy();
				if(strategyName == null){
					strategyName = ctxName.substring(0, (ctxName.indexOf("Context")));
					strategyName += "Strategy";
				}
				*/
				String strategyName = ctxName.substring(0, (ctxName.indexOf("Context")));
				strategyName += "Strategy";
				Object strategy = DistributorStrategyFactory.getStrategyImpl(strategyName);
				if(strategy != null){
					((DistributorStrategy<Boolean>)strategy).setGeneratorDto(generatorDto);
					((DistributorContext)ctx).setStrategy((DistributorStrategy<Boolean>)strategy);
				}
			}
		}
		return ctx;
	}
}
