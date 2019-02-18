# Uses python3
# ToDo: implement iterator solution
import random
import string

def edit_distance(s, t):
    #write your code here
    cache = [[-1 for i in range(len(t))] for _ in range(len(s))]
    edit_distance_sol(s, t, len(s)-1, len(t)-1, cache)
    return cache[len(s)-1][len(t)-1]

# recursion
def edit_distance_sol(s, t, s_i, t_i, cache):
    if s_i < 0:
        return t_i + 1
    if t_i < 0:
        return s_i + 1
    if cache[s_i][t_i] != -1:
        return cache[s_i][t_i]
    m = edit_distance_sol(s, t, s_i-1, t_i, cache) + 1
    m = min(m, edit_distance_sol(s, t, s_i, t_i-1, cache) + 1)
    m = min(m, edit_distance_sol(s, t, s_i-1, t_i-1, cache) + 1)
    if s[s_i] == t[t_i]:
        m = min(m, edit_distance_sol(s, t, s_i-1, t_i-1, cache))
    cache[s_i][t_i] = m
    return m

# iterator
def edit_distance_iter(s, t):
    cache = [[0 for i in range(len(t)+1)] for _ in range(len(s)+1)]
    for i in range(len(t)+1):
        cache[0][i] = i
    for i in range(len(s)+1):
        cache[i][0] = i

    for i in range(1, len(s)+1):
        for j in range(1, len(t)+1):
            cache[i][j] = cache[i-1][j] + 1
            cache[i][j] = min(cache[i][j], cache[i][j-1] + 1)
            cache[i][j] = min(cache[i][j], cache[i-1][j-1] + 1)
            if s[i-1] == t[j-1]:
                cache[i][j] = min(cache[i][j], cache[i-1][j-1])
    #print(cache)
    return cache[len(s)][len(t)]

def stress_test(n):
    for i in range(n):
        size = random.randint(1,100)
        chars = 'abcd'
        s = ''.join(random.choice(chars) for _ in range(size))
        t = ''.join(random.choice(chars) for _ in range(size))
        correct = edit_distance(s, t)
        consider = edit_distance_iter(s, t)
        if correct != consider:
            print(s)
            print(t)
            print(correct)
            print(consider)
            break

if __name__ == "__main__":
    #stress_test(999)
    print(edit_distance_iter(input(), input()))
