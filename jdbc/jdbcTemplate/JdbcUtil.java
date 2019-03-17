package com.seehope.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
	private static Properties p = new Properties();
	static{
		try {
			//通过流将properties文件加载进来
			InputStream in = Thread.currentThread().getContextClassLoader().
					getResourceAsStream("jdbc.properties");
			p.load(in);
			//1.加载驱动
			Class.forName(p.getProperty("DriverClassName"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(p.getProperty("url"), 
				p.getProperty("username"), p.getProperty("password"));
	}
	
	
	public static void close(Connection conn,Statement st,ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(st != null){
					st.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}finally{
				try {
					if(conn != null){
						conn.close();
					}
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
	}
}
