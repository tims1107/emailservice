package com.spectra.asr.db.ds;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

//import java.util.logging.Logger;
//import org.apache.logging.log4j.LogManager;

@Slf4j
public class LocalDataSource implements DataSource, Serializable {
	//private Logger log = Logger.getLogger(LocalDataSource.class);
	
	private String connectionString;
    private String username;
    private String password;
    
    public LocalDataSource(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }
    
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(connectionString, username, password);
    }

	public Connection getConnection(String username, String password)
			throws SQLException {return null;}
	public PrintWriter getLogWriter() throws SQLException {return null;}
	public int getLoginTimeout() throws SQLException {return 0;}
	public void setLogWriter(PrintWriter out) throws SQLException {	}
	public void setLoginTimeout(int seconds) throws SQLException {}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
