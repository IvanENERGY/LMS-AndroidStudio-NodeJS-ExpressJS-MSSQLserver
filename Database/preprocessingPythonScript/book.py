'''
Title varchar(255) NOT NULL,
Author varchar(255) NOT NULL,
Publisher varchar(255),
ISBN char(13) NOT NULL,
PublishingYear smallint,
EditStaffID char(4),
'''
import random

import account
import staff
from useful_stuff import get_list_from_txt
from useful_stuff import get_rand_item_from_list


class Book:
    def __init__(self):
        self.titles = self.gen_title_list()
        self.authors = self.gen_author_list()
        self.publishers = self.gen_publisher_list()
        self.isbns = self.gen_isbn_list()
        self.publish_years = self.gen_publish_year_list()
        self.edit_staff_ids = self.gen_edit_staff_id_list()

    def gen_title_list(self):
        return get_list_from_txt("book_title.txt")

    def gen_author_list(self):
        author_list=[]
        last_names = ["Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"]
        first_names = ["James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph",
                       "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald"]
        for i in range(len(self.gen_title_list())):
            author_list.append(get_rand_item_from_list(first_names) + " " + get_rand_item_from_list(last_names))

        return author_list

    def gen_publisher_list(self):
        res=[]
        publishers = ["Dzanc Books", "Graywolf Press", "Hanging Loose Press", "McSweeney’s", "Penguin Books"]
        for i in range(len(self.gen_title_list())):
            res.append(get_rand_item_from_list(publishers))

        return res

    def gen_isbn_list(self):
        return get_list_from_txt("book_isbn.txt")

    def gen_publish_year_list(self):
        publish_years=[]
        for i in range(len(self.gen_title_list())):
            publish_years.append(str(random.randint(2010, 2022)))

        return publish_years

    def gen_edit_staff_id_list(self):
        res=[]
        account_gen = account.Account()
        staff_gen = staff.Staff(account_gen)

        id_wo_zero = (random.randint(0, staff_gen.number_of_staff - 1))
        for i in range(len(self.gen_title_list())):
            res.append(str("{:04d}".format(id_wo_zero)))

        return res


