package com.fei.memory.db.datasource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Pool {
	
	List<Connection> idleConns = new ArrayList<>() ; 
	
	List<Connection> activeConns = new ArrayList<>() ; 	
	
	
}
