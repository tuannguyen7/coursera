# Uses python3
import sys

def optimal_weight(W, w):
    # write your code here
    visited = [False for _ in range(len(w))]
    cache = [-1 for _ in range(W + 1)]
    optimal_weight_sol(W, w, visited, cache)
    #print(cache)
    return cache[W]

def optimal_weight_sol(W, w, visited, cache):
    if W <= 0:
        return 0
    if cache[W] != -1:
        return cache[W]
    m = 0
    for i in range(len(w)):
        if not visited[i] and W >= w[i]:
            visited[i] = True
            m = max(m, optimal_weight_sol(W-w[i], w, visited, cache) + w[i])
            visited[i] = False
    cache[W] = m
    return m

if __name__ == '__main__':
    input = sys.stdin.read()
    W, n, *w = list(map(int, input.split()))
    print(optimal_weight(W, w))
