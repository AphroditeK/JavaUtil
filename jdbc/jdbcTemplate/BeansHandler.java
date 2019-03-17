package com.seehope.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BeansHandler<T> implements IResultSetHandler<List<T>> {
	private Class<T> clz;
	public BeansHandler(Class<T> clz){
		this.clz = clz;
	}

	public List<T> handler(ResultSet rs) {
		try {
			List<T> list = new ArrayList<T>();
			//获取bean信息
			BeanInfo beanInfo = Introspector.getBeanInfo(clz, Object.class);
			//获取属性描述
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			//创建对象
			while(rs.next()){
				T obj = clz.newInstance();
				for(PropertyDescriptor pd : pds){
					String name = pd.getName();
					pd.getWriteMethod().invoke(obj, rs.getObject(name));
				}
				//将对象添加在list中
				list.add(obj);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
