
public class Customer {
	private String account_number;
	private String cname;
	private String caddress;
	private String phone_number;
	private String email;
	
	public Customer() {
		
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getName() {
		return cname;
	}

	public void setName(String name) {
		this.cname = name;
	}

	public String getAddress() {
		return caddress;
	}

	public void setAddress(String address) {
		this.caddress = address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
/*
	public void insert(String firstName, String secondName, String email, String password) {
		Connection conn = DbConnect.connect();
		PreparedStatement ps = null; 
		try {
			String sql = "INSERT INTO users(firstName, secondName, email, password) VALUES(?,?,?,?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, secondName);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.execute();
			System.out.println("Data has been inserted!");
			
		} catch(SQLException e) {
			System.out.println(e.toString());
			// always remember to close database connections
			
		} finally {
			try{
				ps.close();
				con.close();
				
			}catch(SQLException e) {
				System.out.println(e.toString());	
			}	
		}	
	}
		
///////////
		
		//Registering the Driver
			
		//Getting the connection
		//add to db
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
/*
		String jdbcURL = "jdbc:sqlite:/C:\\Users\\Jean\\Desktop\\myDesktop\\bcs\\2021\\f21\\comp3005_dbms\\project\\BookStore_v2\\bkstr.db";
		//String username = "sqlite";
		//String psswd = "1234";
		
		try {
			
			//Getting the connection
			Connection conn = DriverManager.getConnection(jdbcURL);
			System.out.println("Connection established......");
					
			//Creating the Statement
			Statement stmt = conn.createStatement();
			
			//Query to insert records
			
			/*String query = "INSERT INTO CUSTOMERS(" + "ID, Name, AGE, SALARY, ADDRESS) VALUES "
			+ "(1, 'Amit', 25, 3000, 'Hyderabad'), "
			+ "(2, 'Kalyan', 27, 4000, 'Vishakhapatnam'), " 
			+ "(3, 'Renuka', 30, 5000, 'Delhi'), " 
			+ "(4, 'Archana', 24, 1500, 'Mumbai')," 
			+ "(5, 'Koushik', 30, 9000, 'Kota')," 
			+ "(6, 'Hardik', 45, 6400, 'Bhopal')," 
			+ "(7, 'Trupthi', 33, 4360, 'Ahmedabad')," 
			+ "(8, 'Mithili', 26, 4100, 'Vijayawada')," 
			+ "(9, 'Maneesh', 39, 4000, 'Hyderabad')," 
			+ "(10, 'Rajaneesh', 30, 6400, 'Delhi')," 
			+ "(11, 'Komal', 29, 8000, 'Ahmedabad')," 
			+ "(12, 'Manyata', 25, 5000, 'Vijayawada')";*/
/*			
			String query = "INSERT INTO customer (account_number, cname, caddress, phone, email) VALUES ('" + account_number + "', '" + cname + "', '" + caddress + "', '" + phone_number + "', '" + email + "')";
			
			int i = stmt.executeUpdate(query);
			System.out.println("Rows inserted: "+i);
			
		} catch(Exception e) {
			System.out.println(e);
		}

	}
*/
	
}
