import random


def get_str(r):
    return str(r[0]) +"," + str(r[1]) + "," + str(r[2]) + "," + str(r[3]) + "," + str(r[4]) + ","\
            + str(r[5])



def shuffle(array):
    x1 = random.randint(0,4)
    x2 = random.randint(0,4)
    if(x1 == x2):
        if(x1 !=0):
            x1 +=1
        else:
            x1 -=1
    tmp1 = array[x1]
    tmp2 = array[x2]
    array[x1] = tmp2
    array[x2] = tmp1
    return array


def generate_good_examples(N):
    for i in range(N):
        x = 1 + random.randint(10,20)
        if(x % 3 == 0):
            m1 = random.randint(25,30)
            m2 = random.randint(15,20)
            m3 = random.randint(15,20)
            m4 = random.randint(20,25)
            m5 =random.randint(20,25)
        elif (x % 3 ==1):
            m1 = random.randint(30,40)
            m2 = random.randint(15,20)
            m3 = random.randint(15,20)
            m4 = random.randint(25,30)
            m5 =random.randint(25,30)
        elif (x % 3 == 2):
            m1 = random.randint(20,30)
            m2 = random.randint(10,15)
            m3 = random.randint(10,25)
            m4 = random.randint(15,20)
            m5 =random.randint(15,21)
        r = 0
        if(m1 + m2 + m3 + m4 + m5 > 100):
            r = m1 + m2 + m3 + m4 + m5
            r = r - 100
            r=  1 + r // 5
        m1 -= r
        m2 -= r
        m3 -= r
        m4 -=r
        m5 -=r
        x = [m1, m2, m3,m4,m5, "good"]
        x = get_str(x)
        print(x)


def generate_bad_examples(N):
    for i in range(N):
        x = 1 + random.randint(10,20)
        if(x % 3 == 0):
            m2 = random.randint(25,30)
            m5 = random.randint(15,20)
            m1 = random.randint(15,20)
            m4 = random.randint(20,25)
            m3 =random.randint(20,25)
        elif (x % 3 ==1):
            m2 = random.randint(30,40)
            m5 = random.randint(15,20)
            m1 = random.randint(15,20)
            m4 = random.randint(25,30)
            m3 =random.randint(25,30)
        elif (x % 3 == 2):
            m2 = random.randint(20,30)
            m5 = random.randint(10,15)
            m1 = random.randint(10,25)
            m4 = random.randint(15,20)
            m3 =random.randint(15,21)
        r =0
        if(m1 + m2 + m3 + m4 + m5 > 100):
            r = m1 + m2 + m3 + m4 + m5
            r = r - 100
            r=  1 + r // 5
        m1 -= r
        m2 -= r
        m3 -= r
        m4 -=r
        m5 -=r
        x = [m1, m2, m3,m4,m5, "bad"]
        x = get_str(x)
        print(x)


def generate_medium_examples(N):
    for i in range(N):
        x = 1 + random.randint(10,20)
        if(x % 3 == 0):
            m1 = random.randint(25,30)
            m2 = random.randint(15,20)
            m3 = random.randint(15,20)
            m4 = random.randint(20,25)
            m5 = random.randint(20,25)
        elif (x % 3 ==1):
            m2 = random.randint(30,40)
            m5 = random.randint(15,20)
            m1 = random.randint(15,20)
            m4 = random.randint(25,30)
            m3 =random.randint(25,30)
        elif (x % 3 == 2):
            m2 = random.randint(20,30)
            m5 = random.randint(10,15)
            m1 = random.randint(10,25)
            m4 = random.randint(15,20)
            m3 =random.randint(15,21)
        r= 0
        if(m1 + m2 + m3 + m4 + m5 > 100):
            r = m1 + m2 + m3 + m4 + m5
            r = r - 100
            r=  1 + r // 5
        m1 -= r
        m2 -= r
        m3 -= r
        m4 -=r
        m5 -=r
        x = [m1, m2, m3,m4,m5, "medium"]
        x = shuffle(x)
        x = get_str(x)
        print(x)


if __name__ == "__main__":
    generate_bad_examples(10)

