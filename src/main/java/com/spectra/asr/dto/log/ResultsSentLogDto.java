package com.spectra.asr.dto.log;


import com.spectra.asr.dto.state.StateResultDto;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.sql.Timestamp;

public class ResultsSentLogDto {
	private Integer resultsSentLogPk;
	private Timestamp endLastUpdateTime;
	private String resultSource;
	private StateResultDto stateResultDto;
	
	public StateResultDto getStateResultDto() {
		return stateResultDto;
	}

	public void setStateResultDto(StateResultDto stateResultDto) {
		this.stateResultDto = stateResultDto;
	}

	public String getResultSource() {
		return resultSource;
	}

	public void setResultSource(String resultSource) {
		this.resultSource = resultSource;
	}

	public Timestamp getEndLastUpdateTime() {
		return endLastUpdateTime;
	}

	public void setEndLastUpdateTime(Timestamp endLastUpdateTime) {
		this.endLastUpdateTime = endLastUpdateTime;
	}

	public Integer getResultsSentLogPk() {
		return resultsSentLogPk;
	}

	public void setResultsSentLogPk(Integer resultsSentLogPk) {
		this.resultsSentLogPk = resultsSentLogPk;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
