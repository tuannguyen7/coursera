#Uses python3

import sys

def reach(adj, x, y):
    #write your code here
    visited = [False for _ in range(len(adj))]
    return explore(adj, x, y, visited)
    #if re
    #return 0

def explore(adj, x, y, visited):
    neightbors = adj[x]
    re = 0
    visited[x] = True
    for nbor in neightbors:
        if nbor == y:
            return 1
        if not visited[nbor]:
            re = explore(adj, nbor, y, visited)
            if re == 1:
                return 1
    return re

if __name__ == '__main__':
    input = sys.stdin.read()
    data = list(map(int, input.split()))
    n, m = data[0:2]
    data = data[2:]
    edges = list(zip(data[0:(2 * m):2], data[1:(2 * m):2]))
    x, y = data[2 * m:]
    adj = [[] for _ in range(n)]
    x, y = x - 1, y - 1
    for (a, b) in edges:
        adj[a - 1].append(b - 1)
        adj[b - 1].append(a - 1)
    print(reach(adj, x, y))
