package com.seehope.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
/**
 * 使用该结果集处理器，有一个最大的要点，就是数据库的字段和一一对应实体类的对象中的属性
 * @author Administrator
 *
 * @param <T>
 */
public class BeanHandler<T> implements IResultSetHandler<T>{
	private Class<T> clz;
	public BeanHandler(Class<T> clz){
		this.clz = clz;
	}
	public T handler(ResultSet rs) {
		try {
			//获取bean信息
			BeanInfo beanInfo = Introspector.getBeanInfo(clz, Object.class);
			//获取属性描述
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			//创建对象
			T obj = clz.newInstance();
			while(rs.next()){
				for(PropertyDescriptor pd : pds){
					String name = pd.getName();
					pd.getWriteMethod().invoke(obj, rs.getObject(name));
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
