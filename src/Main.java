import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
	static ArrayList<Book> books = new ArrayList<Book>();
	
	static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	static ArrayList<Publisher> publishers = new ArrayList<Publisher>();
	
	static ShoppingCart cart = new ShoppingCart();
	
	public static void main(String args[]) throws Exception {
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
		System.out.println("1. Add new books");			
		System.out.println("2. Remove a book");			
		System.out.println("3. View sales reports");	//TODO
		System.out.println("4. Return to portal selection");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		if(selection.equals("1")) {
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
		System.out.println("5. Return to portal selection");
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
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4,5");
			customerPortal();
		}
		
	}
	
	//Owner functions
	static void addBook() {
		//generate account number
		String ISBN = UUID.randomUUID().toString();
		
		System.out.println("What is the title of the Book?");
		Scanner inputTitle = new Scanner(System.in);
		String title = inputTitle.nextLine();
		
		System.out.println("What is the genre of the Book?");
		Scanner inputGenre = new Scanner(System.in);
		String genre = inputTitle.nextLine();
		
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
		String author = inputTitle.nextLine();
		
		System.out.println("Who is the publisher of the Book?");
		Scanner inputPublisher = new Scanner(System.in);
		String publisher = inputTitle.nextLine();		
		
		int quantity = 15;
		
		//add to db
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
			
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
				String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
				String username = "postgres";
				String psswd = "1234";
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
			if(number >=1 && number<=books.size()) {
				//select is valid so now show that single book
				searchQuery("ISBN", books.get(number-1).getISBN());
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
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("title", selection1);
		}else if(selection.equals("2")) {
			System.out.println("\n");
			System.out.println("Enter the Genre of the book(s) you are looking for:");
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("genre", selection1);
		}else if(selection.equals("3")) {
			System.out.println("\n");
			System.out.println("Enter the Price of the book(s) you are looking for:");
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("price", selection1);
		}else if(selection.equals("4")) {
			System.out.println("\n");
			System.out.println("Enter the Author of the book(s) you are looking for:");
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("author_name", selection1);
		}else if(selection.equals("5")) {
			System.out.println("\n");
			System.out.println("Enter the Publisher of the book(s) you are looking for:");
			Scanner input1 = new Scanner(System.in);
			String selection1 = input.nextLine();
			searchQuery("publisher_name", selection1);
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4,5");
			search();
		}
		
	}
	
	static void searchQuery(String type, String input) {
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
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
				searchQuery(type, input);
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
		System.out.println("Enter your email address");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		
		int flag = 0;
	
		for(int i=0; i<customers.size(); i++) {
			if(customers.get(i).getEmail().equals(selection)) {
				//user is registered in the store
				flag = 1;				
			}
		}
		
		if(flag == 1) {
			//process transaction
			System.out.println("You are registered! Lets checkout!");
		}else {
			System.out.println("You must be registered in the store to checkout!");
			register();
		}
		
	}
	
	static void register() {
		System.out.println("Would you like to register with the bookstore?(Y/N)");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		if(selection.toLowerCase().equals("y")) {
			//add user to DB and update customers list 
			//generate account number
			String accountNum = UUID.randomUUID().toString();
			
			//get name
			System.out.println("Enter your first and last name:");
			Scanner inputName = new Scanner(System.in);
			String name = input.nextLine();
			
			//get address
			System.out.println("Enter your address:");
			Scanner inputAddress = new Scanner(System.in);
			String address = input.nextLine();
			
			//get phone
			System.out.println("Enter your phone number:");
			Scanner inputPhone = new Scanner(System.in);
			String phone = input.nextLine();
			
			//get email
			System.out.println("Enter your email:");
			Scanner inputEmail = new Scanner(System.in);
			String email = input.nextLine();
			
			//add to db
			String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
			String username = "postgres";
			String psswd = "1234";
			try {
				Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
				
				String query = "INSERT INTO customer (account_number, name, address, phone, email)" + " VALUES (?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				
				preparedStmt.setString(1, accountNum);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, address);
				preparedStmt.setString(4, phone);
				preparedStmt.setString(5, email);
				
				preparedStmt.execute();
				
				conn.close();
				
				System.out.println("\nYou have been registered in the store!");
				System.out.println("Returning to customer portal!\n");
				customerPortal();
				
				
			}catch(Exception e) {
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
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
			String query = "SELECT * FROM book";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			
			while(test.next()) {
				Book newBook = new Book();
				newBook.setTitle(test.getString("title"));
				newBook.setISBN(test.getString("ISBN"));
				newBook.setGenre(test.getString("genre"));
				newBook.setPrice(test.getInt("price"));
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
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
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
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "1234";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
			String query = "SELECT * FROM publisher";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			
			while(test.next()) {
				Publisher newPublisher = new Publisher();
				newPublisher.setID(test.getString("ID"));
				newPublisher.setName(test.getString("publisher_name"));
				newPublisher.setAddress(test.getString("address"));
				newPublisher.setPhone(test.getString("phone"));
				newPublisher.setEmail(test.getString("email"));
				newPublisher.setBankAccount(test.getString("bank_account"));
				publishers.add(newPublisher);	
			}
			
			conn.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
}
