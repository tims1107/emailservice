package com.spectra.asr.generator.strategy.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.asr.generator.strategy.hl7.state.nj.NJHL7GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hl7.v251.*;
import com.spectra.asr.generator.strategy.hl7.v251.noaddr.HL7v251NoaddrGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hl7.v23.HL7v23GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hl7.v23.noaddr.HL7v23NoaddrGeneratorStrategyImpl;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;
import com.spectra.asr.generator.strategy.hl7.state.common.CommonCountyHL7GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hl7.state.common.CommonHL7GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hl7.state.county.nj.NJCountyHL7GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.CommonEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.CommonEIPStateAllHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.county.CommonEIPCountyHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.mugsi.MugsiEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.mugsi.MugsiEIPStateAllHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.state.StateHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.state.county.CountyHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.state.noaddr.StateHssfNoaddrGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.abc.AbcEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.abc.AbcEIPStateAllHssfGeneratorStrategyImpl;

// ** adapter **

public enum GeneratorStrategyEnum {

	CAHL7GeneratorStrategy("CAHL7GeneratorStrategy", CAHL7GeneratorStrategyImpl.class.getName()),
	PAHL7GeneratorStrategy("PAHL7GeneratorStrategy", PAHL7GeneratorStrategyImpl.class.getName()),
	ORHL7GeneratorStrategy("ORHL7GeneratorStrategy", ORHL7GeneratorStrategyImpl.class.getName()),
	ILHL7GeneratorStrategy("ILHL7GeneratorStrategy", ILHL7GeneratorStrategyImpl.class.getName()),
	NMHL7GeneratorStrategy("NMHL7GeneratorStrategy", NMHL7GeneratorStrategyImpl.class.getName()),
	MDHL7GeneratorStrategy("MDHL7GeneratorStrategy", MDHL7GeneratorStrategyImpl.class.getName()),
	// ** add code **
	ALHL7GeneratorStrategy("ALHL7GeneratorStrategy", ALHL7GeneratorStrategyImpl.class.getName()),
	TXHL7GeneratorStrategy("TXHL7GeneratorStrategy", TXHL7GeneratorStrategyImpl.class.getName()),
	NJHL7GeneratorStrategy("NJHL7GeneratorStrategy",NJHL7GeneratorStrategyImpl.class.getName()),
	NJCountyHL7GeneratorStrategy("NJCountyHL7GeneratorStrategy",NJCountyHL7GeneratorStrategyImpl.class.getName()),
	StateHssfGeneratorStrategy("StateHssfGeneratorStrategy",StateHssfGeneratorStrategyImpl.class.getName()),
	CountyHssfGeneratorStrategy("CountyHssfGeneratorStrategy",CountyHssfGeneratorStrategyImpl.class.getName()),
	CommonEIPHssfGeneratorStrategy("CommonEIPHssfGeneratorStrategy",CommonEIPHssfGeneratorStrategyImpl.class.getName()),
	CommonEIPStateAllHssfGeneratorStrategy("CommonEIPStateAllHssfGeneratorStrategy",CommonEIPStateAllHssfGeneratorStrategyImpl.class.getName()),
	CommonEIPCountyHssfGeneratorStrategy("CommonEIPCountyHssfGeneratorStrategy",CommonEIPCountyHssfGeneratorStrategyImpl.class.getName()),
	MugsiEIPHssfGeneratorStrategy("MugsiEIPHssfGeneratorStrategy",MugsiEIPHssfGeneratorStrategyImpl.class.getName()),
	MugsiEIPStateAllHssfGeneratorStrategy("MugsiEIPStateAllHssfGeneratorStrategy",MugsiEIPStateAllHssfGeneratorStrategyImpl.class.getName()),
	AbcEIPHssfGeneratorStrategy("AbcEIPHssfGeneratorStrategy",AbcEIPHssfGeneratorStrategyImpl.class.getName()),
	AbcEIPStateAllHssfGeneratorStrategy("AbcEIPStateAllHssfGeneratorStrategy",AbcEIPStateAllHssfGeneratorStrategyImpl.class.getName()),
	NYHL7GeneratorStrategy("NYHL7GeneratorStrategy",CommonHL7GeneratorStrategyImpl.class.getName()),
	NYCountyHL7GeneratorStrategy("NYCountyHL7GeneratorStrategy",CommonCountyHL7GeneratorStrategyImpl.class.getName()),
	HL7v251GeneratorStrategy("HL7v251GeneratorStrategy",HL7v251GeneratorStrategyImpl.class.getName()),
	HL7v23GeneratorStrategy("HL7v23GeneratorStrategy",HL7v23GeneratorStrategyImpl.class.getName()),
	HL7v23CountyGeneratorStrategy("HL7v23CountyGeneratorStrategy",HL7v23GeneratorStrategyImpl.class.getName()),
	HL7v251CountyGeneratorStrategy("HL7v251CountyGeneratorStrategy",HL7v251GeneratorStrategyImpl.class.getName()),
	StateHssfNoaddrGeneratorStrategy("StateHssfNoaddrGeneratorStrategy",StateHssfNoaddrGeneratorStrategyImpl.class.getName()),
	HL7v251NoaddrGeneratorStrategy("HL7v251NoaddrGeneratorStrategy",HL7v251NoaddrGeneratorStrategyImpl.class.getName()),
	HL7v23NoaddrGeneratorStrategy("HL7v23NoaddrGeneratorStrategy",HL7v23NoaddrGeneratorStrategyImpl.class.getName());

	private String strategyName;
	private Object strategy;

	private final Logger LOGGER = ApplicationRootLogger.getLogger(GeneratorStrategyEnum.class.getName());

	GeneratorStrategyEnum(final String strategyName, final String fullyQualifiedPath) {
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
		for(GeneratorStrategyEnum strategyEnum : GeneratorStrategyEnum.values()){
			if (strategyEnum.strategyName.equalsIgnoreCase(strategyName)) {
				object = strategyEnum.strategy;
				break;
			}
		}
		return object;
	}
}
