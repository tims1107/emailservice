package com.spectra.asr.generator.context.factory;

import com.spectra.asr.generator.context.hl7.state.StateHL7GeneratorContextImpl;
import com.spectra.asr.generator.context.hssf.eip.EIPHssfGeneratorContextImpl;
import com.spectra.asr.generator.context.hssf.state.StateHssfGeneratorContextImpl;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

// ** Adapter ** //
public enum GeneratorContextEnum {
	CAHL7GeneratorContext("CAHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	PAHL7GeneratorContext("PAHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	ILHL7GeneratorContext("ILHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	NMHL7GeneratorContext("NMHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	MDHL7GeneratorContext("MDHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	// ** Adapter **
	TXHL7GeneratorContext("TXHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	ALHL7GeneratorContext("ALHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	ORHL7GeneratorContext("ORHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	NJHL7GeneratorContext("NJHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	NJCountyHL7GeneratorContext("NJCountyHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	StateHssfGeneratorContext("StateHssfGeneratorContext", StateHssfGeneratorContextImpl.class.getName()),
	CountyHssfGeneratorContext("CountyHssfGeneratorContext", StateHssfGeneratorContextImpl.class.getName()),
	CommonEIPHssfGeneratorContext("CommonEIPHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	CommonEIPStateAllHssfGeneratorContext("CommonEIPStateAllHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	CommonEIPCountyHssfGeneratorContext("CommonEIPCountyHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	MugsiEIPHssfGeneratorContext("MugsiEIPHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	MugsiEIPStateAllHssfGeneratorContext("MugsiEIPStateAllHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	AbcEIPHssfGeneratorContext("AbcEIPHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	AbcEIPStateAllHssfGeneratorContext("AbcEIPStateAllHssfGeneratorContext", EIPHssfGeneratorContextImpl.class.getName()),
	NYHL7GeneratorContext("NYHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	NYCountyHL7GeneratorContext("NYCountyHL7GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	HL7v251GeneratorContext("HL7v251GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	HL7v23GeneratorContext("HL7v23GeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	HL7v23CountyGeneratorContext("HL7v23CountyGeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	HL7v251CountyGeneratorContext("HL7v251CountyGeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	StateHssfNoaddrGeneratorContext("StateHssfNoaddrGeneratorContext", StateHssfGeneratorContextImpl.class.getName()),
	HL7v251NoaddrGeneratorContext("HL7v251NoaddrGeneratorContext", StateHL7GeneratorContextImpl.class.getName()),
	HL7v23NoaddrGeneratorContext("HL7v23NoaddrGeneratorContext", StateHL7GeneratorContextImpl.class.getName());
	
	private String ctxName;
	private Object ctx;
	
	private final Logger LOGGER = ApplicationRootLogger.getLogger(GeneratorContextEnum.class.getName());
	
	GeneratorContextEnum(final String ctxName, final String fullyQualifiedPath) {
		this.ctxName = ctxName;
		try {
			this.ctx = Class.forName(fullyQualifiedPath).newInstance();
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
	
	public static Object getCtx(final String ctxName) {
		Object object = null;
		for (GeneratorContextEnum ctxEnum : GeneratorContextEnum.values()) {
			if (ctxEnum.ctxName.equalsIgnoreCase(ctxName)) {
				object = ctxEnum.ctx;
				break;
			}
		}

		return object;
	}
}
