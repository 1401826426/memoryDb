package com.fei.memory.db.btree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked") 
public class BPlusTree <K extends Comparable<K> ,V>{
	
	private int m ; 
	
	private BNode<K,V> root ; 
	
	private LeafNode<K,V> head ; 
	
	public BPlusTree(int m){
		this.m = m % 2 == 0 ? m : m+1; 
	}
	
	public void insert(K k,V v){
		if(root == null){
			this.head = new LeafNode<>(m) ;
			this.head.next = this.head ; 
			root =  head ; 
		}
		BNode<K,V> newNode = root.insert(k, v) ; 
		if(newNode != null){
			InternalNode<K, V> internalNode = new InternalNode<>(m) ; 
			internalNode.keys[0] = root.getKey() ; 
			internalNode.bNodes[0] = root ; 
			root.parent = internalNode ; 
			internalNode.keys[1] = newNode.getKey() ; 
			internalNode.bNodes[1] = newNode ;
			newNode.parent = internalNode ; 
			internalNode.size = 2 ; 
			this.root = internalNode ; 
		}
	}

	public V delete(K k){
		if(root == null){
			return null ; 
		}
		V v = root.delete(k) ;
		if(root instanceof LeafNode){
			LeafNode<K,V> leafNode = (LeafNode<K,V>)root ;
			if(leafNode.size == 0){
				this.root = null ; 
			}
		}else{
			InternalNode<K, V> internalNode = (InternalNode<K,V>)root;
			if(internalNode.size == 1){
				this.root = internalNode.bNodes[0] ; 
				this.root.parent = null ; 
			}
		}
		return v ; 
		
	}
	
	public V find(K k){
		if(root == null){
			return null ; 
		}
		return root.find(k) ; 
	}
	
	public List<V> rangeFind(K st,K en){
		List<V> list = new LinkedList<>();
		LeafNode<K,V> stNode = root.lastLeFind(st) ;
		LeafNode<K,V> enNode = root.lastLeFind(en) ; 
		if(enNode == null){
			return list ; 
		}
		if(stNode == null){
			stNode = this.head ; 
		}
		LeafNode<K,V> p = stNode ;
		while(true){
			for(int i = 0;i < p.size;i++){
				K k = (K)p.keys[i] ;
				if(k.compareTo(st) >= 0 && k.compareTo(en) <= 0){
					list.add((V)p.datas[i]) ;
				}
			}
			if(p == enNode){
				break ; 
			}
			p = p.next ;
		}
		return list ; 
	}
	
	@Override
	public String toString() {
		if(root == null){
			return "null" ; 
		}
		StringBuilder sb = new StringBuilder("") ; 
		LeafNode<K,V> p = this.head ;
		boolean first = true ; 
		while(true){
			if(!first){
				sb.append("  ") ; 
			}
			first = false; 
			sb.append(p.toString()) ; 
			p = p.next ; 
			if(p == this.head){
				break ; 
			}
		}
		int len = sb.toString().length() ; 
		sb = new StringBuilder("") ; 
		List<BNode<K,V>> list = new ArrayList<>();
		list.add(root) ; 
		while(list.size() > 0){
			StringBuilder lenSb = new StringBuilder("") ;
			first = true ; 
			List<BNode<K,V>> tmpList = new ArrayList<>() ; 
			for(BNode<K,V> bNode:list){
				if(!first){
					lenSb.append("  ") ; 
				}
				first = false ; 
				lenSb.append(bNode) ; 
				if(bNode instanceof InternalNode){
					InternalNode<K, V> internalNode = (InternalNode<K,V>)bNode ;
					for(int i = 0;i < internalNode.size;i++){
						tmpList.add(internalNode.bNodes[i]) ; 
					}
				}
			}
			int st = (len - lenSb.toString().length())/2 ; 
			for(int i = 0;i < st;i++){
				sb.append(" ") ; 
			}
			sb.append(lenSb.toString()+"\n") ; 
			list = tmpList ; 
		}
		return sb.toString() ; 
	}
	
	public static void main(String[] args) {
		BPlusTree<Integer, Integer> tree = new BPlusTree<>(6) ; 
		for(int i = 40;i >= 10;i--){
			tree.insert(i, i);
//			System.out.println("================================================================"+i+"==================================================");
//			System.out.println(tree);
		}
		System.out.println(tree);
		
//		System.out.println("==============================================================3==10==================================================");
//		List<Integer> list = tree.rangeFind(3, 10) ;
//		for(int i = 0;i < list.size();i++){
//			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
//		}
//		
//		System.out.println("==============================================================3==3==================================================");
//		list = tree.rangeFind(3, 3) ;
//		for(int i = 0;i < list.size();i++){
//			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
//		}
//		
//		System.out.println("==============================================================10==10==================================================");
//		list = tree.rangeFind(10, 10) ;
//		for(int i = 0;i < list.size();i++){
//			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
//		}
//	
//		
//		System.out.println("==============================================================40==40==================================================");
//		list = tree.rangeFind(30, 30) ;
//		for(int i = 0;i < list.size();i++){
//			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
//		}
//		
//		System.out.println("==============================================================10==40==================================================");
//		list = tree.rangeFind(10, 40) ;
//		for(int i = 0;i < list.size();i++){
//			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
//		}
//		
		for(int i = 10;i <= 20;i++){
			tree.delete(i) ;
			System.out.println("================================================================"+i+"==================================================");
			System.out.println(tree);
		}
		
		for(int i = 30;i >= 20;i--){
			tree.delete(i) ; 
			System.out.println("======================================"+i+"=============================");
			System.out.println(tree);
		}
		
		
		
		System.out.println("==============================================================40==40==================================================");
		List<Integer> list = tree.rangeFind(40, 40) ;
		for(int i = 0;i < list.size();i++){
			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
		}
		
		System.out.println("==============================================================10==40==================================================");
		list = tree.rangeFind(10, 40) ;
		for(int i = 0;i < list.size();i++){
			System.out.printf("%d%c", list.get(i) , i == list.size()-1 ? '\n':' ') ; 
		}
	
	}
	
}

























