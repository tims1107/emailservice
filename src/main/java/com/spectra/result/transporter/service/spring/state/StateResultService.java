package com.spectra.result.transporter.service.spring.state;

import com.spectra.result.transporter.context.factory.ConversionContextFactory;
import com.spectra.result.transporter.context.ConversionContext;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import com.spectra.result.transporter.service.spring.rr.RepositoryService;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.writer.factory.WriterContextFactory;

public interface StateResultService {
	void setConfigService(ConfigService configService);
	void setConversionContextFactory(ConversionContextFactory conversionContextFactory);
	void setResultSchedulerServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory);
	void setWriterContextFactory(WriterContextFactory writerContextFactory);
	boolean process();
}
