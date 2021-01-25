# fast algorithim to calculate b^e mod m
def FastModularExponentiation(b, e, m):
    def helper(b, k, m):
        result = b
        for i in range(k):
            result = (result*result)%m
        return result
      
    binary_e = bin(e)
    result = 1
    for exp, bin_num in enumerate(binary_e[::-1]):
        if bin_num == '1':
            result = (result * helper(b, exp, m))%m
    return result


# fast algorithim to calculate b^(2^e) mod m
def FastModularExponentiation_two(b, k, m):
    result = b
    for i in range(k):
        result = (result*result)%m
    return result


if __name__ == '__main__':
    print(FastModularExponentiation(2, 238, 239))
    print(FastModularExponentiation(2, 953, 239))
    print(FastModularExponentiation(34, 60, 77))
    assert FastModularExponentiation_two(7, 7, 101) == pow(7, 128, 101)
    assert FastModularExponentiation(3, 231, 934) == pow(3, 231, 934)
