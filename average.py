import sys;
file = sys.argv[1:][0];
with open(file, 'r') as f:
    sum = 0
    itemList = f.readline().split()
    for i in range(0,len(itemList)):
        sum += int(itemList[i])
    print(sum/len(itemList))