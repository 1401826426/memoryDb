package com.fei.memory.db.index;

@SuppressWarnings({"unchecked","rawtypes"})
public class IndexKey implements Comparable<IndexKey>{
	
	private Object[] vals ; 

	private int min ; 
	
	public IndexKey(Object[] vals) {
		super();
		this.vals = vals;
	}


	public IndexKey(Object[] vals, int min) {
		super();
		this.vals = vals ; 
		this.min = min;
	}


	@Override
	public int compareTo(IndexKey o) {
		int len = Math.min(this.vals.length,o.vals.length) ; 
		for(int i = 0;i < len;i++) {
			Object val1 = this.vals[i] ; 
			Object val2 = o.vals[i] ; 
			Class<?> clazz1 = val1.getClass() ; 
			Class<?> clazz2 = val2.getClass() ; 
			if(clazz1 != clazz2) {
				continue ; 
			}
			if(val1 instanceof Comparable<?>) {
				Comparable comparable = (Comparable)val1 ; 
				int num = comparable.compareTo(val2) ; 
				if(num != 0) {
					return num ; 
				}
			}
			
		}
		if((this.min - o.min) != 0) {
			return this.min - o.min ; 
		}
		return this.vals.length - o.vals.length;
	} 
	

	
}
