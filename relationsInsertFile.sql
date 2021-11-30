delete from bookstore
delete from book
delete from author
delete from publisher 
delete from customer 
delete from transaction 
delete from reports
delete from shopping_cart
delete from holds
delete from registers
delete from writtenBy
delete from publishedBy

insert into bookstore values ('1001', 'Bob Smith', 'bsmith@gmail.com');

insert into author values ('1002', 'J.K. Rowling', '123 main street');
insert into author values ('1003', 'Phillip K. Dick', '45 second street');
insert into author values ('1004', 'Stephen King', '3 third street');
insert into author values ('1005', 'Suzanne Collins', '18 scallop street');
insert into author values ('1006', 'Jules Verne', '22 scranton ave');

insert into publisher values ('1007', 'Bloomsbury Publishing', 'New York', '123-456-789', 'bloomsburypublishing@gmail.com', 0);
insert into publisher values ('1008', 'David Mckay Publications', 'New York', '123-456-789', 'davidmckaypublications@gmail.com', 0);
insert into publisher values ('1009', 'Viking', 'London', '123-456-789', 'viking@gmail.com', 0);
insert into publisher values ('1010', 'Scholastic Press', 'Toronto', '123-456-789', 'scholasticpress@gmail.com', 0);
insert into publisher values ('1011', 'Pierre-Jules Hetzel', 'LA', '123-456-789', 'pierrehetzel@gmail.com', 0);


insert into book values ('973-1-60705-015-4', 'Harry Potter and the Philosophers Stone', 'Fantasy Fiction', 223, 19.99, 'J.K. Rowling', 'Bloomsbury Publishing');
insert into book values ('973-1-60705-015-4', 'Blade Runner', 'Fantasy Fiction', 150, 19.99, 'Phillip K. Dick', 'David Mckay Publications');
insert into book values ('973-1-60705-015-4', 'It', 'Horror', 135, 15.99, 'Stephen King', 'Viking');
insert into book values ('973-1-60705-015-4', 'Hunger Games', 'Thriller', 200, 25.99, 'Suzanne Collins', 'Scholastic Press');
insert into book values ('973-1-60705-015-4', 'Journey to the Center of the Earth', 'Thriller', 212, 9.99, 'Jules Verne', 'Pierre-Jules Hetzel');

insert into customer values ('4823744352', 'Joe Shmo', '123 derry lane', '123-456-789', 'joeshmo@gmail.com');
insert into customer values ('4823744353', 'Pennywise', '16 clown street', '987-654-321', 'pennywise@gmail.com');