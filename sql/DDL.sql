create table bookstore
	(bank_account    	decimal(5,2),
	
	);

create table orders(
	transaction_id		varchar(50) 	not null, 				
	total_price			decimal(5, 2)	not null,
	order_status		varchar(50)		default 'on the way',
	date_order			DATE			default CURRENT_TIMESTAMP, 
	primary key (transaction_id)
	
);
		
create table publisher
	(ID			   		varchar(50) not null, 
	 publisher_name		varchar(50) not null,
	 address			varchar(50) not null,
	 phone				varchar(50) not null, 
	 email				varchar(50) not null,
	 bank_account	    decimal(5,2),		 
	 primary key (ID)	
	);
	
create table book
	(
		ISBN			varchar(50) not null,
		title			varchar(50) not null,
		genre			varchar(50) not null,
		pages			int NOT NULL,
		price			decimal(5,2), 
		author_name 		varchar(50) not null,
		publisher_name 		varchar(50) not null,
		quantity		int default 15,
		primary key (ISBN)
	);
	
	
create table customer
	(account_number		varchar(50) not null,
	 name				varchar(50) not null,
	 address			varchar(50) not null,
	 phone				varchar(50) not null, 
	 email				varchar(50) not null,
	 primary key (account_number)		
	);

create table transaction
	(transaction_id		varchar(50) not null, 
	 shipping_number	varchar(50) not null,
	 account_number		varchar(50) not null,
	 
	 primary key (transaction_id)		
	);
	
create table reports
	(transaction_id		varchar(50) not null, 
	 shipping_number	varchar(50) not null,
	 
	 primary key (transaction_id)		
	);
	
create table shopping_cart
	(cart_id			varchar(50) not null, 
	 account_number		varchar(50) not null,
	 
	 primary key (cart_id)		
	);
	
create table holds	
	(cart_id			varchar(50) not null, 
	 ISBN				varchar(50) not null,
	 pages				int NOT NULL,
	 
	 primary key (cart_id)		
	);

create table registers
	(ID			   		varchar(50) not null, 
	 account_number		varchar(50) not null,
	 name				varchar(50) not null,
	 address			varchar(50) not null,
	 phone				varchar(50) not null, 
	 email				varchar(50) not null,
	 primary key (ID)		
	);

create table writtenBy
	(ISBN			varchar(50) not null,
	 ID				varchar(50) not null,
	 name				varchar(50) not null,
	 address			varchar(50) not null,
	 
	 primary key (ISBN)		
	);

create table publishedBy
	(ISBN			varchar(50) not null,
	 ID				varchar(50) not null,
	 name				varchar(50) not null,
	 address			varchar(50) not null,
	 phone				varchar(50) not null, 
	 email				varchar(50) not null,
	 bank_account	    varchar(50) not null,
	 
	 primary key (ISBN)		
	);


	
