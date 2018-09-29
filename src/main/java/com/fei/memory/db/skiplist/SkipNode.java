package com.fei.memory.db.skiplist;

public class SkipNode<V extends Comparable<V>> {
	
	SkipNode<V> next ; 
	
	SkipNode<V> down ; 
	
	V v;
	
    int level ; 
    
    

	public SkipNode() {
		super();
	}

	public SkipNode(V v, int level) {
		super();
		this.v = v;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SkipNode<V> getNext() {
		return next;
	}

	public void setNext(SkipNode<V> next) {
		this.next = next;
	}

	public SkipNode<V> getDown() {
		return down;
	}

	public void setDown(SkipNode<V> down) {
		this.down = down;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

	@Override
	public String toString() {
//		return level + "_" + (v == null ? "null" : v.toString());
		return (v == null ? "null" : v.toString());
	} 
	
	
	
}

