import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

//for Jean
import java.sql.SQLException;
import java.util.HashMap;
//import java.util.Map;
import java.io.*;


//TODO:
//jean
//checkout(user input - billing and shipping information) + transaction 
	//when checking out, use customer address as default (billing), 
	//ask user if shipping address is the same as billing (print it if you want)
	
	//transfer a percentage of the sales of books published by these publishers.
	//order new books if quantity <= 5

	//update reports
//track order 

//jacob
//sales reports 
//add publisher when owner adds book 

public class Main {

	static ArrayList<Book> books = 					new ArrayList<Book>();
	static ArrayList<Customer> customers = 			new ArrayList<Customer>();
	static ArrayList<Publisher> publishers = 		new ArrayList<Publisher>();
	static ArrayList<Transaction> transactions = 	new ArrayList<Transaction>();
	static Report reports = 						new Report();
	static ShoppingCart cart = 						new ShoppingCart();
	static ArrayList<Book> cartItems = 				new ArrayList<Book>();
	static HashMap<String, String> cartItems = 		new HashMap<String, String>();
	static HashMap<String, String> searchedQuery = 	new HashMap<String, String>();
	
	public static void main(String args[]) throws SQLException {
		
		System.out.println("Welcome to the COMP3005 interactive BookStore. \n");
		loadBooks();
		loadCustomers();
		loadPublishers();
		selectPortal();	
		
	}
	
	
	static void selectPortal() {
		System.out.println("Enter 1 for owners/manager portal");
		System.out.println("Enter 2 for customer portal");
		
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.equals("1")) {
			System.out.println("\n");
			ownerPortal();
		}else if(selection.equals("2")) {
			System.out.println("\n");
			customerPortal();
		}else {
			System.out.println("\nError, Please enter either 1 or 2");
			selectPortal();
		}
	}
	
	static void ownerPortal() {
		System.out.println("What would you like to do?");
		System.out.println("0. View inventory");	
		System.out.println("1. Add new books");			
		System.out.println("2. Remove a book");			
		System.out.println("3. View sales reports");	//TODO
		System.out.println("4. Return to portal selection");
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.equals("0")){
			viewInventory();
		}else if(selection.equals("1")) {
			System.out.println("\n");
			addBook();
		}else if(selection.equals("2")) {
			System.out.println("\n");
			removeBook();
		}else if(selection.equals("3")) {
			System.out.println("\n");
			viewSalesReports();
		}else if(selection.equals("4")) {
			System.out.println("\n");
			selectPortal();
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4");
			ownerPortal();
		}
	}
	
	static void customerPortal() {
		System.out.println("1. Browse all books");
		System.out.println("2. Search for a book");
		System.out.println("3. Checkout");				//TODO
		System.out.println("4. Register");
		System.out.println("5. Track Order");
		System.out.println("6. Return to portal selection");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.equals("1")) {
			System.out.println("\n");
			browseAll();
		}else if(selection.equals("2")) {
			System.out.println("\n");
			search();
		}else if(selection.equals("3")) {
			System.out.println("\n");
			checkout();
		}else if(selection.equals("4")) {
			System.out.println("\n");
			register();
		}else if(selection.equals("5")) {
			System.out.println("\n");
			selectPortal();
		}else if(selection.equals("6")) {
			System.out.println("\n");
			selectPortal();
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4,5,6");
			customerPortal();
		}
		
	}
	
	//Owner functions
	static void viewInventory() {
		
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore"; 
		String username = "postgres";
		String psswd = "5432";

		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			
			//Creating the statement
			Statement stmt = conn.createStatement();
			
			System.out.println("View inventory by:");
			System.out.println("1. Genre");
			System.out.println("2. Publisher");
			System.out.println("3. View All");
			
			Scanner input = new Scanner(System.in);
			String selection = input.nextLine();
			
			if(selection.equals("1")) {
				System.out.println("\n1. Biographies\n2. Children");
				Scanner inp = new Scanner(System.in);
				String sel = inp.nextLine();
				
				if(sel.equals("1")) {
					String filterBy = "bio";
					
				    //Query to retrieve records
					String query = "select * from book where genre ='" + filterBy + "';";
					System.out.println(query);
					
					browseAll();
				}
				
				if(sel.equals("2")) {
					String filterBy = "children";
					
				    //Query to retrieve records
					String query = "select * from book where genre = '" + filterBy +"';";
										
				    					
					filterInventoryBy(query, filterBy, jdbcURL);
					//String query = "create view booksByGenre AS select genre from book where genre='bio'";
					//browseAll();
				}
			}
			
			if(selection.equals("2")) {
				System.out.println("\n1. DS\n2. ACME");
				Scanner inp = new Scanner(System.in);
				String sel = inp.nextLine();
				
				if(sel.equals("1")) {
		
					String filterBy = "DS Publishers";
					
				    //Query to retrieve records
					String query = "select * from book where publisher_name=" + filterBy;
					System.out.println(query);
					
					filterInventoryBy(query, filterBy, jdbcURL);

				}
				
				if(sel.equals("2")) {
				    //Query to retrieve records
					String filterBy = "ACME Publishers";
					
				    //Query to retrieve records
					String query = "select * from book where publisher_name=" + filterBy;
					System.out.println(query);
					
				    //Executing the query
					ResultSet res = stmt.executeQuery(query);
					
					filterInventoryBy(query, filterBy, jdbcURL);

				}
				

			}
			
			if(selection.equals("3")) {
				System.out.println("\n");
				
				String filteredBy = "DS Publishers";
				
			    //Query to retrieve records
				String query = "select * from book";
				System.out.println(query);
				
			    //Executing the query
				ResultSet res = stmt.executeQuery(query);
				
				filterInventoryBy(query, filteredBy, jdbcURL);	
				
				System.out.println("Contents of the Book table:");
				while(res.next()) {
					String isbn = 			res.getString("isbn");
					String title = 			res.getString("title");
					String author_name = 	res.getString("author_name");
					String genre = 			res.getString("genre");
					String pages = 			res.getString("pages");
					String publisher_name = res.getString("publisher_name");
					String quantity = 		res.getString("quantity");
					String price = 			res.getString("price");
					
					System.out.println("ISBN:" + isbn + "\tTitle:" + title + "\tAuthor:" + author_name + "\tGenre:" + genre + "\tPages:" + pages + "\tPublisher:" + publisher_name + "\tQuantity:" + quantity + "\tPrice:" + price);
				}
				System.out.println("Successful!\n");
				ownerPortal();
				
			}else{
				System.out.println("\nError, Please enter either 1,2 or 3");
				ownerPortal();
			}
		
			conn.close();
			
		}catch(Exception e) {
			System.out.println("Error connecting to SQLite DB.");
			System.out.println(e);
		}
	}
	
	
	static void addBook() {
		//generate account number
		String ISBN = UUID.randomUUID().toString();
		
		System.out.println("What is the title of the Book?");
		Scanner inputTitle = new Scanner(System.in);
		String title = inputTitle.nextLine();
		
		System.out.println("What is the genre of the Book?");
		Scanner inputGenre = new Scanner(System.in);
		String genre = inputGenre.nextLine();
		
		System.out.println("How many pages is the Book?");
		Scanner inputPages = new Scanner(System.in);
		String pagesString = inputPages.nextLine();
		int pages = Integer.parseInt(pagesString);

		System.out.println("What is the price of the Book?");
		Scanner inputPrice = new Scanner(System.in);
		String priceString = inputPrice.nextLine();
		double price = Double.parseDouble(priceString);
		
		System.out.println("Who is the author of the Book?");
		Scanner inputAuthor = new Scanner(System.in);
		String author = inputAuthor.nextLine();
		
		System.out.println("Who is the publisher of the Book?");
		Scanner inputPublisher = new Scanner(System.in);
		String publisher = inputPublisher.nextLine();	
		int checkPublisher = 0;
		for(int i=0; i<publishers.size(); i++) {
			System.out.println("--> publisher Arraylist <--");
			//if(publishers.get(i).getName().toLowerCase() == publisher.toLowerCase()) {
				//checkPublisher = 1;
			//}
		}
		if(checkPublisher == 0) {
			//publisher is not current in publishers table so we need to create a publisher 
			createPublisher(publisher);
		}
		
		int quantity = 15;
		
		//add to db
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\Desktop\\myDesktop\\bcs\\2021\\f21\\comp3005_dbms\\project\\BookStore_v2\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/database"//"jdbc:postgresql://localhost:5432/bookstore"; //"jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String username = "pg";
		String psswd = "5432";
		
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			//Connection conn = DriverManager.getConnection(jdbcURL);
			
			String query = "INSERT INTO book (isbn, title, genre, pages, price, author_name, publisher_name, quantity)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1, ISBN);
			preparedStmt.setString(2, title);
			preparedStmt.setString(3, genre);
			preparedStmt.setInt(4, pages);
			preparedStmt.setDouble(5, price);
			preparedStmt.setString(6, author);
			preparedStmt.setString(7, publisher);
			preparedStmt.setInt(8, quantity);
			
			preparedStmt.execute();
			
			conn.close();
			
			//update books
			loadBooks();
			
			System.out.println("\nThe book has been added to the store!");
			System.out.println("Returning to owner portal!\n");
			ownerPortal();
	
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void createPublisher(String publisherName){
		System.out.println("That publisher is not currently saved, lets create a new publisher");
		//generate account number
		String ID = UUID.randomUUID().toString();
		
		System.out.println("What is the publishers address?");
		Scanner inputAddress = new Scanner(System.in);
		String address = inputAddress.nextLine();
		
		System.out.println("What is the publishers email?");
		Scanner inputEmail = new Scanner(System.in);
		String email = inputEmail.nextLine();
		
		System.out.println("What is the publishers phone number?");
		Scanner inputPhone = new Scanner(System.in);
		String phone = inputPhone.nextLine();
		
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/database";//"jdbc:postgresql://localhost:5432/bookstore"; // 
		String username = "pg";
		String psswd = "5432";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);

			String query = "INSERT INTO book (ID, name, address, phone, email, bank_acount)" + " VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setString(1, ID);
			preparedStmt.setString(2, publisherName);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, email);
			preparedStmt.setDouble(5, 0.0);
			
			preparedStmt.execute();
			
			conn.close();
			
			//update books
			loadPublishers();
			
			System.out.println("\nThe publisher has been added to the store!");

		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void removeBook(){
		System.out.println("\nEnter the book(number) you would like to remove: ");
		for(int i=0; i<books.size(); i++) {
			System.out.println((i+1) + "." + books.get(i).getTitle());
		}
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		if(selection.chars().allMatch(Character::isDigit)) {
			int number = Integer.parseInt(selection);
			if(number >=1 && number<=books.size()) {
				//select is valid so now delete that book
				/*
				String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
				String username = "postgres";
				String psswd = "1234";
				*/
				
				String jdbcURL = "jdbc:postgresql://localhost:5432/database"; //"jdbc:postgresql://localhost:5432/bookstore"; // "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
				String username = "pg";
				String psswd = "5432";
				
				try {
					Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			
					String query = "DELETE FROM book WHERE isbn='" + books.get(number-1).getISBN() + "'";								
					Statement statement = conn.createStatement();					
					statement.executeUpdate(query);					
					conn.close();
					
					System.out.println("Book deleted successfully");
					//update the books array 
					loadBooks();
					
				}catch(Exception e) {
					System.out.println(e);
				}
				
			}else {
				System.out.println("\nError, Please enter a valid book number");
				removeBook();
			}
		}else {
			System.out.println("\nError, Please enter a valid book number");
			removeBook();
		}
		
		System.out.println("Returning to owner portal");
		ownerPortal();
		
	}
	
	
	static void viewSalesReports() {
		System.out.println("Which report would you like to view?");
		System.out.println("1. View sales per month");
		System.out.println("2. View all time sales");

		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		
		if(selection.equals("1")) {
			
			/*
			String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
			String username = "postgres";
			String psswd = "1234";
			*/
			//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
			String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore"; //
			String username = "pg";
			String psswd = "5432";
			
			try {
				/*Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);*/
				Connection conn = DriverManager.getConnection(jdbcURL);
							
				String query = "SELECT sum(total_price), EXTRACT(MONTH FROM date_order) "
						+ "FROM orders "
						+ "GROUP BY EXTRACT(MONTH FROM date_order)";
				
				//String query = "select sum(total_price), strftime('%m', date_order) AS month "
						//+ "from orders "
						//+ "group by strftime('%m', date_order);";
				
				
				//String query2 = "create view monthlySales as "
						//+ "select total_price, date_order "
						//+ "from orders "
						//+ "where date_order = ;";
							
				Statement statement = conn.createStatement();
				
				ResultSet test = statement.executeQuery(query);
				
				ArrayList<String> orderView = new ArrayList<String>();
				
				while(test.next()) {
					orderView.add(test.getString("sum"));
					orderView.add(test.getString("date_part"));
				}
				
				System.out.println(orderView);
				
				conn.close();
				
			}catch(Exception e) {
				System.out.println(e);
			}

			
		}else if(selection.equals("2")) {
			

		}else {
			System.out.println("\nError, Please enter either 1 or 2");
			viewSalesReports();
		}
							
	}
	
	//Customer functions
	static void browseAll() {
		System.out.println("\nEnter the book(number) you would like to view: ");
		for(int i=0; i<books.size(); i++) {
			System.out.println((i+1) + "." + books.get(i).getTitle());
		}
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.chars().allMatch(Character::isDigit)) {
			int number = Integer.parseInt(selection);
			if(number >= 1 && number <= books.size()) {
				//select is valid so now show that single book
				searchQuery("ISBN", books.get(number-1).getISBN());
				//searchQuery2("ISBN", books.get(number-1).getISBN());
			}else {
				System.out.println("\nError, Please enter a valid book number");
				browseAll();
			}
		}else {
			System.out.println("\nError, Please enter a valid book number");
			browseAll();
		}
		
	}
	
	static void search() {
		System.out.println("How would you like to search for a book?");
		System.out.println("1. Title");
		System.out.println("2. Genre");
		System.out.println("3. Price");
		System.out.println("4. Author");
		System.out.println("5. Publisher");
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.equals("1")) {
			System.out.println("\n");
			System.out.println("Enter the Title of the book you are looking for:");
			listTitles();
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("title", selection1);
			//searchQuery2("title", selection1);
			
		}else if(selection.equals("2")) {
			System.out.println("\n");
			System.out.println("Enter the Genre of the book(s) you are looking for:");
			listGenres();
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("genre", selection1);
			//searchQuery2("genre", selection1);
			
		}else if(selection.equals("3")) {
			System.out.println("\n");
			System.out.println("Enter the Price of the book(s) you are looking for:");
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("price", selection1);
			//searchQuery2("price", selection1);
			
		}else if(selection.equals("4")) {
			System.out.println("\n");
			System.out.println("Enter the Author of the book(s) you are looking for:");
			listAuthors();
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("author_name", selection1);
			//searchQuery2("author_name", selection1);
			
		}else if(selection.equals("5")) {
			System.out.println("\n");
			System.out.println("Enter the Publisher of the book(s) you are looking for:");
			listPublishers();
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("publisher_name", selection1);
			//searchQuery2("publisher_name", selection1);
			
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4,5");
			search();
		}
		
		
	}
	
	static void searchQuery(String type, String input) {
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db"; 
		String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore"; //
		String username = "pg";
		String psswd = "5432";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			//Connection conn = DriverManager.getConnection(jdbcURL);
						
			String query = "SELECT * FROM book WHERE " + type + "='"  + input + "'" ;
					
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			ArrayList<Book> searchResults = new ArrayList<Book>(); 
			
			while(test.next()) {
				Book newBook = new Book();
				newBook.setTitle(test.getString("title"));
				newBook.setISBN(test.getString("ISBN"));
				newBook.setGenre(test.getString("genre"));
				newBook.setPages(test.getInt("pages"));
				newBook.setPrice(test.getDouble("price"));
				newBook.setAuthor(test.getString("author_name"));
				newBook.setPublisher(test.getString("publisher_name"));
				newBook.setQuantity(test.getInt("quantity"));
				searchResults.add(newBook);
				
			}
					
			conn.close();

			int count = 1;
			System.out.println("\n");
			for(int i=0; i<searchResults.size(); i++) {
				if(searchResults.size() > 1) { 
					System.out.println(count + ".");
					count++;
				}
				
				System.out.println("Title:		" + searchResults.get(i).getTitle());
				System.out.println("Genre:		" + searchResults.get(i).getGenre());
				System.out.println("Pages:		" + searchResults.get(i).getPages());
				System.out.println("Price:          $" + searchResults.get(i).getPrice());
				System.out.println("Author:		" + searchResults.get(i).getAuthor());
				System.out.println("Publisher:	" + searchResults.get(i).getPublisher());
				System.out.println("\n");
			}
			
			System.out.println("\nSelect from the options below:");
			System.out.println("1. Add to cart");
			System.out.println("2. Checkout");
			System.out.println("3. Browse All");
			System.out.println("4. Search");
			
			Scanner input1 = new Scanner(System.in);
			String selection = input1.nextLine();
			
			if(selection.equals("1")) {
				System.out.println("\n");
				addToCart(count, searchResults);
				
				//need to check how many books are being shown, if more than 1, ask user to specify which
				
			}else if(selection.equals("2")) {
				System.out.println("\n");
				checkout();
			}else if(selection.equals("3")) {
				browseAll();
			}else if(selection.equals("4")) {
				System.out.println("\n");
				search();
			}else {
				System.out.println("\nError, Please enter either 1,2,3");
				//searchQuery(type, input);
				searchQuery2(type, input);
			}
			
		}catch(Exception e) {
			System.out.println(e);
		}
			
			
	}
	
	static void addToCart(int count, ArrayList<Book> books) {
		if(count == 1) {
			//add the single book to cart
			cart.getBooks().add(books.get(0));
			
		}else if(count > 1) {
			//ask the user which book out of the list they want to add to cart
			System.out.println("Which book would you like to add to cart?");
			Scanner bookInput = new Scanner(System.in);
			String bookSelection = bookInput.nextLine();
			
			if(bookSelection.chars().allMatch(Character::isDigit)) {
				int number = Integer.parseInt(bookSelection);
				if(number >=1 && number<=books.size()) {
					//select is valid so now add that book to cart 
					cart.getBooks().add(books.get(number-1));
				}else {
					System.out.println("\nError, Please enter a valid book number");
					addToCart(count, books);
				}
			}else {
				System.out.println("\nError, Please enter a valid book number");
				 addToCart(count, books);
			}
		}
	}
	
	static void checkout() {
		
		System.out.println("Are you a member? (y/n)"); 
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(!selection.equals("n") && !selection.equals("y")) {
			customerPortal();
		}
		
		if(selection.equals("n")) {
			register();
		}
		
		if(selection.equals("y")) {
			System.out.println("Enter your email address :");
			Scanner inputEmail = new Scanner(System.in);
			String checkEmail = inputEmail.nextLine();
			
			authenticateCustomer(checkEmail);
			
			int flag = 0;
		
			for(int i=0; i<customers.size(); i++) {
				if(customers.get(i).getEmail().equals(checkEmail)) {//selection)) {
					//user is registered in the store
					flag = 1;	
				}
			}
			
			if(flag == 1) {
				//process transaction
				System.out.println("\nYou are registered!\nLets checkout!\n");
								
				System.out.println("You currently have " + cartItems.size() + " item(s) in your shopping cart: ");
				
				//transaction
				double totalPrice = 0;
				
		        for(HashMap.Entry<String, String> entry: cartItems.entrySet()) {
		            String key = entry.getKey();
		            String value = entry.getValue();
		            System.out.println(key + ": " + value);		
		            
		            String[] arrstr = value.split("-");
		            totalPrice += Double.parseDouble(arrstr[6]);
	
		        }
		        
		        
		        double tx = 1.13;
		        double totalTax = totalPrice * tx;
		        String bill =String.format("%1.2f",totalTax); 
		        System.out.println("Total price is $" + bill + ".");
			
			}else{
				System.out.println("You must be registered in the store to checkout!");
				register();
			}
		}		
	}
	
	
	static void authenticateCustomer(String sel) {
		
		// lets read specific row on the database
	    Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
	    	String query = "Select email from customer where email = ? "; 
	    	ps = conn.prepareStatement(query); 
	    	ps.setString(1, sel);
	    	rs = ps.executeQuery(); 
	    	
	    	// we are reading one row, so no need to loop 
	    	System.out.println("Checking Customer Database.\n");
	    	String email = rs.getString(1); 
	    	System.out.println(email + ", hey we recognize that email!");
	    	
	    } catch(SQLException e) {
	    	System.out.println("You are not in our database.");
	    	//System.out.println(e.toString());
	    } finally {
	    	// close connections
	    	try{
	    		rs.close();
	    		ps.close();
	    		conn.close(); 
	    		
	    	} catch (SQLException e) {
	    		// TODO: handle exception
	    		System.out.println(e.toString());
	    	}	
	    }    
	}

	
	
	static void register() {
		
		System.out.println("Would you like to register with the bookstore?(Y/N)");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.toLowerCase().equals("y")) {
			//add user to DB and update customers list 
			//generate account number
			String account_number = UUID.randomUUID().toString();
			
			//get name
			System.out.println("Enter your first and last name:");
			Scanner inputName = new Scanner(System.in);
			String name = inputName.nextLine();
			
			//get address
			System.out.println("Enter your address:");
			Scanner inputAddress = new Scanner(System.in);
			String address = inputAddress.nextLine();
			
			//get phone
			System.out.println("Enter your phone number:");
			Scanner inputPhone = new Scanner(System.in);
			String phone = inputPhone.nextLine();
			
			//get email
			System.out.println("Enter your email:");
			Scanner inputEmail = new Scanner(System.in);
			String email = inputEmail.nextLine();
			
			//add to db
			/*
			String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
			String username = "postgres";
			String psswd = "1234";
			*/
			
			//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
			String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore";
			String username = "pg";
			String psswd = "5432";
			
			try {
				Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);

				
				String query = "INSERT INTO customer (account_number, name, address, phone, email)" + " VALUES (?, ?, ?, ?, ?)";

				//Creating the Statement
								
				PreparedStatement preparedStmt = conn.prepareStatement(query);	
				preparedStmt.setString(1, account_number);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, address);
				preparedStmt.setString(4, phone);
				preparedStmt.setString(5, email);
				preparedStmt.execute();
						
				//int i = stmt.executeUpdate(query);

				//System.out.println("Rows inserted: " + i);

				conn.close();
				
				System.out.println("\nYou have been registered in the store!");
				System.out.println("Returning to customer portal!\n");
				customerPortal();
							
			}catch(Exception e) {
				System.out.println("CATCHING!!!");
				System.out.println(e);
			}
			
			//update customers list after adding a customer 
			loadCustomers();
			
		}else if(selection.toLowerCase().equals("n")) {
			//user does not want to register 
			System.out.println("Returning to menu");
			customerPortal();
		}else {
			System.out.println("Sorry that is not a valid entry, input either Y/N \n");
			register();
		}
	}
	
	//helper functions
	static void loadBooks() {
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore"; 
		String username = "pg";
		String psswd = "5432";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			//Connection conn = DriverManager.getConnection(jdbcURL);
						
			String query = "SELECT * FROM book";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			
			while(test.next()) {
				Book newBook = new Book();
				newBook.setTitle(test.getString("title"));
				newBook.setISBN(test.getString("ISBN"));
				newBook.setGenre(test.getString("genre"));
				newBook.setPrice(test.getFloat("price"));
				newBook.setAuthor(test.getString("author_name"));
				newBook.setPublisher(test.getString("publisher_name"));
				newBook.setPrice(test.getInt("quantity"));
				books.add(newBook);
				
			}
			
			conn.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void loadCustomers() {
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore"; 
		String username = "pg";
		String psswd = "5432";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			//Connection conn = DriverManager.getConnection(jdbcURL);
						
			String query = "SELECT * FROM customer";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			
			while(test.next()) {
				Customer newCustomer = new Customer();
				newCustomer.setAccount_number(test.getString("account_number"));
				newCustomer.setName(test.getString("name"));
				newCustomer.setAddress(test.getString("address"));
				newCustomer.setPhone_number(test.getString("phone"));
				newCustomer.setEmail(test.getString("email"));
				customers.add(newCustomer);	
			}
			
			conn.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void loadPublishers() {
		/*
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		*/
		//String jdbcURL = "jdbc:sqlite://C:\\Users\\Jean\\eclipse-workspace\\BookStore_v3\\src\\bkstr.db";
		String jdbcURL = "jdbc:postgresql://localhost:5432/bookstore";
		String username = "pg";
		String psswd = "5432";
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			//Connection conn = DriverManager.getConnection(jdbcURL);
						
			String query = "SELECT * FROM publisher";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			
			while(test.next()) {
				Publisher newPublisher = new Publisher();
				//System.out.println("New publisher created");
				
				newPublisher.setID(test.getString("ID"));
				newPublisher.setName(test.getString("publisher_name"));
				newPublisher.setAddress(test.getString("address"));
				newPublisher.setPhone(test.getString("phone"));
				newPublisher.setEmail(test.getString("email"));
				newPublisher.setBankAccount(test.getDouble("bank_account"));
				publishers.add(newPublisher);	
	
			}
			
			conn.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	

	static void filterInventoryBy(String q, String f, String j) {
		try {
			
			Connection conn = DriverManager.getConnection(j);
			
			//Creating the statement
			Statement stmt = conn.createStatement();
			
			System.out.println("--> " + q);
			System.out.println("--> " + f);
			System.out.println("--> " + j);
			
		    //Executing the query
			ResultSet res = stmt.executeQuery(q);
			System.out.println("Inventory filtered by " + f + ":");
			
			while(res.next()) {
				String isbn = 			res.getString("isbn");
				String title = 			res.getString("title");
				String author_name = 	res.getString("author_name");
				String genre = 			res.getString("genre");
				String pages = 			res.getString("pages");
				String publisher_name = res.getString("publisher_name");
				String quantity = 		res.getString("quantity");
				String price = 			res.getString("price");
				
				System.out.println("ISBN:" + isbn + "\tTitle:" + title + "\tAuthor:" 
				+ author_name + "\tGenre:" + genre + "\tPages:" + pages + "\tPublisher:" 
						+ publisher_name + "\tQuantity:" + quantity + "\tPrice:" + price);
			}			
			System.out.println("Successful!\n");
			ownerPortal();
			conn.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	static void listTitles() {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
	    	String query = "select title from book group by title;";
	    	
	    	ps = conn.prepareStatement(query);
	    	rs = ps.executeQuery();
	    	System.out.println("ALL TITLES:\n");
	    	
	    	while(rs.next()) {
	    		String title = 	rs.getString("title");
	    		System.out.println("Title: " + title);
    		
	    	}
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }	    
	}

	static void listGenres() {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {

	    	String query = "select genre from book group by genre;";
	    	ps = conn.prepareStatement(query);
	    	rs = ps.executeQuery();
	    	System.out.println("ALL GENRES\n");
	    	
	    	while(rs.next()) {
	    		String genre = 	rs.getString("genre");
	    		System.out.println("Genre: " + genre);  		
	    	}
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }	    
	}
	
	
	static void listAuthors() {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
	    	String query = "select author_name from book group by author_name;";
	    	
	    	ps = conn.prepareStatement(query);
	    	rs = ps.executeQuery();
	    	System.out.println("ALL AUTHORS:\n");
	    	
	    	while(rs.next()) {
	    		String authors = 	rs.getString("author_name");
	    		System.out.println("Genre: " + authors); 	
	    		
	    	}
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }	    
	}
	
	static void listPublishers() {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
	    	String query = "select publisher_name from book group by publisher_name;";
	    	
	    	ps = conn.prepareStatement(query);
	    	rs = ps.executeQuery();
	    	System.out.println("ALL PUBLISHERS\n");
	    	
	    	while(rs.next()) {
	    		String publishers = rs.getString("publisher_name");
	    		System.out.println("Publisher: " + publishers);
	    	}
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }	    
	}
	
	
	static void addToCart2(String bookisbn) {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
			String query = "select * from book where isbn ='"  + bookisbn + "';" ;
			
	    	ps = conn.prepareStatement(query);
	    	rs = ps.executeQuery();
	    	
	    	//while(rs.next()) {    
    		String isbn = 		rs.getString("isbn");
	        String title = 		rs.getString("title"); 
	        String genre = 		rs.getString("genre"); 
	        String pages = 		rs.getString("pages"); 
	        String price = 		rs.getString("price"); 
	        String author = 	rs.getString("author_name");
	        String publisher = 	rs.getString("publisher_name");
	        int	quantity = 		rs.getInt("quantity");
	        
	        System.out.println("\nFollowing item will be added to your shopping cart:\n");
			System.out.println(isbn + " - "  + title + " - " + author + " - " + genre + " - " + pages + " - " + publisher + " - " + quantity + " - " + price);
			
	    	String k = isbn;// + " - ";
	    	String v = title + " - " + author + " - " + genre + " - " + pages + " - " + publisher + " - " + quantity + " - " + price;
	    	cartItems.put(k, v);
	    	
			System.out.println("\nWhat would you like to do next? Type in a number to make your selection from the menu.");
			customerPortal();    	
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    	
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }    
	}
	
	static void searchQuery2(String type, String input) {
		
		Connection conn = DbConnect.connect(); 
	    PreparedStatement ps = null; 
	    ResultSet rs = null; 
	    
	    try {
			String query = "SELECT * FROM book WHERE " + type + "='"  + input + "';" ;

	    	ps = conn.prepareStatement(query);
	
	    	rs = ps.executeQuery();
	   
	    	
	    	while(rs.next()) {    
	    		String isbn = 		rs.getString("isbn");
    	        String title = 		rs.getString("title"); 
    	        String genre = 		rs.getString("genre"); 
    	        String pages = 		rs.getString("pages"); 
    	        String price = 		rs.getString("price"); 
    	        String author = 	rs.getString("author_name");
    	        String publisher = 	rs.getString("publisher_name");
    	        int	quantity = 		rs.getInt("quantity");
    	        
    	        String bookRecord = isbn + " - " + title + " - " + author + " - " + genre + " - " + pages + " - " + publisher + " - " + quantity + " - " + price;
    	        
    			System.out.println(bookRecord);
    			
    			searchedQuery.put(isbn, bookRecord);
	    		
	    	}
	    	
	    	System.out.println("\nLike what you see? Go ahead and type the book's isbn to add it to your shopping cart.");
	    	System.out.print("Hit 'x' to search for a different genre: ");

			Scanner inputBuy = new Scanner(System.in);
			String selectionBuy = inputBuy.nextLine();
			
			if(searchedQuery.containsKey(selectionBuy)) {
				addToCart2(selectionBuy);
				
			}else if(selectionBuy.equals("x")) {
				search();
				
			}else {
				System.out.println("Invalid entry");
				searchQuery2(type, input);				
			}
	    	
	    } catch(SQLException e) {
	    	System.out.println(e.toString());
	    	
	    } finally {
	    	try {
	    		rs.close();
	    		ps.close();
	    		conn.close();
	    		
	    	} catch(SQLException e) {
	    		
	    		System.out.println(e.toString());	
	    	}	
	    }
	}
	
    static void insertOrder(String bookisbn, int q, String price, String customer_id, Connection c){

    	PreparedStatement ps = null; 
    	try {
    		String transaction_number = UUID.randomUUID().toString();

	        
	        String query2 = "insert into orders (transaction_id, account_number, isbn_order, quantity, total_price) values (?, ?, ?, ?, ?);";
	        ps = c.prepareStatement(query2);
	        
	        ps.setString(1, transaction_number);
	        ps.setString(2, customer_id);
	        ps.setString(3, bookisbn);
	        ps.setInt(4, q);
	        ps.setString(5, price);
	        ps.execute();
	        
	        System.out.println("Order placed. Remember your Transaction number: " + transaction_number);
	        
    	} catch(SQLException e) {
    		System.out.println(e.toString());
    	  } finally {
    		  try{
    			  ps.close();
    			  c.close();
    			  
    		  } catch(SQLException e) {
    			  System.out.println(e.toString());
    			  
    		  }
    		  customerPortal();
    	  }
    }

	
	
}