package com.fighting.sams.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
	private Connection conn = null;
	private Statement st = null;

	public DatabaseHelper() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/samsdb?user=root&password=1996915&characterEncoding=utf8";
			conn = DriverManager.getConnection(url);
			st = conn.createStatement();
		} catch (java.lang.ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("找不到类" + e.toString());
		} catch (SQLException e) {
			System.out.println("数据库连接失败" + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return查询出来的结果集
	 */
	public ResultSet query(String sql) {
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询失败" + e.toString());
		}
		return rs;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @return 布尔值
	 */
	public boolean execute(String sql) {
		boolean isChanged = false;
		try {
			isChanged = st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询失败" + e.toString());
		}
		return isChanged;
	}

	/**
	 *  执行sql语句
	 * 
	 * @param sql
	 * @return 如果有数据返回大于0的数
	 */
	public int executeUpdate(String sql) {
		int isChanged = 0;
		try {
			isChanged = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询失败 " + e.toString());
		}
		return isChanged;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		if ((conn != null) && (st != null)) {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("关闭失败 " + e.toString());
			}
		}
	}

}
