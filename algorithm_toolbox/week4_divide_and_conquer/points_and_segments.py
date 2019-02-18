# Uses python3
import sys
import random

def fast_count_segments(starts, ends, points):
    cnt = [0] * len(points)
    #write your code here
    starts.sort()
    ends.sort()
    startPosStart = 0
    startPosEnd = 0
    i = 0
    for point in points:
        startPosStart = bin_search2(starts, point, False)
        startPosEnd = bin_search2(ends, point, True)
        cnt[i] = startPosStart - startPosEnd
        i += 1
    return cnt

def bin_search(a, x, endFlag):
    left, right = 0, len(a)
    while True:
        if left >= right:
            return right
        mid = (left + right)//2
        if a[mid] == x:
            if endFlag:
                while mid > 0 and a[mid] == x:
                    mid -= 1
                    found = True
                if a[0] == x:
                    return 0
                return mid+1
            else:
                while mid < len(a) and a[mid] == x:
                    mid += 1
                return mid
        elif a[mid] > x:
            left, right = left, mid
        else:
            left, right = mid+1, right

# Tra ve co bao phan tu trong a ma <= x
def bin_search2(a, x, endFlag):
    left, right = 0, len(a)
    while True:
        if left >= right:
            return right
        mid = (left + right)//2
        if a[mid] == x:
            if endFlag:
                while a[mid] == x:
                    mid -= 1
                    if mid < 0:
                        break
                return mid + 1    # luu y
            else:
                while a[mid] == x:
                    mid += 1
                    if mid == len(a):
                        break
                return mid
        elif a[mid] > x:
            left, right = left, mid
        else:
            left, right = mid+1, right

def naive_count_segments(starts, ends, points):
    cnt = [0] * len(points)
    for i in range(len(points)):
        for j in range(len(starts)):
            if starts[j] <= points[i] <= ends[j]:
                cnt[i] += 1
    return cnt

def stress_test(n):
    for i in range(n):
        starts = [random.randint(0, 9999) for _ in range(111)]
        ends = [random.randint(0, 9999) + starts[_] for _ in range(111)]
        points = [random.randint(0, 9999) for _ in range(111)]
        consider = fast_count_segments(starts[::], ends[::], points)
        correct = naive_count_segments(starts, ends, points)
        if consider != correct:
            print(starts)
            print(ends)
            print(points)
            print(consider)
            print(correct)
            break
        #print(a)
        #print(b)

def manual_test():
    starts = [1, 1, 3, 10]
    ends = [2, 2, 6, 15]
    #print(fast_count_segments(starts, ends, [15]))
    print(bin_search2(starts, 3, False))
    print(bin_search2(ends, 0, True))

def test():
    x = 7
    arr = [1, 5, 7, 9, 10]
    ps = [5, 7, 1, 100, -9]
    for p in ps:
        print(bin_search(arr, p))

if __name__ == '__main__':
    #stress_test(1111)
    #manual_test()
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n = data[0]
    m = data[1]
    starts = data[2:2 * n + 2:2]
    ends   = data[3:2 * n + 2:2]
    points = data[2 * n + 2:]
    #use fast_count_segments
    cnt = fast_count_segments(starts, ends, points)
    for x in cnt:
        print(x, end=' ')
