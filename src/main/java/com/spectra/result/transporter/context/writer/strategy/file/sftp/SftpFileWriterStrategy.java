package com.spectra.result.transporter.context.writer.strategy.file.sftp;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SftpFileWriterStrategy implements WriterStrategy<String> {
	//private Logger log = Logger.getLogger(SftpFileWriterStrategy.class);
	
	protected ConfigService configService;
	
	public void setConfigService(ConfigService configService){
		this.configService = configService;
	}
	
	@Override
	public Boolean write(String resultStr) {
		return this.write(null, resultStr);
	}

	@Override
	public Boolean write(String county, String resultStr) {
		Boolean wrote = Boolean.FALSE;
		if(resultStr != null){
			if(this.configService != null){
				try{
					String hostName = this.configService.getString("sftp.host.name");
					String userName = this.configService.getString("sftp.user.name");
					//String userName = this.configService.getString("sftp.user.name");
				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
					wrote = Boolean.FALSE;
				}
			}
		}
		return wrote;
	}

}
