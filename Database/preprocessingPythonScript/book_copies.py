import random

import useful_stuff


class Book_Copies():
    '''
    BarcodeID varchar(10) NOT NULL,
Status char(1) NOT NULL,
ISBN char(13) NOT NULL,
LibID char(4) NOT NULL,
 # gen 50 copies here
    '''

    def __init__(self):
        file=open("book_copies_barcode.txt",'w')
        file.close()
        self.barcodes = self.gen_bar_code_ids()
        self.statuses = self.gen_statuses()
        self.isbns = self.gen_isbns()
        self.libIds = self.gen_lib_ids()

    def gen_statuses(self):
        avaliable_statuses = ["1", "2", "8"]
        res = []
        for i in range(50):
            res.append(useful_stuff.get_rand_item_from_list(avaliable_statuses))
        return res

    def gen_bar_code_ids(self):
        file=open("book_copies_barcode.txt",'a')

        res_list = []
        for j in range(50):
            bar_code = ""
            for i in range(10):
                bar_code += str(random.randint(0, 9))
            res_list.append(bar_code)
            file.write(bar_code+"\n")
        file.close()
        return res_list


    def gen_isbns(self):
        list = useful_stuff.get_list_from_txt("book_isbn.txt")
        res = []
        for i in range(50):
            res.append(useful_stuff.get_rand_item_from_list(list))
        return res

    def gen_lib_ids(self):
        libids = ["0000", "0001", "0002", "0003"]
        res = []
        for i in range(50):
            res.append(useful_stuff.get_rand_item_from_list(libids))
        return res
