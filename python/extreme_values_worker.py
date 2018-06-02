
task_file = "task_folder/task.txt"
result_extreme_file = "output/extreme.csv"

def temp_extreme(lines):
    line_to_show = list([])
    for i in range( len(lines)):
        lines[i] = lines[i].split(",")
        lines[i] = [float(i) for i in lines[i]]
    temp_list = list([])
    for line in lines:
        temp_list.append(line[5] + line[7])
    max_temp = max(temp_list)
    min_temp = min(temp_list)

    for i, elem in enumerate(temp_list):
        if(elem == min_temp or elem == max_temp):
            line_to_show.append(lines[i])
    with open(result_extreme_file ,"w"):
        pass
    with open(result_extreme_file, "a") as f:
        for line in line_to_show:
            line = [str(i) for i in line]
            line = ",".join(line)
            f.write(line + "\n")

def time_extreme(lines):
    line_to_show = list([])
    for i in range( len(lines)):
        lines[i] = lines[i].split(",")
        lines[i] = [float(i) for i in lines[i]]
    temp_list = list([])
    for line in lines:
        temp_list.append(line[6] + line[8])
    max_temp = max(temp_list)
    min_temp = min(temp_list)

    for i, elem in enumerate(temp_list):
        if(elem == min_temp or elem == max_temp):
            line_to_show.append(lines[i])
    with open(result_extreme_file ,"w"):
        pass
    with open(result_extreme_file, "a") as f:
        for line in line_to_show:
            line = [str(i) for i in line]
            line = ",".join(line)
            f.write(line + "\n")

def metals_extreme(lines):
    line_to_show = list([])
    for i in range(len(lines)):
        lines[i] = lines[i].split(",")
        lines[i] = [int(i) for i in lines[i]]
    metals_list = list([])
    for line in lines:
        m = line[:5]
        m1 = max(m)
        m2 = min(m)
        metals_list.append(abs(m1 - m2))
    max_diff = max(metals_list)
    min_diff = min(metals_list)
    for i, elem in enumerate(metals_list):
        if(elem == max_diff or elem == min_diff):
            line_to_show.append(lines[i])
    with open(result_extreme_file ,"w"):
        pass
    with open(result_extreme_file, "a") as f:
        f.write("m1,m2,m3,m4,m5,t1,cz1,t2,cz2,ocena\n")
        for line in line_to_show:
            line = [str(i) for i in line]
            line = ",".join(line)
            f.write(line + "\n")


def get_extreme(file):
    with(open(task_file)) as f:
        with open(file) as f2:
            lines = f2.readlines()
            line = f.readlines()[0]
            line = line.split(",")
            if(line[0] == "extreme"):
                option = line[1]
                if(option == "temperatura"):
                    temp_extreme(lines)
                elif option == "czas":
                    time_extreme(lines)
                elif option == "metale":
                    metals_extreme(lines)


if __name__ == "__main__":
    get_extreme("processing/test.csv")




