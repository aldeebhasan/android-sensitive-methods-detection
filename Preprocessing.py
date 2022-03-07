f = open("dataset.csv", "r")
output = open('dataset__out.csv', 'w')
lines = f.readlines()

# output.writelines("PackageName;ClassName;ClassModifier;MethodModifiers;MethodPart;MethodModifiers;IsReturnable;ParameterIsInterface;ParameterType;RequestPermission;Category"+"\n")
output.writelines("PackageName;ClassName;MethodModifiers;MethodType;ReturnValueType;MethodName;ArgumentsNumber;MethodPart;ClassModifier;IsReturnable;ParameterIsInterface;ParameterType;PermissionType;RequestPermission;Category"+"\n")
for i,l in enumerate(lines):
    arr = l.replace("\n","").split(";")
    if i==0:
        continue
    line=[]
    line.append(arr[0]) #PackageName
    line.append(arr[1]) #ClassName
    line.append(arr[2]) #MethodModifiers
    line.append(arr[3]) #MethodType
    line.append(arr[4]) #ReturnValueType
    line.append(arr[5]) #MethodName
    line.append(arr[6]) #ArgumentsNumber
    line.append(arr[8]) #MethodPart
    line.append(arr[9]) #ClassModifier
    line.append("TRUE" if(arr[4] !='void') else "FALSE") #IsReturnable
    line.append(arr[10]) #ParameterIsInterface
    line.append(arr[11] if(int(arr[6]) > 0) else "None") #ParameterType
    line.append(arr[12]) #PermissionType
    line.append("TRUE" if(arr[12] !='None') else "FALSE") #RequestPermission
    line.append(arr[7])  # Category


    output.writelines(";".join(line)+"\n")
output.close()

