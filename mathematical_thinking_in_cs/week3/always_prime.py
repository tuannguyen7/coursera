from math import sqrt

# find a counter example for the conjecture: 
# for every integer n > 1n>1 the number n^2 + n + 41 is prime (is not a product of two smaller integer)

def find_counter():
    for n in range(2, 200):
        sum = n**2 + n + 41
        if not is_prime(sum):
            print(n)
            return


def is_prime(n):
    for i in range(2, int(sqrt(n))):
        if n % i == 0:
            return False
    return True


if __name__ == '__main__':
    find_counter()
