package com.spectra.result.transporter.service.spring;

import com.spectra.result.transporter.service.file.ResultFileTransportService;
import com.spectra.result.transporter.service.file.ResultFileTransportServiceImpl;
import com.spectra.result.transporter.service.spring.executor.WinCmdExecutorService;
import com.spectra.result.transporter.service.spring.executor.WinCmdExecutorServiceImpl;
import com.spectra.result.transporter.service.spring.rr.RepositoryService;
import com.spectra.result.transporter.service.spring.rr.RepositoryServiceImpl;
import com.spectra.result.transporter.service.spring.state.StateResultService;
import com.spectra.result.transporter.service.spring.state.StateResultServiceImpl;
import com.spectra.result.transporter.service.spring.state.eip.StateEipResultService;
import com.spectra.result.transporter.service.spring.state.eip.StateEipResultServiceImpl;
import com.spectra.result.transporter.service.spring.state.eip.daily.DailyEipResultService;
import com.spectra.result.transporter.service.spring.state.eip.daily.DailyEipResultServiceImpl;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import org.springframework.web.context.WebApplicationContext;
//import com.spectra.physician.master.constants.PhysicianMasterConstants;
/*
//import com.spectra.ala.processor.ui.lookup.LookupProcessor;
//import com.spectra.test.master.processor.search.SearchProcessor;
import com.spectra.test.master.service.ui.test.definition.TestDefinitionService;
import com.spectra.test.master.service.ui.test.definition.TestDefinitionServiceImpl;
import com.spectra.test.master.service.ui.UIServiceImpl;
import com.spectra.test.master.service.lucene.test.LuceneTestDtoService;
import com.spectra.test.master.service.lucene.test.LuceneTestDtoServiceImpl;
import com.spectra.test.master.service.lucene.test.definition.LuceneTestDefinitionDtoService;
import com.spectra.test.master.service.lucene.test.definition.LuceneTestDefinitionDtoServiceImpl;
import com.spectra.test.master.service.ui.common.sequence.CommonSequencesService;
import com.spectra.test.master.service.ui.common.sequence.CommonSequencesServiceImpl;
import com.spectra.test.master.service.ui.common.sequence.CommonSequenceService;
import com.spectra.test.master.service.ui.common.sequence.CommonSequenceServiceImpl;
import com.spectra.test.master.service.ui.common.sequence.CommonSequenceMicroService;
import com.spectra.test.master.service.ui.common.sequence.CommonSequenceMicroServiceImpl;
import com.spectra.test.master.service.ui.loinc.TmLoincService;
import com.spectra.test.master.service.ui.loinc.TmLoincServiceImpl;
import com.spectra.test.master.service.ui.snomed.SnomedService;
import com.spectra.test.master.service.ui.snomed.SnomedServiceImpl;
import com.spectra.test.master.service.ui.term.TmTermService;
import com.spectra.test.master.service.ui.term.TmTermServiceImpl;
*/
//import com.spectra.physician.master.service.spring.provider.ProviderService;
//import com.spectra.physician.master.service.spring.provider.ProviderServiceImpl;

@Slf4j
public enum ResultSchedulerServiceEnum {
	/*
	UIProcessor(TMConstants.UI_PROCESSOR, TMConstants.UI_PROCESSOR_CLASS),
	MnemonicProcessor(TMConstants.MNEMONIC_PROCESSOR, TMConstants.MNEMONIC_PROCESSOR_CLASS),
	QuestionProcessor(TMConstants.QUESTION_PROCESSOR, TMConstants.QUESTION_PROCESSOR_CLASS),
	TESTREQ_PROCESSOR(TMConstants.TESTREQ_PROCESSOR, TMConstants.TESTREQ_PROCESSOR_CLASS),
	SOURCE_PROCESSOR(TMConstants.SOURCE_PROCESSOR, TMConstants.SOURCE_PROCESSOR_CLASS),
	TEST_PROCESSOR(TMConstants.TEST_PROCESSOR, TMConstants.TEST_PROCESSOR_CLASS),
	LOOKUP_PROCESSOR(LookupProcessor.class.getSimpleName(), LookupProcessor.class.getName());
	*/
	/*
	TestDefinitionService(TestDefinitionService.class.getSimpleName(), TestDefinitionServiceImpl.class.getName()),
	CommonSequencesService(CommonSequencesService.class.getSimpleName(), CommonSequencesServiceImpl.class.getName()),
	CommonSequenceService(CommonSequenceService.class.getSimpleName(), CommonSequenceServiceImpl.class.getName()),
	CommonSequenceMicroService(CommonSequenceMicroService.class.getSimpleName(), CommonSequenceMicroServiceImpl.class.getName()),
	TmLoincService(TmLoincService.class.getSimpleName(), TmLoincServiceImpl.class.getName()),
	SnomedService(SnomedService.class.getSimpleName(), SnomedServiceImpl.class.getName()),
	TmTermService(TmTermService.class.getSimpleName(), TmTermServiceImpl.class.getName());
	*/
	
	//ProviderService(ProviderService.class.getSimpleName(), ProviderServiceImpl.class.getName());
	ResultFileTransportService(ResultFileTransportService.class.getSimpleName(), ResultFileTransportServiceImpl.class.getName()),
	RepositoryService(RepositoryService.class.getSimpleName(), RepositoryServiceImpl.class.getName()),
	StateResultService(StateResultService.class.getSimpleName(), StateResultServiceImpl.class.getName()),
	WinCmdExecutorService(WinCmdExecutorService.class.getSimpleName(), WinCmdExecutorServiceImpl.class.getName()),
	StateEipResultService(StateEipResultService.class.getSimpleName(), StateEipResultServiceImpl.class.getName()),
	DailyEipResultService(DailyEipResultService.class.getSimpleName(), DailyEipResultServiceImpl.class.getName());
	
	//private static Logger log = Logger.getLogger(ResultSchedulerServiceEnum.class);
	
	private String serviceName;
	private SpringServiceImpl serviceObj;
	
	public String getServiceName(){
		return serviceName;
	}
	
	private ResultSchedulerServiceEnum(final String serviceName, final String fullyQualifiedPath) {
		this.serviceName = serviceName;
		try {
			this.serviceObj = (SpringServiceImpl) Class.forName(fullyQualifiedPath).newInstance();
		} catch (InstantiationException instantiationException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("serviceName", serviceName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					instantiationException, hashMap);
		} catch (IllegalAccessException illegalAccessException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("serviceName", serviceName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					illegalAccessException, hashMap);
		} catch (ClassNotFoundException classNotFoundException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("serviceName", serviceName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					classNotFoundException, hashMap);
		}
	}
	
	public static SpringServiceImpl getService(final String serviceName) {
		log.debug("getService(): serviceName: " + (serviceName == null ? "NULL" : serviceName));
		SpringServiceImpl uiService = null;
		for (ResultSchedulerServiceEnum serviceEnum : ResultSchedulerServiceEnum.values()) {
			if (serviceEnum.serviceName.equalsIgnoreCase(serviceName)) {
				return serviceEnum.serviceObj;
				//WebApplicationContext wc = (WebApplicationContext)request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
				//processor = (Processor)wc.getBean(serviceName);
			}
		}
		return uiService;
	}
}
