import pandas
from sklearn import model_selection
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.naive_bayes import GaussianNB
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score
import parser
import measurements
import extreme_values_worker
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler


changed = False

class MyHandler(FileSystemEventHandler):
    def on_modified(self, event):
        global changed
        if changed == False:
            changed = True
            process()

ratings_dict = dict({"v.good": 5, "good": 4, "medium" : 3, "bad" :2, "v.bad" : 1} )
ratings_dict2 = dict({"good" : 1, "medium" : 0, "bad" : -1})
ratings_dict_reverse = dict({5 : "v.good", 4 : "good", 3: "medium", 2: "bad", 1: "v.bad"})
models = list([])
names = None
names2 = None
file_with_task = "task_folder/task.txt"


def sum_marks(marks1, marks2):
    summarized_marks = list([])
    for i, j in zip(marks1, marks2):
        summarized_marks.append(i+j)
    for i in range(len(summarized_marks)):
        summarized_marks[i] = round(summarized_marks[i], 2)
        summarized_marks[i] = int(summarized_marks[i])
        if(summarized_marks[i] == 6):
            summarized_marks[i] = 5
        if summarized_marks[i] < 1:
            summarized_marks[i] = 1
    return summarized_marks

def convert_marks(array):
    return [ratings_dict[x] for x in array]

def convert_marks2(array):
    return [ratings_dict2[x] for x in array]

def convert_marks_reverse(array):
    return [ratings_dict_reverse[x] for x in array]



def save_to_file(file , marks):

    print(marks)

    with open(file) as f:
        lines = f.readlines()
        print(len(lines))
        print(len(marks))
        for i in range(len(lines)):
            line = lines[i]
            print(line)
            print(marks[i])
            line = line.split(",")
            line[len(line) - 1] = marks[i]
            line = ",".join(line)
            lines[i] = line
    with open("output/results.csv", "w"):
        pass
    with open("output/results.csv", "a") as f:
        f.write("m1,m2,m3,m4,m5,t1,cz1,t2,cz2,ocena\n")
        for line in lines:
            f.write(line + "\n")


def simple_predict(filename, filename_test, names, N):
    dataset = pandas.read_csv(filename, names=names)


    array = dataset.values
    X = array[:,0:9]
    print(X)
    Y = array[:,9]
    validation_size = 0.20
    seed = 7
    X_train, Y_train =X, Y

    dataset_test = pandas.read_csv(filename_test, names=names)
    array = dataset_test.values
    X = array[:,0:9]
    print(X)
    Y = array[:,9]
    validation_size = 0.20
    seed = 7
    X_validation, Y_validation =X, Y


    scoring = 'accuracy'

    results = []
    names = []

    model_dict = dict({})
    global models
    for name, model in models:
        kfold = model_selection.KFold(n_splits=8, random_state=seed)
        cv_results = model_selection.cross_val_score(model, X_train, Y_train, cv=kfold, scoring=scoring)
        results.append(cv_results)
        names.append(name)
        msg = "%s: %f (%f)" % (name, cv_results.mean(), cv_results.std())
        model_dict[(name, model)] = cv_results.mean() - cv_results.std()
        print(msg)


    print("/////////////////////////////")

    models_dict = dict(sorted(model_dict.items(), key=lambda x : x[1], reverse= True))
    models = list(models_dict.keys())
    models = models[:3]
    first = None
    second = None
    third = None
    result = list([])
    with open("output/processing.txt", "a") as f:
        f.write("Predictions 1 [przewidywanie bazujące na zawartości metali i procesie grzewczym]\n")
    for model in models:
        model[1].fit(X_train, Y_train)
        predictions = model[1].predict(X_validation)
        print(predictions)
        print("////////////////////")
        predictions_to_write = ",".join(predictions)
        with open("output/processing.txt", "a") as f:
            f.write(predictions_to_write+"\n\n")
        if(first ==None):
            first =convert_marks(predictions)
            print(first)
        elif(second == None):
            second = convert_marks(predictions)
            print(second)
        else:
            third = convert_marks(predictions)
            print(third)
    with open("output/processing.txt", "a") as f:
        f.write("\n")
    for i in range(N):
        if(first[i] == second[i]):
             result.append(first[i])
        elif first[i] == third[i] :
             result.append(first[i])
        elif second[i] == third[i]:
             result.append(second[i])
        else:
             result.append((first[i] + second[i] + third[i])/ 3)
    return result
########################################################################################################
def simple_predict2(filename, filename_test, names, N):
    dataset = pandas.read_csv(filename, names=names)


    array = dataset.values
    X = array[:,0:5]
    print(X)
    Y = array[:,5]
    validation_size = 0.20
    seed = 7
    X_train, Y_train =X, Y

    dataset_test = pandas.read_csv(filename_test, names=names)
    array = dataset_test.values
    X = array[:,0:5]
    print(X)
    Y = array[:,5]
    validation_size = 0.20
    seed = 7
    X_validation, Y_validation =X, Y


    scoring = 'accuracy'

    results = []
    names = []

    model_dict = dict({})
    global models
    for name, model in models:
        kfold = model_selection.KFold(n_splits=8, random_state=seed)
        cv_results = model_selection.cross_val_score(model, X_train, Y_train, cv=kfold, scoring=scoring)
        results.append(cv_results)
        names.append(name)
        msg = "%s: %f (%f)" % (name, cv_results.mean(), cv_results.std())
        model_dict[(name, model)] = cv_results.mean() - cv_results.std()
        print(msg)


    print("/////////////////////////////")

    models_dict = dict(sorted(model_dict.items(), key=lambda x : x[1], reverse= True))
    models = list(models_dict.keys())
    models = models[:3]
    first = None
    second = None
    third = None
    result = list([])
    with open("output/processing.txt", "a") as f:
        f.write("Predictions 2 [przewidywanie bazujące na składzie stopu]\n")
    for model in models:
        model[1].fit(X_train, Y_train)
        predictions = model[1].predict(X_validation)
        print(predictions)
        print("////////////////////")
        predictions_to_write = ",".join(predictions)
        with open("output/processing.txt", "a") as f:
            f.write(predictions_to_write+"\n\n")
        if(first ==None):
            first =convert_marks2(predictions)
        elif(second == None):
            second = convert_marks2(predictions)
        else:
            third = convert_marks2(predictions)
    with open("output/processing.txt", "a") as f:
        f.write("\n")
    for i in range(N):
        if(first[i] == second[i] or first[i] == third[i] or second[i] == third[i]):
             result.append(first[i])
        else:
             result.append((first[i] + second[i] + third[i])/ 3)
    return result


def make_prediction(file1, file2, file3, file4, N):
    r = simple_predict(file2,file1,names, N)
    r2 = simple_predict2(file4, file3, names2, N)
    print("RESULT1")
    print(r)
    print("RESULT2")
    print(r2)
    r_to_write = str(r)
    r2_to_write = str(r2)
    with open("output/processing.txt", "a") as f:
        f.write("Ocena, pierwsza część przewidywania:\n" + r_to_write + "\n")
        f.write("Ocena, druga część przewidywania:\n" + r2_to_write + "\n")
    results = sum_marks(r,r2)
    print(results)
    results = convert_marks_reverse(results)
    print(len(results))
    save_to_file(file1, results)
    return results

def process():
    with open(file_with_task) as f:
        line = f.readlines()[0]
        if(line.startswith("classify")):
            with open("output/processing.txt", "w"):
                pass
            parser.parse("input/classify.json", "processing/test.csv")
            parser.convert_parse_file("processing/test.csv", "processing/test1.csv")
            parser.convert_parse_file2("processing/test.csv", "processing/test2.csv")
            line = line.split(",")


            print(make_prediction("processing/test2.csv", "trening_sets/dane2.csv", "processing/test1.csv", "trening_sets/dane4.csv", int(line[1])))
        elif line.startswith("predict"):
            with open("output/processing.txt", "w"):
                pass
            parser.convert_parse_file("input/predict.csv", "processing/test1.csv")
            parser.convert_parse_file2("input/predict.csv", "processing/test2.csv")
            line = line.split(",")
            print(make_prediction("processing/test2.csv", "trening_sets/dane2.csv", "processing/test1.csv",
                                  "trening_sets/dane4.csv", int(line[1])))
        elif line.startswith("statistics"):
            parser.parse("input/classify.json", "processing/test.csv")
            line = line.split(",")
            measurements.measure("processing/test.csv",int(line[1]))
        elif line.startswith("extreme"):
            parser.parse("input/classify.json", "processing/test.csv")
            extreme_values_worker.get_extreme("processing/test.csv")

        with open("processing/test2.csv", "w"):
            pass
        with open("processing/test.csv", "w"):
            pass
        with open("processing/test1.csv", "w"):
            pass

        global changed
        changed = False

#########################################################################################################

if __name__ == "__main__":

    event_handler = MyHandler()
    observer = Observer()
    observer.schedule(event_handler, path='task_folder', recursive=False)
    observer.start()

    models.append(('LR', LogisticRegression()))
    models.append(('LDA', LinearDiscriminantAnalysis()))
    models.append(('KNN', KNeighborsClassifier()))
    models.append(('CART', DecisionTreeClassifier()))
    models.append(('NB', GaussianNB()))
    models.append(('SVM', SVC()))
    names = ['metal1', 'metal2', 'metal3', 'metal4', 'metal5', 't1', 'c1', 't2', 'c2', 'rate']
    names2 = ['metal1', 'metal2', 'metal3', 'metal4', 'metal5', 'rate']

    while True:
        pass




