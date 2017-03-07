package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbManager {
	
	private Connection connection;

	
	public DbManager(){
		
	}
	
	public Connection openConnection(){
		
		// JDBC driver nome e url DB
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://sviluppo.rete.make-it:3306/formazione";
		
		// Database credentials
		final String USER = "formazione";
		final String PASS = "formazione123";

		try {
			// Registra JDBC driver
			Class.forName(JDBC_DRIVER).newInstance();

			// Apertura connesione
			connection = DriverManager.getConnection(DB_URL, USER, PASS);

		} catch (SQLException e) {
			// gestione errori JDBC
			e.printStackTrace();
			throw new MyException(e);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
		return connection;
	}
	

	public void closeConn(){
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(e);
		} 
	}

	public void closeConn(ResultSet rs, PreparedStatement ps, Connection conn){
		
		if (rs!=null){
	        try{
	            rs.close();

	        } catch(Exception e){
	            e.printStackTrace();
	            throw new MyException(e);
	        }
	    }
	    if (ps != null)
	    {
	        try
	        {
	            ps.close();
	        } catch(Exception e){
	            e.printStackTrace();
	            throw new MyException(e);
	        }
	    }
	    if (conn != null){
	    	try {
	    		connection.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		throw new MyException(e);
	    	} 
	    }
	
	}
}	

