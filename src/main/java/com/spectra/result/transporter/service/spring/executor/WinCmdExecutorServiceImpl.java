package com.spectra.result.transporter.service.spring.executor;

import com.spectra.result.transporter.executor.WinCmdExecutor;
import com.spectra.result.transporter.service.spring.SpringServiceImpl;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WinCmdExecutorServiceImpl extends SpringServiceImpl implements WinCmdExecutorService {
	//private Logger log = Logger.getLogger(WinCmdExecutorServiceImpl.class);
	
	private WinCmdExecutor winCmdExecutor;
	
	@Override
	public void setWinCmdExecutor(WinCmdExecutor winCmdExecutor) {
		this.winCmdExecutor = winCmdExecutor;

	}

	@Override
	public void exec(String cmd) throws Exception {
		if(cmd != null){
			if(this.winCmdExecutor != null){
				this.winCmdExecutor.exec(cmd);
			}
		}else{
			throw new Exception(new IllegalArgumentException("null cmd"));
		}
	}

}
