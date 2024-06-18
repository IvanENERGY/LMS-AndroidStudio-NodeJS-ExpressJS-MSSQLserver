import datetime
import random

import account
import useful_stuff


class Borrow_History:
    '''
    BorrowRecordID char(4) NOT NULL,
BorrowDate date NOT NULL,
DueDate date NOT NULL,
RenewalTimes smallint ,
State smallint,
BarCodeID  varchar(10) ,
ReaderId char(4),
    '''

    # gen 50 history
    def __init__(self):
        self.ids = self.gen_ids()
        self.bdates = []
        self.ddates = []
        self.states = []
        self.renewal_times = []
        self.gen_bdate_and_ddate_and_states_and_renewal_times()
        self.barcodes = self.gen_barcodes()
        self.reader_ids = self.gen_reader_ids()

    def gen_ids(self):
        res = []
        for i in range(50):
            res.append(str("{:04d}".format(i)))
        return res

    def gen_bdate_and_ddate_and_states_and_renewal_times(self):
        for i in range(50):
            bday = account.Account().generate_random_date(2023, 1, 1, 2023, 3, 3)
            no_of_renewal = random.randint(0, 3)

            random_borrow_days = 14 * (1 + no_of_renewal)

            dday = bday + datetime.timedelta(days=random_borrow_days)
            state = "8"

            self.bdates.append(str(bday))
            self.ddates.append(str(dday))
            self.states.append(state)
            self.renewal_times.append(str(no_of_renewal))

    def gen_barcodes(self):
        res = []
        list = useful_stuff.get_list_from_txt("book_copies_barcode.txt")
        for i in range(50):
            res.append(list[i])
        return res

    def gen_reader_ids(self):
        res = []
        for i in range(50):
            rand_num = random.randint(0, 14)
            res.append(str("{:04d}".format(rand_num)))
        return res
