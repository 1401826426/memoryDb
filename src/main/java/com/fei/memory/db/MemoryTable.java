package com.fei.memory.db;

import java.util.List;

import com.fei.memory.db.index.BPlusTreeIndexManager;

public class MemoryTable<V> {
	
	private BPlusTreeIndexManager<V> indexManager ; 
	
	private SqlExecutor<V> sqlExecutor ; 
	
	public void insert(V v){
		indexManager.insert(v);
		sqlExecutor.insert(v) ; 
	}
	
	public void delete(V v){
		indexManager.delete(v);
		sqlExecutor.delete(v) ;
	}
	
	public List<V> find(String key,Object... args){
		return indexManager.find(key, args) ; 
	}
	
}








