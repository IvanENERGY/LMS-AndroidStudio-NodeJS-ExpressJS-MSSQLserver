# Target is to make many INSERT INTO account Values('username','password','logindate') statment
import random
import datetime


class Account:

    def __init__(self):
        self.usernames = []
        username_file = open("account_username.txt", 'r')
        for record in username_file.readlines():
            if record.__contains__('\n'):
                record = record[0:record.index("\n") - 1]
            self.usernames.append(record)
        username_file.close()

    def generate_random_string(self,strlen):
        res = ""
        avaliable_char = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                          "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                          "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "!", "@",
                          "#", "$", "%", "^", "&", "*", "A", "B", "C", "D", "E", "F", "G", "H",
                          "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                          "W", "X", "Y", "Z"]

        for i in range(strlen):
            res += avaliable_char[random.randint(0, len(avaliable_char) - 1)]
        return res

    def generate_random_date(self,start_y, start_m, start_d, end_y, end_m, end_d):
        start_date = datetime.date(start_y, start_m, start_d)
        end_date = datetime.date(end_y, end_m, end_d)

        time_between_dates = end_date - start_date
        days_between_dates = time_between_dates.days
        random_number_of_days = random.randrange(days_between_dates)
        random_date = start_date + datetime.timedelta(days=random_number_of_days)

        return random_date

