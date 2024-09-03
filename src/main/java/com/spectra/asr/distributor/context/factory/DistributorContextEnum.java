package com.spectra.asr.distributor.context.factory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;
import com.spectra.asr.distributor.context.common.CommonDistributorContextImpl;
import com.spectra.asr.writer.context.hl7.HL7WriterContextImpl;
import com.spectra.asr.writer.context.hssf.HssfWriterContextImpl;
import com.spectra.asr.writer.context.hssf.county.CountyHssfWriterContextImpl;

public enum DistributorContextEnum {
	SambaDistributorContext("SambaDistributorContext",CommonDistributorContextImpl.class.getName()),
	LocalDistributorContext("LocalDistributorContext",CommonDistributorContextImpl.class.getName()),
	LocalEIPDistributorContext("LocalEIPDistributorContext",CommonDistributorContextImpl.class.getName());
	
	private String ctxName;
	private Object ctx;
	
	private final Logger LOGGER = ApplicationRootLogger.getLogger(DistributorContextEnum.class.getName());
	
	DistributorContextEnum(final String ctxName, final String fullyQualifiedPath) {
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
		for (DistributorContextEnum ctxEnum : DistributorContextEnum.values()) {
			if (ctxEnum.ctxName.equalsIgnoreCase(ctxName)) {
				object = ctxEnum.ctx;
				break;
			}
		}
		return object;
	}
}
