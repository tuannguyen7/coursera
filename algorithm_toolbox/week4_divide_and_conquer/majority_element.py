# Uses python3
import sys

def get_majority_element(a, left, right):
    if left == right:
        return -1
    if left + 1 == right:
        return a[left]
    #write your code here
    mid = (left + right)//2
    majority1 = get_majority_element(a, left, mid)
    majority2 = get_majority_element(a, mid, right)
    return merge(a, left, right, majority1, majority2)

def merge(a, left, right, majority1, majority2):
    count1 = 0
    count2 = 0
    if majority1 == majority2:
        return majority1
    #if majority1 == -1 and majority2 == -1:
    #    return -1
    for i in range(left, right):
        if a[i] == majority1:
            count1 += 1
        elif a[i] == majority2:
            count2 += 1
    if count1 > (right - left)//2:
        return count1
    if count2 > (right - left)//2:
        return count2
    return -1
    
if __name__ == '__main__':
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    a.sort()
    if get_majority_element(a, 0, n) != -1:
        print(1)
    else:
        print(0)
