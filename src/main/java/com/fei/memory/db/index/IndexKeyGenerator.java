package com.fei.memory.db.index;

import java.lang.reflect.Field;


@BTreeIndexes({
		@BTreeIndex(name="1",fields= {"1","2"}),
		@BTreeIndex(name="2",fields= {"1","2"})
})
public class IndexKeyGenerator {
	
	private static IndexKeyGenerator instance = new IndexKeyGenerator()  ; 
	
	
	public static IndexKeyGenerator getInstance() {
		return instance ; 
	}
	
	public IndexKey generate(Object obj,Field[] fields,int min) {
		Object[] vals = new Object[fields.length] ; 
		for(int i = 0;i < vals.length;i++) {
			Field field = fields[i] ; 
			try {
				Object val = field.get(obj) ;
				vals[i] = val ;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null ; 
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null ; 
			} 
		}
		return generate(vals,min) ; 
	}
	
	public IndexKey generate(Object obj,Field[] fields) {
		return generate(obj,fields,0) ; 
	}

	public IndexKey generate(Object[] vals, int min) {
		IndexKey indexKey = new IndexKey(vals,min) ; 
		return indexKey ; 
	}
	
}











