import os
import re
import javalang

f = open("result.txt", "r")

lines = f.readlines()
globalMethods = {}
onlyMethods = {}
foundedMethods = []
i = 0
for line in lines:
    if i == 0:
        i += 1
        continue
    arr = line.split(";")
    key = arr[1] + "." + arr[5]

    if key not in globalMethods.keys():
        globalMethods[key] = [line]
    else:
        globalMethods[key].append(line)

    method = arr[5]
    if method not in onlyMethods.keys():
        onlyMethods[method] = [line]
    else:
        onlyMethods[method].append(line)

print(len(globalMethods))
print(len(lines))


def readFile(file):
    if not file.endswith(".java"):
        return []

    print("\n", file, "\n----------------")
    f = open(file, "r")

    # tree = javalang.parse.parse(f.read())
    #
    # for klass in tree.types:
    #     if isinstance(klass, javalang.tree.ClassDeclaration):
    #         for m in klass.methods:
    #             print(m)
    # tokens = javalang.tokenizer.tokenize(f.read())
    # parser = javalang.parser.Parser(tokens)

    occuredMethods = []
    packages = []
    lines = f.readlines()
    for line in lines:
        line = line.strip()

        if line.startswith("import"):
            packages.append(line.split(" ")[1].replace(";", ""))
            continue

        arr = line.split(" ")
        for token in arr:
            # search for mothods
            found = re.search("^.+\..+\(", token)
            if found != None:
                minified = token[found.start():found.end()-1]
                # check if method found
                if minified in globalMethods.keys():
                    occurance = line[line.index("(",found.start()) + 1:line.index(")",found.start())]
                    if len(occurance) > 0:
                        argNum = occurance.count(",") + 1
                    else:
                        argNum = 0
                    # find currect item by arg num
                    for item in globalMethods[minified]:
                        splited = item.replace("\n", "").split(";")
                        if int(splited[6]) == argNum:
                            if item not in occuredMethods:
                                occuredMethods.append(item)
                else:
                    idx = minified.index(".")
                    minified = minified[idx+1:]
                    if minified in onlyMethods.keys():
                        if minified == "exists":
                            print("")
                        for item in onlyMethods[minified]:
                            arr = item.split(";")
                            if arr[0] in packages  or arr[0]+"."+arr[1] in packages:
                                if item not in occuredMethods:
                                    occuredMethods.append(item)
                                break

    return occuredMethods


def readDir(directory):
    methods = []
    for file in os.listdir(directory):
        path = os.path.join(directory, file)
        if os.path.isdir(path):
            methods += readDir(path)
        else:
            methods += readFile(path)
    return methods


def readSamples(directory, family):
    for file in os.listdir(directory):
        path = os.path.join(directory, file)
        if os.path.isdir(path):
            if not os.path.exists('output/' + family):
                os.makedirs('output/' + family)
            output = open('output/' + family + '/' + file + '.txt', 'w')
            if file == '3ed6ba45a1bb38e09e99faed2708314c.apk+1':
                print('s')
            lines = readDir(path)
            lines = set(lines)
            output.writelines(
                "PackageName;ClassName;MethodModifiers;MethodType;ReturnValueType;MethodName;argumentsNumber;MethodPart\n")
            for line in lines:
                arr = line.split(";")
                methodPart = "other"
                search = ['set', 'get', 'is', 'request']
                for x in search:
                    if arr[5].startswith(x):
                        methodPart = x
                line = line.replace("\n", ";" + methodPart + "\n")
                output.writelines(line)
            output.close()


def readFamilies(directory):
    if not os.path.exists('output'):
        os.makedirs('output')
    for file in os.listdir(directory):
        path = os.path.join(directory, file)
        readSamples(path, file)


readFamilies("./samples")
