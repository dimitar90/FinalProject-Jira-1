package com.jira.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DBManager {
	private static final String DB_USER = "root";

	private static final String DB_PASSWORD = "1234";

	private static final String DB_IP = "127.0.0.1";

	private static final String DB_PORT = "3306";

	private static final String DB_NAME = "jira";

	private static Connection connection;

	public DBManager() {
		// load driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry, Driver not loaded or does not exist! Aborting.");
		}
		System.out.println("Driver loaded");
		// Create connection
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME, DB_USER,
					DB_PASSWORD);
 
		} catch (SQLException e) {
			System.out.println("Sorry, connection failed. Maybe wrong credetials?");
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}
}
