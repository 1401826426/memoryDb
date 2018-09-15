package com.fei.memory.db;

@SuppressWarnings("unchecked")
class LeafNode <K extends Comparable<K>,V> extends BNode<K,V>{
	
	Object[] datas  ;

	LeafNode<K,V> next ; 
	
	public LeafNode(int m) {
		super(m);
		this.datas = new Object[m] ; 
	}

	
	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public Object[] getData() {
		return datas;
	}

	
	
	@Override
	protected void clear() {
		super.clear();  
		this.datas = null ; 
		this.next = null ; 
	}


	@Override
	public BNode<K,V> insert(K k, V v) {
		int pos = lastLeBinSearch(k) ; 
		rightMove(pos+1); 
		this.keys[0] = k ; 
		this.datas[0] = v ; 
		if(this.size == m){
			LeafNode<K,V> rightNode = new LeafNode<K,V>(m) ; 
			System.arraycopy(this.keys, m/2, rightNode.keys, 0, m/2);
			System.arraycopy(this.datas, m/2, rightNode.datas, 0, m/2);
			this.size = m/2 ; 
			rightNode.size = m/2 ;
			LeafNode<K,V> tmp = this.next ;
			this.next = rightNode ; 
			rightNode.next = tmp ; 
			return rightNode ; 
		}
		return null; 
	}

	

	@Override
	protected void merge(BNode<K, V> bNode) {
		super.merge(bNode);
		LeafNode<K,V> leafNodeB = (LeafNode<K,V>)bNode ;  
		this.next = leafNodeB.next ; 
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("") ;
		sb.append("|") ; 
		for(int i = 0;i < size;i++){
			sb.append(this.keys[i]+"_"+this.datas[i]+"|") ; 
		}
		return sb.toString() ; 
	}


	@Override
	public V delete(K k) {
		int pos = lastLeBinSearch(k) ; 
		if(pos < 0){
			return null; 
		}
		Object obj = this.datas[pos] ; 
		leftMove(pos);
		return (V)obj;
		
	}


	@Override
	public V find(K k) {
		int pos = lastLeBinSearch(k) ; 
		if(pos == -1){
			return null ;
		}
		return (V)this.datas[pos];
	}


	@Override
	public LeafNode<K, V> firstGeFind(K k) {
		return this ; 
	}


	@Override
	public LeafNode<K, V> lastLeFind(K k) {
		return this;
	}
	
	
}















