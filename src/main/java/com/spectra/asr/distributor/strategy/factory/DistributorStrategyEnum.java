package com.spectra.asr.distributor.strategy.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;
import com.spectra.asr.distributor.strategy.local.LocalDistributorStrategyImpl;
import com.spectra.asr.distributor.strategy.local.eip.LocalEIPDistributorStrategyImpl;
import com.spectra.asr.distributor.strategy.samba.SambaDistributorStrategyImpl;
import com.spectra.asr.writer.strategy.hl7.HL7WriterStrategyImpl;
import com.spectra.asr.writer.strategy.hssf.HssfWriterStrategyImpl;
import com.spectra.asr.writer.strategy.hssf.daily.DailyHssfWriterStrategyImpl;

public enum DistributorStrategyEnum {
	SambaDistributorStrategy("SambaDistributorStrategy",SambaDistributorStrategyImpl.class.getName()),
	LocalDistributorStrategy("LocalDistributorStrategy",LocalDistributorStrategyImpl.class.getName()),
	LocalEIPDistributorStrategy("LocalEIPDistributorStrategy",LocalEIPDistributorStrategyImpl.class.getName());
	
	private String strategyName;
	private Object strategy;
	
	private final Logger LOGGER = ApplicationRootLogger.getLogger(DistributorStrategyEnum.class.getName());
	
	DistributorStrategyEnum(final String strategyName, final String fullyQualifiedPath) {
		this.strategyName = strategyName;
		try {
			this.strategy = Class.forName(fullyQualifiedPath).newInstance();
		} catch (InstantiationException instantiationException) {
			final StringWriter stringWriter = new StringWriter();
			instantiationException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		} catch (IllegalAccessException illegalAccessException) {
			final StringWriter stringWriter = new StringWriter();
			illegalAccessException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		} catch (ClassNotFoundException classNotFoundException) {
			final StringWriter stringWriter = new StringWriter();
			classNotFoundException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		}
	}
	
	public static Object getStrategy(final String strategyName) {
		Object object = null;
		for(DistributorStrategyEnum strategyEnum : DistributorStrategyEnum.values()){
			if (strategyEnum.strategyName.equalsIgnoreCase(strategyName)) {
				object = strategyEnum.strategy;
				break;
			}
		}
		return object;
	}
}
