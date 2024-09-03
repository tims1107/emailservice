package com.spectra.asr.businessobject.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.asr.generator.strategy.hl7.state.nj.NJHL7GeneratorStrategyImpl;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;
import com.spectra.asr.businessobject.hl7.state.ny.NYHL7BoImpl;
import com.spectra.asr.businessobject.hl7.state.ny.NYHL7Bo;
import com.spectra.asr.businessobject.hl7.state.nyc.NYCHL7BoImpl;
import com.spectra.asr.businessobject.hl7.state.nyc.NYCHL7Bo;
import com.spectra.asr.businessobject.ms.cm.CMBo;
import com.spectra.asr.businessobject.ms.cm.CMBoImpl;
import com.spectra.asr.businessobject.ora.hub.condition.MicroConditionBo;
import com.spectra.asr.businessobject.ora.hub.condition.MicroConditionBoImpl;
import com.spectra.asr.businessobject.hl7.v251.HL7v251Bo;
import com.spectra.asr.businessobject.hl7.v251.HL7v251BoImpl;
import com.spectra.asr.businessobject.hl7.v23.HL7v23Bo;
import com.spectra.asr.businessobject.hl7.v23.HL7v23BoImpl;

/*
import com.spectra.asr.generator.strategy.hl7.state.county.nj.NJCountyHL7GeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.CommonEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.CommonEIPStateAllHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.common.county.CommonEIPCountyHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.mugsi.MugsiEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.mugsi.MugsiEIPStateAllHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.state.StateHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.state.county.CountyHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.abc.AbcEIPHssfGeneratorStrategyImpl;
import com.spectra.asr.generator.strategy.hssf.eip.abc.AbcEIPStateAllHssfGeneratorStrategyImpl;
*/

public enum AsrBoEnum {
	NYHL7Bo(NYHL7Bo.class.getSimpleName(),NYHL7BoImpl.class.getName()),
	NYCHL7Bo(NYCHL7Bo.class.getSimpleName(),NYCHL7BoImpl.class.getName()),
	CMBo(CMBo.class.getSimpleName(),CMBoImpl.class.getName()),
	MicroConditionBo(MicroConditionBo.class.getSimpleName(),MicroConditionBoImpl.class.getName()),
	HL7v251Bo(HL7v251Bo.class.getSimpleName(),HL7v251BoImpl.class.getName()),
	HL7v23Bo(HL7v23Bo.class.getSimpleName(),HL7v23BoImpl.class.getName());
	/*
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
	AbcEIPStateAllHssfGeneratorStrategy("AbcEIPStateAllHssfGeneratorStrategy",AbcEIPStateAllHssfGeneratorStrategyImpl.class.getName());
	*/
	
	private String boName;
	private Object bo;
	
	private final Logger LOGGER = ApplicationRootLogger.getLogger(AsrBoEnum.class.getName());
	
	AsrBoEnum(final String boName, final String fullyQualifiedPath) {
		this.boName = boName;
		try {
			this.bo = Class.forName(fullyQualifiedPath).newInstance();
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
	
	public static Object getBo(final String boName) {
		Object object = null;
		for(AsrBoEnum boEnum : AsrBoEnum.values()){
			if (boEnum.boName.equalsIgnoreCase(boName)) {
				object = boEnum.bo;
				break;
			}
		}
		return object;
	}
}
