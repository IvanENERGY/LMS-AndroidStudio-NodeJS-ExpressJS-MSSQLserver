'''
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
'''
import account
import random


class Staff():
    def __init__(self, account_gen_obj):
        self.first_names = ["Tsz Chung", "Kin Hang", "Tin Sheng", "Mei Lee", "Tai Man", "Kwok Hei", "Yin Hei",
                            "Ying Nam",
                            "Yik Lam"]
        self.last_names = ["LEE", "CHAN", "HO", "CHEUNG", "WONG", "AU", "YEUNG", "DING", "CHENG", "LAM"]
        self.genders = ["M", "F"]
        self.email_suffixes = ["@gmail.com", "@hotmail.com", "@yahoo.com", "@my.cityu.edu.hk", "@rocketmail.com"]
        self.number_of_staff = 0
        if len(account_gen_obj.usernames) % 2 == 1:
            self.number_of_staff = int((len(account_gen_obj.usernames) + 1) / 2)
        else:
            self.number_of_staff = int(len(account_gen_obj.usernames) / 2)

    def generate_hkID(self):
        res = ""
        prefixes = ["Y", "A", "R", "Z", "B", "C", "D", "E", "J", "K"]
        digits = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
        res += prefixes[random.randint(0, len(prefixes) - 1)]

        for i in range(6):
            res += digits[random.randint(0, 9)]
        res += str(self.calculate_checkdigit(res))
        return res

    def calculate_checkdigit(self, seven_digit):
        sum = 0;
        sum += 9 * 36
        map = {
            "Y": 34,
            "A": 10,
            "R": 27,
            "Z": 35,
            "B": 11,
            "C": 12,
            "D": 13,
            "E": 14,
            "J": 19,
            "K": 20
        }
        sum += map.get(seven_digit[0]) * 8
        for i in range(6):
            sum += (7 - i) * int(seven_digit[i + 1])

        check_digit = 11 - (sum % 11)
        if check_digit == 10:
            return "A"
        elif check_digit == 11:
            return 0
        else:
            return check_digit

    def generate_address(self):
        floor_number = random.randint(1, 33)
        room_number = ["A", "B", "C", "D", "E"][random.randint(0, 4)]
        building_address = \
            ["Hiu Wah House, 22 Hiu Jai Road, Kowloon City", "Sun Yat House,52 Sun Shui Road,Sham Shui Po",
             "Sung Kei House, 31 Sun Wah Road, Kowloon Tong", "Ming Wah House,52 Fan Wai Road, Fan ling",
             "Kin Ming House, 24 Kin Pak Road, Tuen Mun"][random.randint(0, 4)]
        return str(floor_number) + room_number + ", " + building_address

    def generate_contact_no(self):
        prefix = ["5", "6", "9"][random.randint(0, 2)]
        seven_digit = random.randint(0000000, 9999999)
        return prefix + str(seven_digit)
