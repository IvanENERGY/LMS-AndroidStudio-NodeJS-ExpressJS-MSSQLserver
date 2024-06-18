import borrow_history
from add_remove_history import Add_Rm_History
from book import Book
from book_copies import Book_Copies
from library import Library
from reader import Reader
from staff import Staff
from account import Account
import random

from useful_stuff import get_list_from_txt


def generate_sql_staff(account_gen_obj, staff_gen_obj):
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')

    for i in range(staff_gen_obj.number_of_staff):
        first_name = staff_gen_obj.first_names[random.randint(0, len(staff_gen_obj.first_names) - 1)]
        last_name = staff_gen_obj.last_names[random.randint(0, len(staff_gen_obj.last_names) - 1)]
        gender = staff_gen_obj.genders[random.randint(0, 1)]
        email_suffix = staff_gen_obj.email_suffixes[random.randint(0, len(staff_gen_obj.email_suffixes) - 1)]
        sql_statement += ("INSERT INTO staff Values(\'"
                          + str("{:04d}".format(i)) + "\',\'"
                          + first_name + "\',\'"
                          + last_name + "\',\'"
                          + gender + "\',\'"
                          + staff_gen_obj.generate_hkID() + "\',\'"
                          + str(account_gen_obj.generate_random_date(1966, 1, 1, 2002, 1, 1)) + "\',\'"
                          + account_gen_obj.usernames[i] + email_suffix + "\',\'"
                          + staff_gen_obj.generate_address() + "\',\'"
                          + staff_gen_obj.generate_contact_no() + "\',\'"
                          + account_gen_obj.usernames[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_reader(account_gen_obj, reader_gen_obj):
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')

    for i in range(reader_gen_obj.number_of_reader):
        first_name = reader_gen_obj.first_names[random.randint(0, len(reader_gen_obj.first_names) - 1)]
        last_name = reader_gen_obj.last_names[random.randint(0, len(reader_gen_obj.last_names) - 1)]
        gender = reader_gen_obj.genders[random.randint(0, 1)]
        email_suffix = reader_gen_obj.email_suffixes[random.randint(0, len(reader_gen_obj.email_suffixes) - 1)]
        sql_statement += ("INSERT INTO reader Values(\'"
                          + str("{:04d}".format(i)) + "\',\'"
                          + first_name + "\',\'"
                          + last_name + "\',\'"
                          + gender + "\',\'"
                          + reader_gen_obj.generate_hkID() + "\',\'"
                          + str(account_gen_obj.generate_random_date(1966, 1, 1, 2002, 1, 1)) + "\',\'"
                          + account_gen_obj.usernames[i + reader_gen_obj.number_of_reader] + email_suffix + "\',\'"
                          + reader_gen_obj.generate_address() + "\',\'"
                          + reader_gen_obj.generate_contact_no() + "\',\'"
                          + str(random.randint(5, 6)) + "\',\'"
                          + account_gen_obj.usernames[i + reader_gen_obj.number_of_reader]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_account(account_gen_obj):
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')

    for i in range(len(account_gen_obj.usernames)):
        sql_statement += ("INSERT INTO account Values(\'"
                          + account_gen_obj.usernames[i] + "\',\'"
                          + account_gen_obj.generate_random_string(8)
                          + "\',\'" + str(account_gen_obj.generate_random_date(2010, 1, 1, 2023, 1, 1))
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_book():
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')
    book_gen = Book()
    '''
    Title varchar(255) NOT NULL,
    Author varchar(255) NOT NULL,
    Publisher varchar(255),
    ISBN char(13) NOT NULL,
    PublishingYear smallint,
    EditStaffID char(4),'''
    for i in range(len(get_list_from_txt("book_title.txt"))):
        sql_statement += ("INSERT INTO book Values(\'"
                          + book_gen.gen_title_list()[i] + "\',\'"
                          + book_gen.gen_author_list()[i] + "\',\'"
                          + book_gen.gen_publisher_list()[i] + "\',\'"
                          + book_gen.gen_isbn_list()[i] + "\',\'"
                          + book_gen.gen_publish_year_list()[i] + "\',\'"
                          + book_gen.gen_edit_staff_id_list()[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_lib():
    '''
     LibID char(4) NOT NULL,
     Location varchar(255) NOT NULL,
     ContactNo char(8) ,
     Address varchar(255),
     '''
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')
    lib_gen = Library()
    for i in range(4):
        sql_statement += ("INSERT INTO Library Values(\'"
                          + lib_gen.gen_id_list()[i] + "\',\'"
                          + lib_gen.gen_location_list()[i] + "\',\'"
                          + lib_gen.gen_contact_list()[i] + "\',\'"
                          + lib_gen.gen_address_list()[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_book_copies():
    '''
    BarcodeID varchar(10) NOT NULL,
Status char(1) NOT NULL,
ISBN char(13) NOT NULL,
LibID char(4) NOT NULL,
     '''
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')
    copies_gen = Book_Copies()
    for i in range(50):
        sql_statement += ("INSERT INTO BookCopies Values(\'"
                          + copies_gen.barcodes[i] + "\',\'"
                          + copies_gen.statuses[i] + "\',\'"
                          + copies_gen.isbns[i] + "\',\'"
                          + copies_gen.libIds[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


def generate_sql_add_rm_history():
    '''
 AddRemoveRecordID char(4) NOT NULL,
Date date NOT NULL,
BarcodeID varchar(10) not null,
StaffID char(4) ,
     '''
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')
    add_rm_gen = Add_Rm_History()
    for i in range(50):
        sql_statement += ("INSERT INTO AddAndRemoveHistory Values(\'"
                          + add_rm_gen.ids[i] + "\',\'"
                          + add_rm_gen.dates[i] + "\',\'"
                          + add_rm_gen.barcodes[i] + "\',\'"
                          + add_rm_gen.staffIds[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


file = open("preprocess.sql", 'w')
file.close()


def generate_sql_borrow_history():
    '''
BorrowRecordID char(4) NOT NULL,
BorrowDate date NOT NULL,
DueDate date NOT NULL,
RenewalTimes smallint ,
State smallint,
BarCodeID  varchar(10) ,
ReaderId char(4),
     '''
    sql_statement = ""
    sql_file = open("preprocess.sql", 'a')
    borrow_gen = borrow_history.Borrow_History()
    for i in range(50):
        sql_statement += ("INSERT INTO BorrowHistory Values(\'"
                          + borrow_gen.ids[i] + "\',\'"
                          + borrow_gen.bdates[i] + "\',\'"
                          + borrow_gen.ddates[i] + "\',\'"
                          + borrow_gen.renewal_times[i] + "\',\'"
                          + borrow_gen.states[i] + "\',\'"
                          + borrow_gen.barcodes[i] + "\',\'"
                          + borrow_gen.reader_ids[i]
                          + "\')\n")

    sql_file.write(sql_statement)
    sql_file.close()


file = open("preprocess.sql", 'w')
file.close()
account_gen = Account()
staff_gen = Staff(account_gen)
reader_gen = Reader(account_gen)

generate_sql_account(account_gen)
generate_sql_staff(account_gen, staff_gen)
generate_sql_book()
generate_sql_lib()
generate_sql_reader(account_gen, reader_gen)
generate_sql_book_copies()
generate_sql_add_rm_history()
generate_sql_borrow_history()