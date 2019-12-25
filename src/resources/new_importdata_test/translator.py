import pandas as pd
import os
import json

def getNameId(s):
    """
    去除s中以`name:id(`开头的元素，并返回该元素。
    """
    for i in range(len(s)):
        if s[i].startswith('name:id('):
            ret = s[i]
            s.pop(i)
            return ret


def translate(inFile, outFile, mapping=None):
    data = pd.read_csv(inFile)
    label = ':LABEL'
    column = list(data.columns)
    column.remove(label)
    nameid = getNameId(column)

    with open(outFile, 'w+', encoding='utf8') as f:
        print('subject', 'predicate', 'object', sep=',', file=f)
        for i in data.iterrows():
            i = i[1]
            if mapping:
                print(i[label], mapping[nameid], i[nameid], sep=',', file=f)
                for j in column:
                    print(i[nameid], mapping[j], i[j], sep=',', file=f)
            else:
                print(i[label], nameid, i[nameid], sep=',', file=f)
                for j in column:
                    print(i[nameid], j, i[j], sep=',', file=f)


def files_to_translate():
    files = []
    for i in os.listdir():
        if os.path.isdir(i):
            continue
        if i.endswith('_attribute.csv'):
            j = i.replace('.csv', '_triple.csv')
            yield i, j




if __name__ == "__main__":
    mapping = json.load(open('mapping.json', encoding='utf8'))
    # print(mapping)
    for inFile, outFile in files_to_translate():
        translate(inFile, outFile, mapping[inFile])
