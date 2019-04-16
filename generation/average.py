import sys;
file = sys.argv[1:][0];
with open(file, 'r') as f:
    sum = 0
    itemList = f.readline().split()
    for i in range(0,len(itemList)):
        sum += int(itemList[i])
    result = sum/len(itemList)
    arg = file.split("_")
    with open("DATA-SUM.md", 'a+') as w:
     w.write(arg[3] + ": " + arg[0] + " " + arg[1] + "_" + arg[2] + " : " + str(result) +"\n");