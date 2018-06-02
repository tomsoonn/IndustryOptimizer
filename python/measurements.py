
import numpy as np

data = list([])


def measure(file, measurements_ammount):
    with open(file) as f:
        lines = f.readlines()
        for line in lines:
            line = str(line)
            line = line.split(",")
            data.append(line)

    m1 = [float(d[0]) for d in data]
    m2 = [float(d[1]) for d in data]
    m3 = [float(d[2]) for d in data]
    m4 = [float(d[3]) for d in data]
    m5 = [float(d[4]) for d in data]
    temp1 = [float(d[5]) for d in data]
    temp2 = [float(d[7]) for d in data]
    t1 = [float(d[6]) for d in data]
    t2 = [float(d[8]) for d in data]
    mark = [float(d[9]) for d in data]

    avg_metal1 = sum(m1) / measurements_ammount
    avg_metal2 = sum(m2) / measurements_ammount
    avg_metal3 = sum(m3) / measurements_ammount
    avg_metal4 = sum(m4) / measurements_ammount
    avg_metal5 = sum(m5) / measurements_ammount
    avg_temp1 = sum(temp1) / measurements_ammount
    avg_temp2 = sum(temp2) / measurements_ammount
    avg_time1 = sum(t1) / measurements_ammount
    avg_time2 = sum(t2) / measurements_ammount
    avg_mark = sum(mark) / measurements_ammount

    m1 = np.array(m1)
    m2 = np.array(m2)
    m3 = np.array(m3)
    m4 = np.array(m4)
    m5 = np.array(m5)
    temp1 = np.array(temp1)
    temp2 = np.array(temp2)
    t1 = np.array(t1)
    t2 = np.array(t2)
    mark = np.array(mark)


    with open("output/statistics.txt", "a") as f:
        f.write("Średnia zawartość metalu 1 : " + str(avg_metal1) + "\n")
        f.write("Średnia zawartość metalu 2 : " + str(avg_metal2) + "\n")
        f.write("Średnia zawartość metalu 3 : " + str(avg_metal3) + "\n")
        f.write("Średnia zawartość metalu 4 : " + str(avg_metal4) + "\n")
        f.write("Średnia zawartość metalu 5 : " + str(avg_metal5) + "\n")
        f.write("Średnia wartość temperatury 1 : " + str(avg_temp1) + "\n")
        f.write("Średnia wartość temperatury 2 : " + str(avg_temp2) + "\n")
        f.write("Średni czas ogrzewania 1 : " + str(avg_time1) + "\n")
        f.write("Sredni czas ogrzewania 2 :" + str(avg_time2) + "\n")
        f.write("Srednia ocena w skali 1-5:" + str(avg_mark) +"\n" )

        f.write("\n" )

        f.write("Maksymalna zawartość metalu 1 : " + str(max(m1)) + "\n")
        f.write("Maksymalna zawartość metalu 2 : " + str(max(m2)) + "\n")
        f.write("Maksymalna zawartość metalu 3 : " + str(max(m3)) + "\n")
        f.write("Maksymalna zawartość metalu 4 : " + str(max(m4)) + "\n")
        f.write("Maksymalna zawartość metalu 5 : " + str(max(m5)) + "\n")
        f.write("Maksymalna wartość temperatury 1 : " + str(max(temp1)) + "\n")
        f.write("Maksymalna wartość temperatury 2 : " + str(max(temp2)) + "\n")
        f.write("Maksymalna czas ogrzewania 1 : " + str(max(t1)) + "\n")
        f.write("Maksymalna czas ogrzewania 2 :" + str(max(t2)) + "\n")
        f.write("Maksymalna ocena w skali 1-5:" + str(max(mark)) + "\n")

        f.write("\n")

        f.write("Minimalna zawartość metalu 1 : " + str(min(m1)) + "\n")
        f.write("Minimalna zawartość metalu 2 : " + str(min(m2)) + "\n")
        f.write("Minimalna zawartość metalu 3 : " + str(min(m3)) + "\n")
        f.write("Minimalna zawartość metalu 4 : " + str(min(m4)) + "\n")
        f.write("Minimalna zawartość metalu 5 : " + str(min(m5)) + "\n")
        f.write("Minimalna wartość temperatury 1 : " + str(min(temp1)) + "\n")
        f.write("Minimalna wartość temperatury 2 : " + str(min(temp2)) + "\n")
        f.write("Minimalna czas ogrzewania 1 : " + str(min(t1)) + "\n")
        f.write("Minimalna czas ogrzewania 2 :" + str(min(t2)) + "\n")
        f.write("Minimalna ocena w skali 1-5:" + str(min(mark)) + "\n")

        f.write("\n")


        f.write("Odchylenie standardowe zawartości metalu 1 : " + str(min(m1)) + "\n")
        f.write("Odchylenie standardowe zawartości metalu 2 : " + str(min(m2)) + "\n")
        f.write("Odchylenie standardowe zawartości metalu 3 : " + str(min(m3)) + "\n")
        f.write("Odchylenie standardowe zawartości metalu 4 : " + str(min(m4)) + "\n")
        f.write("Odchylenie standardowe zawartości metalu 5 : " + str(min(m5)) + "\n")
        f.write("Odchylenie standardowe temperatury 1 : " + str(min(temp1)) + "\n")
        f.write("Odchylenie standardowe temperatury 2 : " + str(min(temp2)) + "\n")
        f.write("Odchylenie standardowe czasu ogrzewania 1 : " + str(min(t1)) + "\n")
        f.write("Odchylenie standardowe czasu ogrzewania 2 :" + str(min(t2)) + "\n")
        f.write("Odchylenie standardowe oceny w skali 1-5:" + str(min(mark)) + "\n")

        f.write("\n")



#measure("test.csv",15 )

