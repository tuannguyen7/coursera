# Uses python3
def evalt(a, b, op):
    if op == '+':
        return a + b
    elif op == '-':
        return a - b
    elif op == '*':
        return a * b
    else:
        assert False

def get_maximum_value(dataset):
    #write your code here
    # construct dataset
    numbers = []
    operations = []
    for c in dataset:
        if c >= '0' and c <= '9':
            numbers.append(int(c))
        else:
            operations.append(c)
    M = [[99999 for _ in range(len(numbers))] for _ in range(len(numbers))]
    m = [[-99999 for _ in range(len(numbers))] for _ in range(len(numbers))]
    minAndMax2(numbers, operations, 0, len(numbers)-1, M, m)
    #print(M)
    return M[0][len(numbers)-1]

def minAndMax2(numbers, operations, left, right, M, m):
    if right == left:
        M[left][right] = numbers[right]
        m[left][right] = numbers[right]
        return (M[left][right], m[left][right])

    elif right - left == 1:
        M[left][right] = evalt(numbers[left], numbers[right], operations[left])
        m[left][right] = M[left][right]
        return (M[left][right], m[left][right])
    if M[left][right] != 99999:
        return (M[left][right], m[left][right])

    minVal = 99999
    maxVal = -99999
    for i in range(left, right):
        leftMin, leftMax = minAndMax2(numbers, operations, left, i, M, m)
        rightMin, rightMax = minAndMax2(numbers, operations, i+1, right, M, m)
        a = evalt(leftMin, rightMin, operations[i])    # leftMin op
        b = evalt(leftMin, rightMax, operations[i])    # leftMax op 
        c = evalt(leftMax, rightMin, operations[i])
        d = evalt(leftMax, rightMax, operations[i])
        minVal = min(minVal, a, b, c, d)
        maxVal = max(maxVal, a, b, c, d)
    M[left][right] = maxVal
    m[left][right] = minVal
    return (minVal, maxVal)

# noo: number of operations already computed
def minAndMax(numbers, operations, left, right, M, m):
    minVal = 99999
    maxVal = -99999
    for i in range(left, right):
        a = evalt(M[left][i], M[i][right], operations[i])
        b = evalt(M[left][i], m[i][right], operations[i])
        c = evalt(m[left][i], M[i][right], operations[i])
        d = evalt(m[left][i], m[i][right], operations[i])
        minVal = min(minVal, a, b, c, d)
        maxVal = min(maxVal, a, b, c, d)
    return (minVal, maxVal)

if __name__ == "__main__":
    print(get_maximum_value(input()))
