package com.spectra.result.transporter.service.spring.state.daily;

import com.spectra.result.transporter.service.spring.state.StateResultService;

public interface StateDailyResultService extends StateResultService {
	boolean process(String state, String ewFlag);
}
