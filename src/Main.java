import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
	static ArrayList<Book> books = new ArrayList<Book>();
	
	static ArrayList<Customer> customers = new ArrayList<Customer>();
	
	static ArrayList<Publisher> publishers = new ArrayList<Publisher>();
	
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
		System.out.println("3. View all publishers");
		System.out.println("4. View sales reports");
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
		}else {
			System.out.println("\nError, Please enter either 1,2,3");
			ownerPortal();
		}
	}
	
	static void customerPortal() {
		System.out.println("1. Browse all books");
		System.out.println("2. Search for a book");
		System.out.println("3. Checkout");
		System.out.println("4. Register");
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
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4");
			customerPortal();
		}
		
	}
	
	//Owner functions
	static void addBook() {
		
	}
	
	static void removeBook(){
		
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
			
			ArrayList<String> viewBook = new ArrayList<String>(); 
			
			while(test.next()) {
				viewBook.add("Title:		" + test.getString("title"));
				viewBook.add("Genre:		" + test.getString("genre"));
				viewBook.add("Pages:		" + test.getString("pages"));
				viewBook.add("Price:          $" + test.getString("price"));
				viewBook.add("Author:		" + test.getString("author_name"));
				viewBook.add("Publisher:	" + test.getString("publisher_name"));
				
			}
			
			conn.close();
			
			System.out.println("\n");
			for(int i=0; i<viewBook.size(); i++) {
				System.out.println(viewBook.get(i));
				
				if(viewBook.get(i).charAt(2) == 'b') {
					System.out.println("\n");
				}
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
				//shoppingCart.add(isbn);
				//System.out.println("Book added to shopping cart!");
				//searchQuery(type, input);
			}else if(selection.equals("2")) {
				System.out.println("\n");
				checkout();
			}else if(selection.equals("3")) {
				System.out.println("\n");
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
							
				String query = "INSERT INTO customer VALUES ('" + accountNum + "','" + name + "','" + address + "','" + phone + "','" + email + "')";
				Statement statement = conn.createStatement();
				
				statement.executeQuery(query);
				
				conn.close();
				
				System.out.println("\nYou have been registered in the store!");
				System.out.println("Lets go back to checkout!\n");
				checkout();
				
				
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
