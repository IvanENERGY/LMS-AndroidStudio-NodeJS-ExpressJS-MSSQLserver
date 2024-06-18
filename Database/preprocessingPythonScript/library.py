import random



class Library():
    '''
    LibID char(4) NOT NULL,
    Location varchar(255) NOT NULL,
    ContactNo char(8) ,
    Address varchar(255),
    '''  # default 4 lib set

    def __init__(self):
        self.lib_id = self.gen_id_list()
        self.location = self.gen_location_list()
        self.contact_no = self.gen_contact_list()
        self.address = self.gen_address_list()

    def gen_address_list(self):
        return ["13 Chai Kok Road", "35 Tsuen King Road", "4 Yau Street", "42 Fa Yuen Street"]


    def gen_contact_list(self):
        contacts = []
        for i in range(4):
            seven_digit = random.randint(0000000, 9999999)
            contacts.append("2" + str(seven_digit))
        return contacts


    def gen_location_list(self):
        locations = ["Chai Wan",
                     "Tsuen Wan",
                     "Yau Ma Tei",
                     "Mong Kok"]

        return locations


    def gen_id_list(self):
        ids = []
        for i in range(4):
            ids.append(str("{:04d}".format(i)))
        return ids
