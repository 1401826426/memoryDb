package com.fei.memory.db.btree;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
class InternalNode <K extends Comparable<K>,V> extends BNode<K,V>{
	
	BNode<K,V>[] bNodes ; 
	
	public InternalNode(int m) {
		super(m);
		bNodes = (BNode<K, V>[]) Array.newInstance(BNode.class, m) ; 
	}

	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public Object[] getData() {
		return bNodes ; 
	}

	
	
	@Override
	protected void clear() {
		super.clear();
		this.bNodes = null ; 
	}
	
	@Override
	protected void merge(BNode<K, V> bNode) {	
		super.merge(bNode);
		InternalNode<K, V> internalNode = (InternalNode<K, V>)bNode ; 
		for(int i = 0;i < bNode.size;i++){
			BNode<K,V> b = internalNode.bNodes[i] ; 
			b.parent = this;
		}
	}

	@Override
	public BNode<K, V> insert(K k, V v) {
		int pos = lastLeBinSearch(k);
		if(pos == -1){
			pos = 0 ; 
		}
		BNode<K,V> bNode = bNodes[pos] ; 
		BNode<K,V> newNode = bNode.insert(k, v) ; 
		this.keys[pos] = bNode.getKey() ; 
		if(newNode != null){
			rightMove(pos+1);
			this.keys[pos+1] = newNode.getKey() ; 
			this.bNodes[pos+1] = newNode ;
			newNode.parent = this ; 
			if(this.size == m){
				InternalNode<K, V> internalNode = new InternalNode<>(m) ; 
				System.arraycopy(this.keys, m/2, internalNode.keys, 0, m/2);
				System.arraycopy(this.bNodes, m/2, internalNode.bNodes, 0, m/2);
				this.size = m/2 ; 
				internalNode.size = m/2 ; 
				for(int i = 0;i < bNode.size;i++){
					BNode<K,V> bb = internalNode.bNodes[i] ; 
					bb.parent = internalNode ; 
				}
				return internalNode ; 
			}
		}
		return null;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("") ; 
		sb.append("|") ; 
		for(int i = 0;i < size;i++){
			sb.append(this.keys[i]+"_" + bNodes[i].getKey()+"|") ; 
		}
		return sb.toString();
	}

	@Override
	public V delete(K k) {
		int pos = lastLeBinSearch(k) ; 
		if(pos == -1){
			return null; 
		}
		BNode<K,V> bNode = this.bNodes[pos] ; 
		V v = bNode.delete(k);
		this.keys[pos] = bNode.getKey() ;  
		if(bNode.isLack()){
			if(pos == 0){
				BNode<K,V> nextNode = this.bNodes[pos+1] ; 
				if(nextNode.isJustFull()){
					bNode.merge(nextNode);
					leftMove(pos+1);
					nextNode.clear();  
				}else{
					nextNode.moveOne(bNode, false);
					this.keys[pos+1] = nextNode.getKey() ;
				}
			}else{
				BNode<K,V> preNode = this.bNodes[pos-1] ; 
				if(preNode.isJustFull()){
					preNode.merge(bNode);
					leftMove(pos);
					bNode.clear();  
				}else{
					preNode.moveOne(bNode, true);
					this.keys[pos] = bNode.getKey() ; 
				}
			}
		}
		return v;
		
	}

	@Override
	public V find(K k) {
		int pos = lastLeBinSearch(k) ; 
		if(pos == -1){
			return null  ;
		}
		return this.bNodes[pos].find(k) ; 
	}

	@Override
	public LeafNode<K, V> firstGeFind(K k) {
		int pos = firstGeBinSearch(k) ; 
		if(pos == -1){
			return null ; 
		}
		return this.bNodes[pos].firstGeFind(k) ;
	}

	@Override
	public LeafNode<K, V> lastLeFind(K k) {
		int pos = lastLeBinSearch(k) ;
		if(pos == -1){
			return null ; 
		}
		return this.bNodes[pos].lastLeFind(k) ; 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
}

