package com.spectra.result.transporter.service.spring.executor;

import com.spectra.result.transporter.executor.WinCmdExecutor;

public interface WinCmdExecutorService {
	void setWinCmdExecutor(WinCmdExecutor winCmdExecutor);
	void exec(String cmd) throws Exception;
}
