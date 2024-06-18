import random

import account
import useful_stuff


class Add_Rm_History():
    '''
    AddRemoveRecordID char(4) NOT NULL,
    Date date NOT NULL,
    BarcodeID varchar(10) not null,
    StaffID char(4) ,
    '''  ##gen 50 records here

    def __init__(self):
        self.ids = self.gen_ids()
        self.dates = self.gen_dates()
        self.barcodes = self.gen_barcodes()
        self.staffIds = self.gen_staff_ids()

    def gen_ids(self):
        res = []
        for i in range(50):
            res.append(str("{:04d}".format(i)))
        return res

    def gen_dates(self):
        res = []
        for i in range(50):
            res.append(str(account.Account().generate_random_date(2023, 1, 1, 2023, 3, 14)))
        return res

    def gen_barcodes(self):
        res = []
        for i in range(50):
            rand_num = random.randint(0, 49)
            bar_code_selected = useful_stuff.get_list_from_txt("book_copies_barcode.txt")[rand_num]
            res.append(bar_code_selected)
        return res

    def gen_staff_ids(self):
        res = []
        for i in range(50):
            rand_num=random.randint(0,14)
            res.append(str("{:04d}".format(rand_num)))
        return res
