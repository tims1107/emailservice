package com.spectra.result.transporter.businessobject.spring.poi;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dataaccess.spring.ora.rr.RepositoryDAOFactory;

public interface DailyReportWorkbookBo extends WorkbookBo {
	void setDaoFactory(RepositoryDAOFactory repositoryDAOFactory);
	Workbook toWorkbook(List<RepositoryResultDto> dtoList);
	Workbook toWorkbookDailyReport(List<PatientRecord> dtoList);
}
