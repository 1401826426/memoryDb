package com.fei.memory.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import util.str.StringUtils;

public class UnpooledDataSource implements DataSource{
	
	private String driver ; 

	private String url ; 
	
	private String user ; 
	
	private String password ;  

	private ClassLoader classLoader ; 
	
	private Properties driverProperties ; 
	
	private boolean autoCommit ; 
	
	private int defaultTransactionIsolationLevel ; 
	
	public UnpooledDataSource(ClassLoader classLoader,String driver,String url,String user,String password) {
		this.driver = driver  ;
		this.url = url ; 
		this.user  = user ;
		this.password = password ;
		this.classLoader = classLoader ; 
	}
	
	public UnpooledDataSource(String driver,String url, String user, String password) {
		super();
		this.driver = driver;
		this.url = url ; 
		this.user = user;
		this.password = password;
	}

	public Properties getDriverProperties() {
		return driverProperties;
	}

	public void setDriverProperties(Properties driverProperties) {
		this.driverProperties = driverProperties;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter() ; 
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		DriverManager.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout() ;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLException("not supported") ; 
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		Properties prop = new Properties()  ; 
		if(driverProperties != null) {			
			prop.putAll(driverProperties);
		}
		if(!StringUtils.isBlank(user)) {			
			prop.put("user", user) ; 
		}
		if(!StringUtils.isBlank(password)) {			
			prop.put("password",password) ; 
		}
		return doGetConnection(prop) ; 
	}
	
	
	private Connection doGetConnection(Properties prop) throws SQLException {
		initDriver() ; 
		Connection connection = DriverManager.getConnection(url, user, password) ; 
		configure(connection) ; 
		return connection ; 
	}
	
	private void configure(Connection connection) throws SQLException {
		connection.setAutoCommit(autoCommit);
		connection.setTransactionIsolation(defaultTransactionIsolationLevel);
	}



	private void initDriver() throws SQLException {
		try {			
			DriverManager.getDriver(driver) ; 
		}catch(SQLException e) {
			try {
				if(this.classLoader != null) {
					Class.forName(driver, true,this.classLoader) ; 
				}else {
					ClassLoader.getSystemClassLoader().loadClass(driver) ; 
				}
				
			}catch(Exception exception) {
				throw new SQLException("canot load dirver",exception) ; 
			}
			
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		Properties prop = new Properties()  ; 
		if(driverProperties != null) {			
			prop.putAll(driverProperties);
		}
		if(!StringUtils.isBlank(user)) {			
			prop.put("user", username) ; 
		}
		if(!StringUtils.isBlank(password)) {			
			prop.put("password",password) ; 
		}
		return doGetConnection(prop) ; 
	}

}













