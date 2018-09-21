package com.fei.memory.db.datasource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class PooledConnection implements InvocationHandler{
	
	private PooledDataSource pooledDataSource ; 
	
	private final static String CLOSE = "close"  ; 
	
	private Connection connection ; 
	
	private Connection proxyConnection ; 
	
	public PooledConnection(PooledDataSource pooledDataSource, Connection connection) {
		super();
		this.pooledDataSource = pooledDataSource;
		this.connection = connection;
		this.proxyConnection = (Connection)Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class<?>[]{Connection.class}, this) ; 
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String name = method.getName() ; 
		if(CLOSE.equals(name)){
			pooledDataSource.close(this) ;
			return null ; 
		}
		return method.invoke(proxy, args);
	}
	
	public Connection getConnection(){
		return connection ; 
	}
	
	public Connection getProxyConnection(){
		return proxyConnection ; 
	}

	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

}
