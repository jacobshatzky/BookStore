import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static ArrayList<String> books = new ArrayList<String>(); 
	static ArrayList<String> isbns = new ArrayList<String>(); 
	static ArrayList<String> shoppingCart = new ArrayList<String>(); 
	public static void main(String args[]) throws Exception {
		System.out.println("Welcome to the COMP3005 interactive BookStore. \n");
		loadBooks();
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
			viewPublishers();
		}else if(selection.equals("4")) {
			System.out.println("\n");
			viewSalesReports();
		}else {
			System.out.println("\nError, Please enter either 1,2,3,4");
			ownerPortal();
		}
	}
	
	static void customerPortal() {
		System.out.println("1. Browse all books");
		System.out.println("2. Search for a book");
		System.out.println("3. Checkout");
		System.out.println("3. Register");
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
	
	static void viewPublishers() {
		
	}
	
	static void viewSalesReports() {
		
	}
	
	//Customer functions
	static void browseAll() {
		System.out.println("\nEnter the book(number) you would like to view: ");
		for(int i=0; i<books.size(); i++) {
			System.out.println((i+1) + "." + books.get(i));
		}
		
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		if(selection.chars().allMatch(Character::isDigit)) {
			int number = Integer.parseInt(selection);
			if(number >=1 && number<=books.size()) {
				//select is valid so now show that single book
				viewBook(isbns.get(number-1));
			}else {
				System.out.println("\nError, Please enter a valid book number");
				browseAll();
			}
		}else {
			System.out.println("\nError, Please enter a valid book number");
			browseAll();
		}
		
	}
	
	static void viewBook(String isbn) {
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "M1ckey45";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
			String query = "SELECT * FROM book WHERE ISBN='" + isbn + "'";
						
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
			
			for(int i=0; i<viewBook.size(); i++) {
				System.out.println(viewBook.get(i));
			}
			
			System.out.println("\nSelect from the options below:");
			System.out.println("1. Add to cart");
			System.out.println("2. Checkout");
			System.out.println("3. Go back");
			
			Scanner input = new Scanner(System.in);
			String selection = input.nextLine();
			
			if(selection.equals("1")) {
				System.out.println("\n");
				shoppingCart.add(isbn);
				System.out.println("Book adding to shopping cart!");
				viewBook(isbn);
			}else if(selection.equals("2")) {
				System.out.println("\n");
				checkout();
			}else if(selection.equals("3")) {
				System.out.println("\n");
				browseAll();
			}else {
				System.out.println("\nError, Please enter either 1,2,3");
				viewBook(isbn);
			}
			
		}catch(Exception e) {
			System.out.println(e);
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
		
	}
	
	static void checkout() {
		System.out.println("Enter your email address");
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
	}
	
	static void register() {
		
	}
	
	//helper functions
	static void loadBooks() {
		String jdbcURL = "jdbc:postgresql://localhost:5432/Bookstore";
		String username = "postgres";
		String psswd = "M1ckey45";
		try {
			Connection conn = DriverManager.getConnection(jdbcURL, username, psswd);
						
			String query = "SELECT * FROM book";
						
			Statement statement = conn.createStatement();
			
			ResultSet test = statement.executeQuery(query);
			
			while(test.next()) {
				books.add(test.getString("title"));
				isbns.add(test.getString("ISBN"));
			}
			
			conn.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
