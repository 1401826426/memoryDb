package com.fei.memory.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class PooledDataSource implements DataSource {

	private DataSource dataSource;

	private Pool pool;
	protected int poolMaximumActiveConnections = 10;
	protected int poolMaximumIdleConnections = 5;
	protected int poolMaximumCheckoutTime = 20000;
	protected int poolTimeToWait = 20000;
	protected int poolMaximumLocalBadConnectionTolerance = 3;
	protected String poolPingQuery = "NO PING QUERY SET";
	protected boolean poolPingEnabled;
	protected int poolPingConnectionsNotUsedFor;

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return dataSource.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return dataSource.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {

		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}

	public void close(PooledConnection conn) throws SQLException {
		pool.activeConns.remove(conn) ; 
		if(pool.idleConns.size() < poolMaximumIdleConnections && conn.isValid()){
			if(!conn.getConnection().getAutoCommit()){
				conn.getConnection().rollback();
			}
			pool.idleConns.add(conn.getConnection()) ; 
		}else{
			
		}
		
//		pool.getIdleConn().add(conn);
	}

}
