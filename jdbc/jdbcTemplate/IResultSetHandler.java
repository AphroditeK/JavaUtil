package com.seehope.utils;

import java.sql.ResultSet;

public interface IResultSetHandler <T>{

	/**
	 * 结果处理方法
	 * @param rs
	 * @return
	 */
	T handler(ResultSet rs);
}
