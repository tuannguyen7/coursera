#Uses python3
import sys
import math
import random

# sortX_x, sortX_y: phan tu x, y duoc sort theo x
# sortY_x, sortY_y: phan tu x, y duoc sort theo y

def minimum_distance(x, y):
    #write your code here
    #return 10 ** 18
    sortX_x, sortX_y = zip(*sorted(zip(x, y)))
    sortY_y, sortY_x = zip(*sorted(zip(y, x)))
    return recursive(x, y, 0, len(x)-1)

def naive_minimum_distance(x, y):
    m = 99999
    for i in range(len(x)-1):
        for j in range(i+1, len(x)):
            m = min(calDistance(x[i], y[i], x[j], y[j]))
    return m

def recursive_optimal(sortX_x, sortX_y, sortY_y, sortY_x, left, right):
    if left >= right:
        return 999999
    mid = (left + right)//2
    d1 = recursive_optimal(sortX_x, sortX_y, sortY_x, sortY_y, left, mid)
    d2 = recursive_optimal(sortX_x, sortX_y, sortY_x, sortY_y, mid+1, right)
    minD = min(d1, d2)
    lower, higher = findLowerHigherXPos(sortX_x, mid, minD, left, right)
    #minD = min(findMinDisBetween2Halve(x, y, lower, higher), minD)
    return minD

def findMinDisBetween2Halve_optimal(sortX_x, sortX_y, sortY_x, sortY_y, leftX, rightX):
    tempX = x[leftX:rightX+1:]
    tempY = y[leftX:rightX+1:]
    tempY, tempX = zip(*sorted(zip(tempY, tempX)))
    minDis = 999999
    leftX, rightX = 0, rightX-leftX
    for i in range(leftX, rightX):
        for nP in range(i+1,i+7):   # neighbor position
            if nP > rightX:
                break
            minDis = min(minDis, calDistance(tempX[i], tempY[i], tempX[nP], tempY[nP]))
    return minDis

def correctpondingFromSortXArrayToSortYArray(sortX_x, sortX_y, sortY_y, sortY_x, left, right):
    cY_x = [0]*(right - left + 1) # correctpondingY
    cY_y = [0]*(right - left + 1) # correctpondingX
    for i in range(left, right+1):
        


def recursive(x, y, left, right):
    if left >= right:
        return 999999
    mid = (left + right)//2
    d1 = recursive(x, y, left, mid)
    d2 = recursive(x, y, mid+1, right)
    minD = min(d1, d2)
    lower, higher = findLowerHigherXPos(x, mid, minD, left, right)
    minD = min(findMinDisBetween2Halve(x, y, lower, higher), minD)
    return minD

def calDistance(x1, y1, x2, y2):
    curDis = abs(x1 - x2)**2 + abs(y1-y2)**2
    return math.sqrt(curDis)

def findLowerHigherXPos(x, mid, minD, lower, higher):
    li = mid # lower index
    hi = mid # higher index
    lowerX = x[mid] - minD
    higherX = x[mid] + minD
    while True:
        found = False
        if li >= lower and x[li] >= lowerX:
            li -= 1
            found = True
        if hi <= higher and x[hi] <= higherX:
            hi += 1
            found = True
        if not found:
            return (li+1, hi-1)
    return (li, hi)

def findMinDisBetween2Halve(x, y, leftX, rightX):
    tempX = x[leftX:rightX+1:]
    tempY = y[leftX:rightX+1:]
    #print(tempY)
    #print(tempX)
    tempY, tempX = zip(*sorted(zip(tempY, tempX)))
    #print(tempY)
    #print(tempX)
    minDis = 999999
    leftX, rightX = 0, rightX-leftX
    #leftX,  = 0
    #rightX = rightX - leftX
    for i in range(leftX, rightX):
        for nP in range(i+1,i+7):   # neighbor position
            if nP > rightX:
                break
            minDis = min(minDis, calDistance(tempX[i], tempY[i], tempX[nP], tempY[nP]))
    return minDis

def stress_test(n):
    for i in range(n):
        x = [random]

if __name__ == '__main__':
    #inputStream = open("resource/closetPointt1.inp")
    inputStream = sys.stdin
    input = inputStream.read()
    data = list(map(int, input.split()))
    n = data[0]
    x = data[1::2]
    y = data[2::2]
    print("{0:.9f}".format(minimum_distance(x, y)))
