package com.fei.memory.db;

import java.util.List;

import com.fei.memory.db.SqlCommand.Command;

import util.bean.BeanDescription;
import util.bean.BeanDescriptionParser;
import util.bean.PropertyDescription;

public class SqlCommandGenerator {
	
	public SqlCommand generate(Object obj,Command command){
		BeanDescription beanDescription = BeanDescriptionParser.getInstance().parse(obj.getClass()) ; 
		List<PropertyDescription> pds = beanDescription.getPds() ;
		for(PropertyDescription pd:pds){
			
		}
		return null ; 
	}
	
	public static void main(String[] args) {
		System.out.println((-1 << 29) & (~(1<<29)-1));
	}
	
}
