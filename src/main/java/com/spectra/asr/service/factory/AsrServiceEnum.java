package com.spectra.asr.service.factory;

import com.spectra.asr.dev.service.factory.LoadServiceEnum;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;

import static com.spectra.asr.utils.file.writer.FileUtil.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.asr.service.entity.EntityService;
import com.spectra.asr.service.entity.EntityServiceImpl;
import com.spectra.asr.service.condition.ConditionService;
import com.spectra.asr.service.condition.ConditionServiceImpl;
import com.spectra.asr.service.generator.GeneratorService;
import com.spectra.asr.service.generator.GeneratorServiceImpl;
import com.spectra.asr.service.abnormal.AbnormalLocalService;
import com.spectra.asr.service.abnormal.AbnormalLocalServiceImpl;
import com.spectra.asr.service.abnormal.AbnormalHL7LocalServiceImpl;
import com.spectra.asr.service.eip.EipLocalService;
import com.spectra.asr.service.eip.EipLocalServiceImpl;
import com.spectra.asr.service.email.EmailService;
import com.spectra.asr.service.email.EmailServiceImpl;
import com.spectra.asr.service.notification.AbnormalResultsNotificationService;
import com.spectra.asr.service.notification.AbnormalResultsNotificationServiceImpl;
import com.spectra.asr.service.audit.AsrAuditService;
import com.spectra.asr.service.audit.AsrAuditServiceImpl;
import com.spectra.asr.service.distributor.DistributorService;
import com.spectra.asr.service.distributor.DistributorServiceImpl;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.demographic.AsrDemographicServiceImpl;
import com.spectra.asr.service.eip.extract.EipResultsExtractService;
import com.spectra.asr.service.eip.extract.EipResultsExtractServiceImpl;
import com.spectra.asr.service.ms.cm.CMService;
import com.spectra.asr.service.ms.cm.CMServiceImpl;
import com.spectra.asr.service.notification.NoDemoNotificationService;
import com.spectra.asr.service.notification.NoDemoNotificationServiceImpl;
import com.spectra.asr.service.condition.MicroConditionService;
import com.spectra.asr.service.condition.MicroConditionServiceImpl;
import com.spectra.asr.service.abnormal.noaddr.AbnormalHL7NoaddrLocalServiceImpl;
import com.spectra.asr.service.abnormal.noaddr.AbnormalNoaddrLocalService;
import com.spectra.asr.service.abnormal.noaddr.AbnormalNoaddrLocalServiceImpl;

public enum AsrServiceEnum {

	/**
	 * Constants for the individual Service implementation.
	 */
	EntityService(EntityService.class.getSimpleName(), EntityServiceImpl.class.getName()),
	ConditionService(ConditionService.class.getSimpleName(), ConditionServiceImpl.class.getName()),
	GeneratorService(GeneratorService.class.getSimpleName(), GeneratorServiceImpl.class.getName()),
	AbnormalLocalService(AbnormalLocalService.class.getSimpleName(), AbnormalLocalServiceImpl.class.getName()),
	AbnormalHL7LocalService("AbnormalHL7LocalService", AbnormalHL7LocalServiceImpl.class.getName()),
	EipLocalService(EipLocalService.class.getSimpleName(), EipLocalServiceImpl.class.getName()),
	EmailService(EmailService.class.getSimpleName(), EmailServiceImpl.class.getName()),
	AsrAuditService(AsrAuditService.class.getSimpleName(), AsrAuditServiceImpl.class.getName()),
	AbnormalResultsNotificationService(AbnormalResultsNotificationService.class.getSimpleName(), AbnormalResultsNotificationServiceImpl.class.getName()),
	DistributorService(DistributorService.class.getSimpleName(), DistributorServiceImpl.class.getName()),
	AsrDemographicService(AsrDemographicService.class.getSimpleName(), AsrDemographicServiceImpl.class.getName()),
	EipResultsExtractService(EipResultsExtractService.class.getSimpleName(), EipResultsExtractServiceImpl.class.getName()),
	CMService(CMService.class.getSimpleName(), CMServiceImpl.class.getName()),
	NoDemoNotificationService(NoDemoNotificationService.class.getSimpleName(), NoDemoNotificationServiceImpl.class.getName()),
	MicroConditionService(MicroConditionService.class.getSimpleName(), MicroConditionServiceImpl.class.getName()),
	AbnormalNoaddrLocalService(AbnormalNoaddrLocalService.class.getSimpleName(), AbnormalNoaddrLocalServiceImpl.class.getName()),
	AbnormalHL7NoaddrLocalService("AbnormalHL7NoaddrLocalService", AbnormalHL7NoaddrLocalServiceImpl.class.getName());
             
	private String serviceName;


	private Object service;

	private final Logger LOGGER = ApplicationRootLogger
           .getLogger(AsrServiceEnum.class.getName());

	AsrServiceEnum(final String serviceName, final String fullyQualifiedPath) {
		this.serviceName = serviceName;
		try {
			this.service = Class.forName(fullyQualifiedPath).newInstance();
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

	// Call Factory Enum to instantiate class implementation.

	public static Object getService(final String serviceName) {
		


		AsrServiceEnum obj = Arrays.asList(AsrServiceEnum.values())
				.stream()
				.filter(i -> i.serviceName.equalsIgnoreCase(serviceName))
				.findFirst()
				.orElseGet(() -> null);

//		for (AsrServiceEnum serviceEnum : AsrServiceEnum.values()) {
//			if (serviceEnum.serviceName.equalsIgnoreCase(serviceName)) {
//				object = serviceEnum.service;
//				break;
//			}
////		}



		return obj.service;
	}

}
