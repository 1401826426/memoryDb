package com.fei.memory.db.skiplist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class SkipList<K extends Comparable<K>> {
	
	private SkipNode<K> head ;
	
	private int maxLevel = 10;  
	
	private double factor = 0.5 ; 
	
	public SkipList() {
		head = new SkipNode<>(null,0) ; 
	}

	public SkipNode<K> insert(K K){
		SkipNode<K> node = find(K) ;
		if(node != null){
			return node ; 
		}
		int level = randomGetLeKel() ;
		int nowLevel = head.getLevel(); 
		while(nowLevel < level){
			nowLevel++ ; 
			SkipNode<K> tmp = new SkipNode<K>(null,nowLevel); 
			tmp.down = head ; 
			head = tmp ; 
		}
		SkipNode<K>[] nodes = (SkipNode<K>[]) Array.newInstance(SkipNode.class, level+1) ; 
		SkipNode<K> pre = null ; 
		for(int i = level;i >= 0;i--){
			nodes[i] = new SkipNode<K>(K,i) ;
			if(pre != null){ 
				pre.down = nodes[i] ; 
			} 
			pre = nodes[i] ; 
		}
		SkipNode<K> p = head ; 
		while(p.level > level){
			p = p.down ; 
		}
		SkipNode<K> q = p ;
		for(int i = level;i >= 0;i--){
			while(q.next != null && q.next.v.compareTo(K) < 0){
				q = q.next ; 
			}
			SkipNode<K> tmp = q.next ; 
			q.next = nodes[i] ;
			nodes[i].next = tmp;
			q = q.down ; 
		}
		return nodes[0] ; 
	}
	
	public SkipNode<K> delete(K k){
		SkipNode<K> p = head ; 
		while(p != null){
			while(p.next != null && p.next.v.compareTo(k) < 0){
				p = p.next; 
			}
			if(p.next != null && p.next.v.compareTo(k) == 0){ 
				p.next = p.next.next ;  
			}
			p = p.down ; 
		}
		return null ; 
	}
	
	public SkipNode<K> find(K K){
		SkipNode<K> p = head ; 
		while(p != null){
			while(p.next != null && p.next.v.compareTo(K) < 0){
				p = p.next ; 
			}
			if(p.next != null && p.next.v.compareTo(K) == 0){
				return p.next ; 
			}
			p = p.down ; 
		}
		return null;
	}
	
	

	@Override
	public String toString() {
		Stack<SkipNode<K>> stack = new Stack<>() ; 
		SkipNode<K> p = head; 
		while(p.down != null){
			stack.add(p) ; 
			p = p.down ; 
		}
		Map<SkipNode<K>,Integer> map = new HashMap<>() ;
		List<String> list = new ArrayList<>() ; 
		while(p != null){			
			String s = "" ;  
			SkipNode<K> q = p ;
			boolean flag = false ; 
			Map<SkipNode<K>,Integer> newMap = new HashMap<>() ; 
			while(q != null){
				if(flag){
					if(q.down != null){
						int st = map.get(q.down) ; 
						for(int i = s.length();i < st-1;i++){
							s += "-";
						}
						s += ">" ; 
					}else{
						s += "---->" ;
					}
				}
				newMap.put(q, s.length()) ; 
				s += q.toString() ; 
				flag = true ;
				q = q.next; 
			}
			list.add(s) ;
			map = newMap ;
			if(stack.isEmpty()){
				break ; 
			}
			p = stack.pop();
		}
		StringBuilder sb = new StringBuilder() ;
		for(int i = list.size()-1;i >= 0;i--){
			sb.append(list.get(i) + "\n") ; 
		}
		return sb.toString() ; 
	}

	private int randomGetLeKel() {
		int level = 0 ;
		Random random = new Random() ; 
		while((random.nextInt(0xffff)) < (factor*0xffff)){
			level++ ; 
		}
		return level > maxLevel ? maxLevel : level; 
	}
	
	public static void main(String[] args) {
		SkipList<Integer> skipList = new SkipList<Integer>() ; 
		for(int i = 0;i < 20;i++){
			skipList.insert(i) ;
		}
		for(int i = 40;i >= 0;i--){
			skipList.insert(i) ;
		}
		for(int i = 30;i <= 50;i++){
			skipList.insert(i); 
		}
		System.out.println(skipList);
		for(int i = 0;i < 10;i++){
			skipList.delete(i) ;
		}
		for(int i = 20;i < 30;i++){
			skipList.delete(i) ;
		}
		for(int i = 5;i < 10;i++){
			skipList.insert(i) ;
		}
		for(int i = 40;i < 50;i++){
			skipList.delete(i) ;
		}	
		System.out.println(skipList);
	}
	
}





