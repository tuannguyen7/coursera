# Uses python3
import sys

def get_change(m):
    #write your code here
    cache = [-1]*(m+1)
    cache[0] = 0
    recursion(m, cache)
    return cache[m]

def recursion(m, cache):
    if m < 0:
        return 99999
    if cache[m] != -1:
        return cache[m]
    res = min(recursion(m-1, cache), recursion(m-3, cache), recursion(m-4, cache)) + 1
    cache[m] = res
    return res

if __name__ == '__main__':
    m = int(sys.stdin.read())
    print(get_change(m))
