package com.fei.memory.db.btree;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
abstract class BNode <K extends Comparable<K>,V>{
	
	protected Comparable<K>[] keys ; 
	
	protected int size ;
	
	protected int m ; 

	@SuppressWarnings("rawtypes")
	BNode parent ; 
	
	public BNode(int m) {
		this.m = m;
		this.keys = (Comparable<K>[]) Array.newInstance(Comparable.class, m) ;
	}

	public abstract boolean isRoot() ; 
	
	public abstract boolean isLeaf() ; 
	
	public abstract Object[] getData() ;
	
	public boolean isLack(){
		return this.size < m/2 ;
	}
	
	public boolean isJustFull(){
		return this.size == m/2 ; 
	}
	
	public K getKey(){
		return (K)this.keys[0] ; 
	}
	
	public abstract BNode<K,V> insert(K k,V v) ; 
	
	public abstract V delete(K k) ; 
	
	public abstract V find(K k) ; 
	
	//第一个大于等于k的LeafNode
	public abstract LeafNode<K,V> firstGeFind(K k); 
	
	//最后一个小于等于k的LeafNode
	public abstract LeafNode<K,V> lastLeFind(K k);
	
	//从当前移动一个到bNode
	//to为true  从当前的末尾移动一个到bNode头
	//to为false 从当前的头移动一个到bNode的末尾
	protected void moveOne(BNode<K,V> bNode,boolean to){
		if(to){
			bNode.rightMove(0);
			bNode.keys[0] = this.keys[this.size-1] ; 
			bNode.getData()[0] = this.getData()[this.size-1] ; 
			Object obj = bNode.getData()[0] ; 
			if(obj instanceof BNode){
				BNode<K,V> b = (BNode<K,V>)obj ;
				b.parent = bNode; 
			}
			this.size-- ; 
		}else{
			bNode.keys[bNode.size-1] = this.keys[0] ; 
			bNode.getData()[bNode.size-1] = this.getData()[0] ; 
			bNode.size++ ; 
			Object obj = bNode.getData()[bNode.size-1] ; 
			if(obj instanceof BNode){
				BNode<K,V> b = (BNode<K,V>)obj ;
				b.parent = bNode; 
			}
			this.leftMove(0);
		}
	}
	
	//将bNode的数据合并到当前bNode
	protected void merge(BNode<K,V> bNode){
		System.arraycopy(bNode.keys, 0, this.keys, this.size, bNode.size);
		System.arraycopy(bNode.getData(), 0, this.getData(), this.size, bNode.size);
		this.size += bNode.size ; 
	}
	
	protected void leftMove(int pos){
		Object data = this.getData()[pos] ; 
		if(data instanceof BNode){
			BNode<K,V> bNode = (BNode<K,V>)data ; 
			bNode.clear() ; 
		}
 		System.arraycopy(this.keys, pos+1, this.keys, pos, size-pos-1);
		System.arraycopy(this.getData(), pos+1, this.getData(), pos, size-pos-1);
		this.size-- ; 
	}
	
	

	protected void rightMove(int pos){
		System.arraycopy(this.keys, pos, this.keys, pos+1, size-pos);
		System.arraycopy(this.getData(), pos, this.getData(), pos+1, size-pos);
		this.size++ ; 
	}
	
	protected void clear() {
		this.parent = null ; 
		this.keys = null ; 
	}
	
	//最后一个小于等于的
	protected int lastLeBinSearch(K k){
		int l = 0 ; 
		int r = size-1 ; 
		while(l <= r){
			int mid = (l+r) >> 1 ; 
		    if(keys[mid].compareTo(k) <= 0){
		    	l = mid + 1 ;  
		    }else{
		    	r = mid - 1 ; 
		    }
		}
		return r; 
	}
	
	protected int firstGeBinSearch(K k){
		int l = 0 ; 
		int r = size-1 ;
		while(l <= r){
			int mid = (l+r) >> 1 ; 
		    if(keys[mid].compareTo(k) >= 0){
		    	r = mid-1 ; 
		    }else{
		    	l = mid+1; 
		    }
		}
		return l ; 
	}
	
}
