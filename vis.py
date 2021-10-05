file = open('result.txt', 'r')
lines = list(map(lambda x: list(map(int, x)), [x.strip().split(' ') for x in file.readlines()]))

# print(lines)
duration = []
processing = []
size = []

for line in lines:
    processing.append(line[0])
    duration.append(line[1])
    size.append(line[2])

print(size)

# for fileName in files:
#     if not fileName.endswith('.txt'):
#         continue
#
#     print(fileName)
#     currentFile = open(fileName, 'r')
#     values = list(map(int, map(lambda x: x.strip(), currentFile.readlines()[0:-2])))
#     values.sort()
#
#     d = {}
#     for v in values:
#         second = v // 1000
#         x = d.get(second)
#         if x is None:
#             x = 0
#         d[second] = x + 1
#     print('Количество запросов в каждую секунду', d)
#
#     deltas = []
#     for i in range(len(values) - 1):
#         deltas.append(values[i + 1] - values[i])
#     deltas.sort()
#
#     bigDeltas = list(filter(lambda x: x > 500, deltas))
#     print("Задержка больше 500", len(bigDeltas), bigDeltas)
#     print("Минимальнаый промежуток, медианный промежуток, максимальный промежуток", deltas[0], deltas[len(deltas) // 2], deltas[-1])
#     print("Колечство обработанных запросов", len(values))
