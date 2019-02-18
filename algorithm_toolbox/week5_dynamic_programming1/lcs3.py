#Uses python3

import sys

def lcs3(a, b, c):
    #write your code here
    cache = [[[-1 for _ in range(len(c))] for _ in range(len(b))] for _ in range(len(a))]
    lcs3_recursion(a, b, c, 0, 0, 0, cache)
    #print(cache)
    return cache[0][0][0]

def lcs3_recursion(a, b, c, a_i, b_i, c_i, cache):
    if a_i == len(a) or b_i == len(b) or c_i == len(c):
        return 0
    if cache[a_i][b_i][c_i] != -1:
        return cache[a_i][b_i][c_i]
    m =        lcs3_recursion(a, b, c, a_i+1, b_i, c_i, cache)
    m = max(m, lcs3_recursion(a, b, c, a_i, b_i+1, c_i, cache))
    m = max(m, lcs3_recursion(a, b, c, a_i, b_i, c_i+1, cache))
    if a[a_i] == b[b_i] and a[a_i] == c[c_i]:
        m = max(m, lcs3_recursion(a, b, c, a_i+1, b_i+1, c_i+1, cache) + 1)
    cache[a_i][b_i][c_i] = m
    return m

def lcs3_naive(a, b, c):
    return 1

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    an = data[0]
    data = data[1:]
    a = data[:an]
    data = data[an:]
    bn = data[0]
    data = data[1:]
    b = data[:bn]
    data = data[bn:]
    cn = data[0]
    data = data[1:]
    c = data[:cn]
    print(lcs3(a, b, c))
