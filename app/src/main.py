import math
from collections import defaultdict

def main():
    length = 27
    diff_map = defaultdict(list)

    for x in range(1, int(math.sqrt(length)) + 1):
        for y in range(1, int(length / x) + 1):
            diff_map[str(abs(x - y))].append((x, y))

    pair_map = defaultdict(list)
    for v in diff_map.values():
        for x in v:
            if (a := abs(length - x[0] * x[1])) <= min(x[0], x[1]):
                pair_map[str(a)].append(x)

    print(pair_map)

if __name__ == "__main__":
    main() # somethig???