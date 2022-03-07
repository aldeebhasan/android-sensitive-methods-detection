import os
import re

file1 = open('result.txt', 'w')


def readFile(file):
    print("\n", file, "\n----------------")
    f = open(file, "r")
    lines = f.readlines()
    packageName = ""
    className = ""
    for line in lines:
        line = line.strip()
        # read package
        if line.startswith("package"):
            packageName = line.split(" ")[1].replace(";", "")
            continue
        if line.find(" class ") != -1:
            arr = line.split(" ")
            if arr[0] not in ['public', 'private', 'protected']:
                continue
            index = arr.index("class")
            className = arr[index + 1]
            continue


        found = re.search("^.+\(", line)
        if found != None and className != '':
            arr = line.split(" ")
            if arr[len(arr)-1] != '{':
                continue
            parot = 0
            methodType = arr[parot]
            # check if it is realy a function
            if methodType not in ['public', 'private', 'protected']:
                continue
            parot += 1
            methodModifier = "instance"
            if arr[parot] in ['static', 'abstract','final','synchronized']:
                methodModifier = arr[parot]
                parot += 1
            returnValue = arr[parot]
            parot += 1
            if arr[parot].find("(") == -1:
                continue
            methodName = arr[parot][0:arr[parot].index("(")]

            if methodName == className:
                continue

            occurance = line[line.index("(") + 1:line.index(")")]
            if len(occurance) > 0:
                argNum = occurance.count(",") + 1
            else:
                argNum = 0

            print(packageName, className, methodType, methodModifier, returnValue, methodName, argNum)
            file1.writelines(
                packageName + ";" + className + ";" + methodType + ";" + methodModifier + ";" + returnValue + ";" + methodName + ";" + str(
                    argNum) + "\n")


def readDir(directory):
    for file in os.listdir(directory):
        path = os.path.join(directory, file)
        if os.path.isdir(path):
            readDir(path)
        else:
            readFile(path)


 readDir("./android27")

file1.close()
