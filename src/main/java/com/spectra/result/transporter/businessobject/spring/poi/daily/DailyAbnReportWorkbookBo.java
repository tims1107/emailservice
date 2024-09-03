package com.spectra.result.transporter.businessobject.spring.poi.daily;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.result.transporter.businessobject.spring.poi.DailyReportWorkbookBo;
import com.spectra.result.transporter.businessobject.spring.poi.WorkbookBo;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;

public interface DailyAbnReportWorkbookBo extends DailyReportWorkbookBo {
	Workbook toWorkbookDailyReport(PatientRecord patientRecord);
}
