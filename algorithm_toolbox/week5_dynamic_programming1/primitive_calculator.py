# Uses python3
import sys

# greedy wrong algorithm
def optimal_sequence(n):
    sequence = []
    while n >= 1:
        sequence.append(n)
        if n % 3 == 0:
            n = n // 3
        elif n % 2 == 0:
            n = n // 2
        else:
            n = n - 1
    return reversed(sequence)

# dynamic programing correct algorithm
def optimal_sequence_dp(n):
    #cache = [0]*(n+1)
    #sol = [0]*99
    #dp(n, cache, sol)
    return dp_iterator(n)
    #print(cache[n])
    #print(sol)
    #return sol[:cache[n]+1]

# dp iterator
def dp_iterator(n):
    sol = [0]*(n+1)
    sequence = []
    sequence2 = {}
    for i in range(1, n+1):
        m = 99999
        if i%3 == 0:
            m1 = sol[i//3] + 1
            m = m1
        if i%2 == 0:
            m2 = sol[i//2] + 1
            if m2 < m:
                m = m2
        m3 = sol[i-1] + 1
        if m3 < m:
            m = m3
        sol[i] = m
        sequence2[i] = m
    return printSequence(n, sequence2)

def printSequence(n, sequence2):
    cur = sequence2[n]
    l = [0]*cur
    l[cur-1] = n
    while cur > 1:
        if n%3 == 0 and sequence2[n//3] == cur-1:
            n = n//3
        elif n%2 == 0 and sequence2[n//2] == cur-1:
            n = n//2
        else:
            n -= 1
        cur -= 1
        l[cur-1] = n
    return l

# dp recursion, but can't get sequence
def dp(n, cache, sol):
    if n == 0:
        return 0
    if n == 1:
        return 1
    if cache[n] != 0:
        return cache[n]
    curMin = 99999
    if n%3 == 0:
        min1 = dp(n//3, cache, sol) + 1
        appendSolution(sol, n//3, min1)
        curMin = min1
    if n%2 == 0:
        min2 = dp(n//2, cache, sol) + 1
        if min2 < curMin:
            appendSolution(sol, n//2, min2)
            curMin = min2
    min3 = dp(n-1, cache, sol) + 1
    if min3 < curMin:
        appendSolution(sol, n-1, min3)
        curMin = min3
    cache[n] = curMin
    return curMin

def appendSolution(sol, val, index):
    if len(sol) <= index:
        sol.append(val)
    else:
        sol[index] = val

input = sys.stdin.read()
n = int(input)
#n = 96234
sequence = list(dp_iterator(n))
print(len(sequence) - 1)
for x in sequence:
    print(x, end=' ')
