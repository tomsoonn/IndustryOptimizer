from random import randint
import random


def get_str(r):
    return str(r[0]) +"," + str(r[1]) + "," + str(r[2]) + "," + str(r[3]) + "," + str(r[4]) + ","\
            + str(r[5]) + "," + str(r[6]) + "," + str(r[7]) + "," + str(r[8]) + "," + r[9]


def fail_metals1(r):
    if(r[4] > 8):
        r[4] -=5
        r[0] +=5
    if(r[3] >=8):
        r[3] -=5
        r[1] +=5
    return r

def fail_metals2(r):
    for i in range(5):
        r[i] -= randint(3,5)
    return r

def fail_temperatures1(r):
    r[5] -=20
    r[6] +=10
    return r

def fail_temperatires2(r):
    r[7] +=20
    r[8] + 10
    return r


failures_dict = dict({"1":fail_metals1,"2":fail_metals2, "3":fail_temperatures1, "4":fail_temperatires2 })

###################################################################################################################


def improve_metals1(r):
    if(r[0] > 40):
        r[0] -= 10
        r[1] +=5
        r[2] +=5
        return r
    return improve_metals2(r)

def improve_metals2(r):
    s = r[0] + r[1] +r[2] + r[3] + r[4]
    if(s < 90):
        r[2] +=4
        r[3] +=3
        r[4] +=3
        return r
    return improve_metals1(r)

def improve_temperatures1(r):
    r[5] = randint(80,120)
    r1 = int(round(r[5] / 2, 2))
    r2 = int(round(r[5] / 1.7, 2))
    t1 = randint(r1, r2)
    return r

def improve_temperatures2(r):
    r1 = int(round(r[5] * 1.5))
    r2 = int(round(r[5] * 1.7))
    r[7] = randint(r1, r2)
    r1 = int(round(r[7] / 2.5))
    r2 = int(round(r[7] / 2))
    r[8] = randint(r1, r2)
    return r

improve_dict = dict({"1":improve_metals1,"2":improve_metals2, "3":improve_temperatures1, "4":improve_temperatures2 })


########################################################################################################################

def generate_v_good_examples(N):
    for i in range(N):
        m1 = randint(20,30)
        m2 = randint(20,25)
        rest = 100 - m1 - m2
        if(rest > 55):
            m3 = randint(15,20)
            m4 = randint(15,20)
            m5 = randint(10,15)
        elif(rest >50):
            m3 = randint(15,20)
            m4 = randint(10,15)
            m5 = randint(10,15)
        else:
            m3 = randint(10,15)
            m4 = randint(10,15)
            m5 = randint(10,15)

        temp = randint(800,1200)
        r1  = int(round(temp/2 , 2))
        r2 = int(round(temp/1.7 , 2))
        t1 = randint(r1,r2)
        r1 = int(round(temp*1.5))
        r2 = int(round(temp*1.7))
        temp2 = randint(r1,r2)
        r1 = int(round(temp2/2.5))
        r2 = int(round(temp2/2))
        t2 = randint(r1,r2)
        result = "v.good"
        m = [m1,m2,m3,m4,m5]
        m = sorted(m, reverse=True)
        r = [m[0],m[1],m[2],m[3],m[4],temp,t1,temp2,t2,result]
        rr = get_str(r)
        print(rr)
        if(N == 1):
            return r
#######################################################################

def generate_good_examples(N):
    for i in range(N):
        r = generate_v_good_examples(1)
        i = randint(1,4)
        r = failures_dict[str(i)](r)
        r[9] = "good"
        rr = get_str(r)
        print(rr)



def generate_medium_examples(N):
    for i in range(N):
        if(i%2 == 0):
            m1 = randint(40,60)
            m2 = randint(5,10)
            m3 = randint(5,10)
            rest = 100 - m1 - m2 - m3
            rest = rest / 2
            rest= int(round(rest/2 ,2 ))
            m4 = randint(5,int(round(rest)))
            m5 = randint(5,int(round(rest)))
            temp = randint(800, 1200)
            r1 = int(round(temp / 2, 2))
            r2 = int(round(temp / 1.7, 2))
            t1 = randint(r1, r2)
            r1 = int(round(temp * 1.5))
            r2 = int(round(temp * 1.7))
            temp2 = randint(r1, r2)
            r1 = int(round(temp2 / 2.5))
            r2 = int(round(temp2 / 2))
            t2 = randint(r1, r2)
            result = "medium"
            m = [m1, m2, m3, m4, m5]
            m =sorted(m, reverse=True)
            r = [m[0], m[1], m[2], m[3], m[4], temp, t1, temp2, t2, result]
            rr = get_str(r)
            print(rr)
            #return r
        else:
            m1 = randint(20, 30)
            m2 = randint(20, 25)
            rest = 100 - m1 - m2
            if (rest > 55):
                m3 = randint(15, 20)
                m4 = randint(15, 20)
                m5 = randint(10, 15)
            elif (rest > 50):
                m3 = randint(15, 20)
                m4 = randint(10, 15)
                m5 = randint(10, 15)
            else:
                m3 = randint(10, 15)
                m4 = randint(10, 15)
                m5 = randint(10, 15)
            temp = randint(500, 700)
            r1 = int(round(temp / 3, 2))
            r2 = int(round(temp / 1.5, 2))
            t1 = randint(r1, r2)
            r1 = int(round(temp * 2))
            r2 = int(round(temp * 3))
            temp2 = randint(r1, r2)
            r1 = int(round(temp2 / 3))
            r2 = int(round(temp2 / 2))
            t2 = randint(r1, r2)
            result = "medium"
            m = [m1, m2, m3, m4, m5]
            m = sorted(m, reverse=True)
            r = [m[0], m[1], m[2], m[3], m[4], temp, t1, temp2, t2, result]
            rr = get_str(r)
            print(rr)
            #return r
#################################################################################

def generate_bad_examples(N):
    for i in range(N):
        r = generate_v_bad_examples(1)
        i = randint(1,4)
        r = improve_dict[str(i)](r)
        r[9] = "bad"
        rr = get_str(r)
        print(rr)


def generate_v_bad_examples(N):
    for i in range(N):
        m1 = randint(40,60)
        m2 = randint(5,10)
        m3 = randint(5,10)
        rest = 100 - m1 - m2 - m3
        rest = rest / 2
        rest= int(round(rest/2 ,2 ))
        m4 = randint(5,int(round(rest)))
        m5 = randint(5,int(round(rest)))
        temp = randint(500, 700)
        r1 = int(round(temp / 3, 2))
        r2 = int(round(temp / 1.5, 2))
        t1 = randint(r1, r2)
        r1 = int(round(temp * 2))
        r2 = int(round(temp * 3))
        temp2 = randint(r1, r2)
        r1 = int(round(temp2 / 3))
        r2 = int(round(temp2 / 2))
        t2 = randint(r1, r2)
        result = "v.bad"
        m = [m1, m2, m3, m4, m5]
        m = sorted(m, reverse=True)
        r = [m[0], m[1], m[2], m[3], m[4], temp, t1, temp2, t2, result]
        rr= get_str(r)
        print(rr)
        if(N==1):
            return r

####################################################################################33

if __name__ == "__main__":
    generate_medium_examples(10)