#Uses python3

import sys

def lcs2(a, b):
    #write your code here
    #return min(len(a), len(b))
    cache = [[-1 for _ in range(len(b))] for _ in range(len(a))]
    lcs2_recursion(a, b, len(a)-1, len(b)-1, cache)
    return cache[len(a)-1][len(b)-1]

def lcs2_recursion(a, b, a_i, b_i, cache):
    if a_i < 0 or b_i < 0:
        return 0
    if cache[a_i][b_i] != -1:
        return cache[a_i][b_i]
    m = lcs2_recursion(a, b, a_i-1, b_i, cache)
    m = max(m, lcs2_recursion(a, b, a_i, b_i-1, cache))
    if a[a_i] == b[b_i]:
        m = max(m, lcs2_recursion(a, b, a_i-1, b_i-1, cache)+1)
    cache[a_i][b_i] = m
    return m

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))

    n = data[0]
    data = data[1:]
    a = data[:n]

    data = data[n:]
    m = data[0]
    data = data[1:]
    b = data[:m]

    print(lcs2(a, b))
