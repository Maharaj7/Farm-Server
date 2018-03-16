package controller;

import java.sql.*;

import javax.swing.JOptionPane;

public class SqlConnection {

	Connection conn = null;
	public static Connection dbConnector()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("org.sqlite.JDBC");//for SQLite
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers market","root","");
			//Connection conn = DriverManager.getConnection("jdbc:sqlite:farmers market.sqlite");//for SQLite

			//logging for server connection
			//JOptionPane.showMessageDialog(null, "Connection Successful");
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
