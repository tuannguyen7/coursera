
# Quiz: max amount that cannot be paid with 5 and 7
def coin_chain():
    coins = [5, 7]
    moneys = [False for i in range(101)]
    moneys[0] = True
    moneys[5] = True
    moneys[7] = True

    for i in range(7, 101):
        if i % 5 == 0 or i % 7 == 0:
            moneys[i] = True
        elif moneys[i - 5]:
            moneys[i] = True
        elif moneys[i-7]:
            moneys[i] = True

    for i in range(100, 0, -1):
        if not moneys[i]:
            print(i)
            return

    return 


# Quiz: return the change for an amount of money with coins 5 and 7
def change(amount):
    
    if amount == 24:
        return [5, 5, 7, 7]
    if amount == 25:
        return [5, 5, 5, 5, 5]
    if amount == 26:
        return [5, 7, 7, 7]
    if amount == 27:
        return [5, 5, 5, 5, 7]
    if amount == 28:
        return [7, 7, 7, 7]

    return change(amount - 5) + [5]


# Quiz: min moves to solve hanoi tower
def hanoi_tower(n):
    if n == 1:
        return 1
    if n == 2:
        return 3

    smaller_n = hanoi_tower(n-1)
    return smaller_n*2 + 1


if __name__ == '__main__':
    print(hanoi_tower(6))
