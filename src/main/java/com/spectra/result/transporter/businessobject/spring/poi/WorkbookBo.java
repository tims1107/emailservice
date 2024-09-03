package com.spectra.result.transporter.businessobject.spring.poi;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;

public interface WorkbookBo {
	void setConfigService(ConfigService configService);
	Workbook toWorkbook(List<RepositoryResultDto> dtoList);
}
