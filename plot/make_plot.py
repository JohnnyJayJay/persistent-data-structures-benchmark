import matplotlib
import matplotlib.pyplot as plt
import numpy as np
import sys
import csv
import re

libs = ['Java', 'Clojure', 'Kotlin', 'PCollections']
benchmarks = ['Addition', 'Removal', 'Lookup']
y_labels = {'Addition': 'ms/1M ops', 'Removal': 'ms/1M ops', 'Lookup': 'ns/op'}
coll_types = ['List', 'Stack', 'Set', 'Map']
coll_names = {
    'List': {'Java': 'ArrayList', 'Clojure': 'Vector', 'Kotlin': 'Vector', 'PCollections': 'Vector'},
    'Stack': {'Java': 'LinkedList', 'Clojure': 'List', 'PCollections': 'Stack'},
    'Set': {'Java': 'HashSet', 'Clojure': 'HashSet', 'Kotlin': 'HashSet', 'PCollections': 'Set'},
    'Map': {'Java': 'HashMap', 'Clojure': 'HashMap', 'Kotlin': 'HashMap', 'PCollections': 'Map'}
}

coll_names = {
    'Java': {'ArrayList': 'List', 'LinkedList': 'Stack', 'HashSet': 'Set', 'HashMap': 'Map'},
    'Clojure': {'Vector': 'List', 'List': 'Stack', 'HashSet': 'Set', 'HashMap': 'Map'},
    'Kotlin': {'Vector': 'List', 'HashSet': 'Set', 'HashMap': 'Map'},
    'PCollections': {'Vector': 'List', 'Stack': 'Stack', 'Set': 'Set', 'Map': 'Map'}
}

bench_name_pattern = r'[\w.]+\.(?P<lib>Java|Clojure|Kotlin|PCollections)(?P<bench>Addition|Removal|Lookup)\.benchmark(?P<coll>\w+)'

# Reading result data
result_path = sys.argv[1]
results = {}
with open(result_path, newline='') as file:
    header_reader = csv.reader(file, quoting=csv.QUOTE_ALL)
    header_row = next(header_reader)
    print(header_row)
    csv_reader = csv.DictReader(file, quoting=csv.QUOTE_NONNUMERIC, fieldnames=header_row)
    for row in csv_reader:
        
        m = re.match(bench_name_pattern, row['Benchmark'])
        if m is None:
            continue
        lib = m.group('lib')
        bench = m.group('bench')
        coll = m.group('coll')
        if bench not in results:
             results[bench] = {}
        bench_result = results[bench]
        if coll not in coll_names[lib]:
            continue
        coll_type = coll_names[lib][coll]
        if coll_type not in bench_result:
             bench_result[coll_type] = {}
        coll_result = bench_result[coll_type]
        coll_result[lib] = row
        print(lib, bench, coll)
        

print(results)
list_removal_results = results['Removal']['List']
list_removal_results.pop('Java', None)
list_removal_results.pop('Kotlin', None)

x_axis = np.arange(len(coll_types))  # the label locations
width = 0.2  # the width of the bars

def results_fetcher(bench, lib, field):
    return lambda type: results[bench][type][lib][field] if lib in results[bench][type] else 0

fig, axs = plt.subplots(3)
fig.set_size_inches(18.5, 10.5)
for (ax, bench) in zip(axs, benchmarks):
    java = ax.bar(x_axis - 1.5 * width, list(map(results_fetcher(bench, "Java", "Score"), coll_types)), width, label="Java", yerr=list(map(results_fetcher(bench, "Java", "Score Error (99.9%)"), coll_types)))
    clojure = ax.bar(x_axis - 0.5 * width, list(map(results_fetcher(bench, "Clojure", "Score"), coll_types)), width, label="Clojure", yerr=list(map(results_fetcher(bench, "Clojure", "Score Error (99.9%)"), coll_types)))
    kotlin = ax.bar(x_axis + 0.5 * width, list(map(results_fetcher(bench, "Kotlin", "Score"), coll_types)), width, label="Kotlin", yerr=list(map(results_fetcher(bench, "Kotlin", "Score Error (99.9%)"), coll_types)))
    pcoll = ax.bar(x_axis + 1.5 * width, list(map(results_fetcher(bench, "PCollections", "Score"), coll_types)), width, label="PCollections", yerr=list(map(results_fetcher(bench, "PCollections", "Score Error (99.9%)"), coll_types)))
    ax.set_ylabel(y_labels[bench])
    ax.set_title(f'{bench} Scores')
    ax.set_xticks(x_axis)
    ax.set_xticklabels(coll_types)
    ax.legend()

plt.savefig(sys.argv[2])
