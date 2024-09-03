package com.spectra.asr.dao.type.handler;

import com.spectra.asr.dto.state.extract.ResultExtractDto;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleConnection;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

@Slf4j
public class ResultExtractDtoTypeHander implements TypeHandler<ResultExtractDto>{
	//private Logger log = Logger.getLogger(ResultExtractDtoTypeHander.class);
	
	@Override
	//public void setParameter(PreparedStatement ps, int i, Object parameter, String arg3) throws SQLException {
	//public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType arg3) throws SQLException {
	public void setParameter(PreparedStatement ps, int idx, ResultExtractDto param, JdbcType jdbcType) throws SQLException {
	    //List<ResultExtractDto> objects = (List<ResultExtractDto>) parameter;
		//ResultExtractDto object = (ResultExtractDto)parameter;

		OracleConnection oraConn = getOracleConnection(ps.getConnection());
		StructDescriptor structDescriptor = StructDescriptor.createDescriptor("STATERPT_OWNER.ASR_RESULTS_EXTRACT_DTO", oraConn);
		//StructDescriptor structDescriptor = StructDescriptor.createDescriptor("STATERPT_OWNER.ASR_RESULTS_EXTRACT_DTO", ps.getConnection());

	    Object[] params = new Object[10];
	    params[0] = param.getState();
	    params[1] = param.getOtc();
	    params[2] = param.getRtc();
	    params[3] = param.getOtcOuter();
	    params[4] = param.getRtcOuter();
	    params[5] = param.getOtcClose();
	    params[6] = param.getRtcClose();
	    params[7] = param.getFilterInner();
	    params[8] = param.getFilterOuter();
	    params[9] = param.getEwFlag();
	    //STRUCT struct = new STRUCT(structDescriptor, ps.getConnection(), params);
	    STRUCT struct = new STRUCT(structDescriptor, oraConn, params);
	    ps.setObject(idx, struct);
	    
	    /*
	    STRUCT[] structs = new STRUCT[objects.size()];
	    for (int index = 0; index < objects.size(); index++)
	    {
	        YourObject pack = objects.get(index);
	        Object[] params = new Object[2];
	        params[0] = pack.getFieldOne();
	        params[1] = pack.getFieldTwo();
	        STRUCT struct = new STRUCT(structDescriptor, ps.getConnection(), params);
	        structs[index] = struct;
	    }

	    ArrayDescriptor desc = ArrayDescriptor.createDescriptor("YOUR_OBJECT_ARRAY", ps.getConnection());
	    ARRAY oracleArray = new ARRAY(desc, ps.getConnection(), structs);
	    ps.setArray(i, oracleArray);
	    */
	}
	
	public OracleConnection getOracleConnection(Connection connection) throws SQLException {
	    OracleConnection oconn = null;
	    try {
	        if (connection.isWrapperFor(oracle.jdbc.OracleConnection.class)) {
	            oconn = (OracleConnection) connection.unwrap(oracle.jdbc.OracleConnection.class)._getPC();
	        }
	    } catch (SQLException e) {
	        throw e;
	    }
	    return oconn;
	}
	
	/*
	@Override
	public void setParameter(PreparedStatement ps, int idx, ResultExtractDto param, JdbcType jdbcType) throws SQLException {
		this.setParameter(ps, idx, param, jdbcType.STRUCT);
		
	}
	*/
	
	public ResultExtractDto getResult (ResultSet rs, int columnIndex){
		return null;
	}
	
	public ResultExtractDto getResult (ResultSet rs, String columnName){
		return null;
	}
	
	public ResultExtractDto getResult (CallableStatement cs, int columnIndex){
		return null;
	}

}
