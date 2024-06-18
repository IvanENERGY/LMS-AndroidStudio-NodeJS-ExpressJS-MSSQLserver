import random


def get_rand_item_from_list(list):
    return list[random.randint(0, len(list) - 1)]


def get_list_from_txt(filename):
    list = []
    file = open(filename, 'r')
    for record in file.readlines():
        if record.__contains__('\n'):
            record = record[0:record.index("\n")]
        list.append(record)
    file.close()
    return list


