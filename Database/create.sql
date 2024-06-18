
create table Account(
Ac varchar(10) NOT NULL,
Pw varchar(10) NOT NULL,
LastLogin date ,
PRIMARY KEY (Ac)
);

create table Staff(
StaffId char(4) NOT NULL,
FirstName varchar(255) NOT NULL,
LastName varchar(255),
Gender char(1) ,
HKID varchar(8),
DOB DATE,
EmailAddress varchar(255) ,
Address varchar(255),
ContactNo  char(8),
Ac varchar(10) ,
PRIMARY KEY (StaffID),
FOREIGN KEY (Ac) REFERENCES Account(Ac)
);
create table Book(
Title varchar(255) NOT NULL,
Author varchar(255) NOT NULL,
Publisher varchar(255),
ISBN char(13) NOT NULL,
PublishingYear year,
EditStaffID char(4),
PRIMARY KEY (ISBN),
FOREIGN KEY (EditStaffID) REFERENCES Staff(StaffID)
);
create table Library(
LibID char(4) NOT NULL,
Location varchar(255) NOT NULL,
ContactNo char(8) ,
Address varchar(255),
PRIMARY KEY (LibID)

);
create table Reader(
ReaderId char(4) NOT NULL,
FirstName varchar(255) NOT NULL,
LastName varchar(255),
Gender char(1) ,
HKID varchar(8),
DOB DATE,
EmailAddress varchar(255) ,
Address varchar(255),
ContactNo  char(8),
MaxQuota smallint,
Ac varchar(10) ,
PRIMARY KEY (ReaderID),
FOREIGN KEY (Ac) REFERENCES Account(Ac)
);
create table BookCopies(
BarcodeID varchar(10) NOT NULL,
Status char(1) NOT NULL, --1:ava 2:borrowout 3:onreturnrequest 8:del  
ISBN char(13) NOT NULL,
LibID char(4) NOT NULL,
PRIMARY KEY (BarcodeID),
FOREIGN KEY (ISBN) REFERENCES Book(ISBN),
FOREIGN KEY (LibID) REFERENCES Library(LibID)
);
create table AddAndRemoveHistory(
AddRemoveRecordID char(4) NOT NULL,
Date date NOT NULL,
BarcodeID varchar(10) not null,
StaffID char(4) ,
PRIMARY KEY (AddRemoveRecordID),
FOREIGN KEY (BarcodeID) REFERENCES BookCopies(BarcodeID),
FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);
create table BorrowHistory(
BorrowRecordID char(4) NOT NULL,
BorrowDate date NOT NULL,
DueDate date NOT NULL,
RenewalTimes smallint ,
State smallint, --1:activerecord 8:finished
BarCodeID  varchar(10) ,
ReaderId char(4),
PRIMARY KEY (BorrowRecordID),
FOREIGN KEY (BarcodeID) REFERENCES BookCopies(BarcodeID),
FOREIGN KEY (ReaderId) REFERENCES Reader(ReaderId)
);
