import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	public static Connection connect() {
		Connection conn = null; 
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		//String sqlVersion = "org.sqlite.JDBC";
		
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		
//		String username = "pg"; //"postgres";
//		String psswd = "5432";
//		String jdbcHost = "jdbc:postgresql://localhost:5432/database";//"jdbc:postgresql://localhost:5432/bookstore";
		String jdbcDriver = "org.postgresql.Driver";

		try {
			Class.forName(jdbcDriver);
			
			// connecting to our database
			conn = DriverManager.getConnection(jdbcURL, username, psswd); 
			
			// If using sqlite locally 
			//conn = DriverManager.getConnection(jdbcHost);
			
						
		} catch (ClassNotFoundException | SQLException e ) {
			// TODO Auto-generated catch block
			System.out.println(e+"");
		}
		return conn;
	}
	
}
