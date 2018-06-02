import csv
import json


def convert_parse_file(file1, file3):
    with open(file1) as f1:

        with open(file3, "a") as f3:
            lines = f1.readlines().copy()
            for line in lines:
                line = line.split(",")
                line = line[0:5]
                line.append("0")
                line[5] += "\n"
                line = ",".join(line)
                f3.write(line)

def convert_parse_file2(file1, file2):

    with open(file1) as f1:

        with open(file2, "a") as f2:
            lines = f1.readlines()
            print(lines)
            for line in lines:
                line = line.split(",")
                line[0:5] = sorted(line[0:5], reverse=True)
                line = ",".join(line)
                print(line)
                f2.write(str(line))



def parse(file1, file2):
    with open(file1) as json_data:
        x = json.load(json_data)

    f = csv.writer(open(file2, "w"))

    for x in x:
        f.writerow([  # x["_id"],
            # x["name"],
            x["parametry"]["metale"]["nikiel"],
            x["parametry"]["metale"]["miedź"],
            x["parametry"]["metale"]["żelazo"],
            x["parametry"]["metale"]["mangan"],
            x["parametry"]["metale"]["magnez"],
            x["parametry"]["temp1"],
            x["parametry"]["czas1"],
            x["parametry"]["temp2"],
            x["parametry"]["czas2"],
            x["jakość"]])

