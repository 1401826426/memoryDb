package com.fei.memory.db.index;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fei.memory.db.btree.BPlusTree;

import util.clazz.ClazzUtil;

public class BPlusTreeIndexManager<V> {
	
	private Map<String,Field[]> indexes = new HashMap<>(); 
	
	private Map<String,BPlusTree<IndexKey,V>> map = new HashMap<>() ; 
	
	private int m = 10 ; 
	
	public void insert(V v) {
		for(Map.Entry<String, BPlusTree<IndexKey,V>> entry:map.entrySet()) {
			String name = entry.getKey() ; 
			Field[] fields = indexes.get(name) ; 
			BPlusTree<IndexKey,V> tree = entry.getValue() ; 
			IndexKey indexKey = IndexKeyGenerator.getInstance().generate(v, fields) ;
			tree.insert(indexKey, v);
		}
	}
	
	public void delete(V v) {
		for(Map.Entry<String, BPlusTree<IndexKey,V>> entry:map.entrySet()) {
			String name = entry.getKey() ; 
			Field[] fields = indexes.get(name) ; 
			BPlusTree<IndexKey,V> tree = entry.getValue() ; 
			IndexKey indexKey = IndexKeyGenerator.getInstance().generate(v, fields) ;
			tree.delete(indexKey) ; 
		}
	}
	
	public List<V> find(String key,Object... args) {
		BPlusTree<IndexKey,V> tree  = map.get(key) ;
		IndexKey stKey = IndexKeyGenerator.getInstance().generate(args,-1) ; 
		IndexKey enKey = IndexKeyGenerator.getInstance().generate(args, 1) ; 
		return tree.rangeFind(stKey, enKey); 
	}
	
	public void init(Class<V> clazz) throws NoSuchFieldException, SecurityException {
		BTreeIndexes btreeIndexes = ClazzUtil.getAnnotation(clazz, BTreeIndexes.class) ; 
		for(BTreeIndex btreeIndex:btreeIndexes.value()) {
			String name = btreeIndex.name() ; 
			String[] fieldNames = btreeIndex.fields()  ;
			List<Field> list = new ArrayList<Field>() ;
			for(String fieldName:fieldNames) {
				Field field = clazz.getField(fieldName) ; 
				list.add(field) ; 
			}
			Field[] fields = new Field[list.size()] ;
			list.toArray(fields) ; 
			indexes.put(name, fields) ;
			BPlusTree<IndexKey,V> tree = new BPlusTree<IndexKey,V>(m) ; 
			map.put(name, tree) ; 
		}
		
	}
	
}
