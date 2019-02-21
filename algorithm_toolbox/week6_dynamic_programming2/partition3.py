# Uses python3
import sys
import itertools

def partition3(A):
    cache = {}
    s = partition3_recursion(A, 0, 0, 0, 0, cache)
    #print(s)
    if s:
        return 1
    return 0

def partition3_recursion(A, s1, s2, s3, index, cache):
    if index >= len(A):
        return s1 == s2 and s1 == s3
    #key = str(s1) + ":" + str(s2) + ":" + str(s3) + ":" + str(index)
    #if key in cache:
    #    return cache[key]
    can3partition = partition3_recursion(A, s1+A[index], s2, s3, index+1, cache) or partition3_recursion(A, s1, s2+A[index], s3, index+1, cache) or partition3_recursion(A, s1, s2, s3+A[index], index+1, cache)
    #cache[key] = can3partition
    return can3partition

if __name__ == '__main__':
    input = sys.stdin.read()
    n, *A = list(map(int, input.split()))
    print(partition3(A))

