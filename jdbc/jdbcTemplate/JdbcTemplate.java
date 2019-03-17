package com.seehope.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTemplate {
	
	//DML操作模板
	public static void update(String sql,Object ... params){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			//1.获取接连
			conn = JdbcUtil.getConnection();
			//2.创建预编译语句对象
			ps = conn.prepareStatement(sql);
			if(params != null){
				for(int index = 0 ; index < params.length ; index++){
					ps.setObject(index+1, params[index]);
				}
			}
			//3.执行语句
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(conn, ps, null);
		}
	}
	
	//DQL操作模板
	public static <T> T query(String sql,IResultSetHandler<T> rsh,Object ... params){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取连接
			conn = JdbcUtil.getConnection();
			//2.获取预编译语句对象
			ps = conn.prepareStatement(sql);
			//3.设置值
			if(params != null){
				for(int index = 0 ; index < params.length ; index++){
					ps.setObject(index+1, params[index]);
				}
			}
			rs = ps.executeQuery();
			return rsh.handler(rs);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally{
			JdbcUtil.close(conn, ps, rs);
		}
	}
	
	
	
	
}
