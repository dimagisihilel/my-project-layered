create database PawnShop;

use PawnShop;

create table user(
                     u_id varchar(10) primary key,
                     username varchar(50),
                     password varchar(20)

);

insert into user Values("U001","ds","1234");
insert into user Values("U002","dsg","890");

create table employee(
                         emp_id varchar(10)primary key,
                         name varchar(50),
                         address varchar(80),
                         position varchar(30),
                         salary decimal (10,2)
);

create table customer(
                         customer_id varchar(10)primary key,
                         name varchar(50),
                         contactNo varchar(15),
                         NIC varchar(20) not null,
                         address varchar(80)

);

create table inventory(
                          inventory_id varchar(10) primary key,
                          customer_id varchar(10),
                          itemType varchar(30),
                          caratValue varchar(8),
                          pricePerGram decimal(12,2),
                          weight decimal(5,2),
                          description varchar(80),
                          AssessedValue decimal(12,2),
                          constraint foreign key(customer_id) references customer(customer_id) on delete cascade on update cascade
);

create table paymentDetails(
                               payment_id varchar(10) primary key,
                               inventory_id varchar(10),
                               dateGranted date,
                               duedate date,
                               constraint foreign key(inventory_id) references inventory(inventory_id) on delete cascade on update cascade
);


create table installments(
                             installment_id int,
                             payment_id varchar(10),
                             amount decimal(12,2),
                             paymentStatus varchar(30),
                             primary key(installment_id,payment_id),
                             constraint foreign key(payment_id) references paymentDetails(payment_id) on delete cascade on update cascade
);
