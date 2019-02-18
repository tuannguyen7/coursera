# Uses python3
import sys
import random

def get_number_of_inversions(a, b, left, right):
    number_of_inversions = 0
    if right - left <= 1:
        return number_of_inversions
    ave = (left + right) // 2
    number_of_inversions += get_number_of_inversions(a, b, left, ave)
    number_of_inversions += get_number_of_inversions(a, b, ave, right)
    #write your code here
    number_of_inversions += merge(a, left, ave, right)
    return number_of_inversions

def merge(a, left, ave, right):
    i = left - left
    j = ave - left
    cur = left
    number_of_inversions = 0
    tempA = a[left:right:]
    #b = list(a)
    while i < ave-left and j < right-left:
        if tempA[i] <= tempA[j]:
            a[cur] = tempA[i]
            i += 1
        else:
            a[cur] = tempA[j]
            number_of_inversions += ave - left - i
            j += 1
        cur += 1
    while i < ave - left:
        a[cur] = tempA[i]
        i += 1
        cur += 1
        #number_of_inversions += right - 1 - ave
    while j < right - left:
        a[cur] = tempA[j]
        j += 1
        cur += 1
    #print("---------------")
    #print(a[left:right])
    #print(number_of_inversions)
    #print("---------------")
    return number_of_inversions

def test():
    a = [random.randint(0, 99) for _ in range(100)]
    #a = [5, 4, 2, 4, 6]
    #a = [4, 5, 2, 4, 6]
    #a = [3, 9, 8, 0, 5]
    #a = [8, 0, 5]
    correct = naiveCount(a)
    consider = get_number_of_inversions(a[::], [], 0, len(a))
    if correct != consider:
        print("-------END--------")
        print(correct)
        print(consider)
        print(a)

def naiveCount(a):
    inversion = 0
    for i in range(len(a)-1):
        for j in range(i+1, len(a)):
            if a[i] > a[j]:
                inversion += 1
    return inversion

if __name__ == '__main__':
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    b = n * [0]
    #sys.setrecursionlimit(3000)
    print(get_number_of_inversions(a, b, 0, len(a)))
    #test()
    #print(a)
