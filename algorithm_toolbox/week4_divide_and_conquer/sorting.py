# Uses python3
import sys
import random

# Demo partition https://algs4.cs.princeton.edu/lectures/23DemoPartitioning.pdf
def partition3(a, l, r):
    pivot = a[l]
    lt = l      # less than p
    i = l       # iterator
    gt = r      # greter than p
    while i <= gt:
        if a[i] == pivot:
            i += 1
        elif a[i] < pivot:
            a[i], a[lt] = a[lt], a[i]
            lt += 1
            i += 1
        else:
            a[i], a[gt] = a[gt], a[i]
            gt -= 1
    return (lt, gt)

def partition2(a, l, r):
    pivotPos = (l+r)//2
    x = a[pivotPos]
    print(x)
    print(pivotPos)
    a[pivotPos], a[l] = a[l], a[pivotPos]
    j = l
    for i in range(l+1, r + 1):
        if a[i] <= x:
            j += 1
            a[i], a[j] = a[j], a[i]
    
    a[l], a[j] = a[j], a[l]
    return (j, j)


def randomized_quick_sort(a, l, r):
    if l >= r:
        return
    k = random.randint(l, r)
    a[l], a[k] = a[k], a[l]
    m1, m2 = partition3(a, l, r)
    randomized_quick_sort(a, l, m1-1);
    randomized_quick_sort(a, m2+1, r);

def test():
    a = [random.randint(0, 4) for _ in range(9)]
    #b = a[::]
    #a = [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
    #a = [4, 5, 1, 3]
    a = [5, 2, 4, 2, 3, 2, 1, 2, 2, 2]
    print(a)
    #lt, gt = partition3(a, 0, len(a)-1)
    randomized_quick_sort(a, 0, len(a)-1)
    print(a)
    #print((lt, gt))

def stress_test(n):
    for i in range(n):
        rangeRange = random.randint(0, 9999)
        a = [random.randint(0, 99) for _ in range(rangeRange)]
        b = a[::]
        randomized_quick_sort(a, 0, len(a)-1)
        b.sort()
        if b == a:
            print("wrong")
            print(a)
            break

if __name__ == '__main__':
    #test()
    #stress_test(100)
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    randomized_quick_sort(a, 0, n - 1)
    for x in a:
       print(x, end=' ')


