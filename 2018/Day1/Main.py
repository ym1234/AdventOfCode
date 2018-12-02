#!/usr/bin/env python3
import operator
def scan_right(l, d, x):
    last = d
    while True:
        for i in l:
            last = x(last, i)
            yield last

with open("input.txt") as f:
    content = [int(x) for x in f.readlines()]
    part1 = sum(content)
    results = set()

    for i in scan_right(content, 0, operator.add):
        if i in results:
            part2 = i
            break
        else:
            results.add(i)

    print(f'{part1}, {part2}')

# From reddit, Huh this is a pretty nice solution, didn't known that these existed lmao
# from itertools import accumulate, cycle
# seen = set()
# print(next(f for f in accumulate(cycle(changes)) if f in seen or seen.add(f)))
