package com.commity.DataBaseUtil;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataBaseConnection {
	private static final String dbConfigFileName = "MySQLProperty";
	private static final String DS_JNDI_NAME = "jdbc/petSurvey";

	
	/*
	 * 从配置文件中获取数据库连接
	 */
	public static Connection getConn() throws Exception {
		Properties prop = new Properties();
		ResourceBundle bundle = ResourceBundle.getBundle(dbConfigFileName);
		Enumeration en = bundle.getKeys();
		String key = null;

		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			prop.put(key, bundle.getObject(key));
		}

		String drivers = prop.getProperty("jdbc.drivers");
		String dbURL = prop.getProperty("jdbc.url");
		String user = prop.getProperty("jdbc.user");
		String pwd = prop.getProperty("jdbc.pwd");

		if (drivers == null)
			return null;// 如果没有指定驱动，返回空；

		// System.setProperty("jdbc.drivers", drivers);
		Class.forName(drivers);

		// 获取数据库连接
		Connection con = DriverManager.getConnection(dbURL, user, pwd);
		return con;
	}


	public static void releaseConn(Connection con)throws Exception{
		if(con != null) con.close();
	}
}
