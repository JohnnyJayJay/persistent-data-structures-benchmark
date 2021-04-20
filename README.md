# persistent-collections-benchmark

A set of benchmarks comparing the performance of various operations from four different JVM collections frameworks.

[Jump to the results](#Results)

## Goals

The goal of these benchmarks is to compare **addition**, **removal** and **lookup** on different types of 
persistent collections from three different JVM libraries/languages:

- [clojure.lang](https://clojure.org/reference/data_structures#Collections) (Clojure)
- [kotlinx.collections.immutable](https://github.com/Kotlin/kotlinx.collections.immutable) (Kotlin)
- [pcollections](https://github.com/hrldcpr/pcollections) (Java)

Another goal is to see how persistent collections compare to the mutable
[java.util Collections framework](https://docs.oracle.com/javase/tutorial/collections/intro/index.html).

## Non-Goals

First and most importantly:
**This benchmark does not aim to determine what collections are ultimately better than others in general.**

It does not emulate a real-world-scenario, because the operations are each benchmarked individually. 
In reality, most uses of collections involve addition, removal and lookup all at once and in a much 
less concentrated fashion. Also, the size of the collections is a significant factor when it comes to speed, 
so there may be notable differences in results when focusing more on collections of different sizes.

Furthermore, this benchmark only considers the ways these collections are *supposed to be* used, i.e.,
it examines the performance of the operations the respective collections were built and optimised for 
(e.g. value-/key-based lookup on sets/maps, random access on vectors/arraylists, 
head/tail lookup on LinkedLists/queues/stacks).
Thus, there are no benchmarks measuring the performance of random access 
on non-random-access collections, for example.

Lastly, the benchmarks do not cover every operation. For instance, Clojure's vectors and sorted maps 
support a constant time reversing operation that is not included.

## Structure

Below you can see how many collections are benchmarked in each category for each library. The last column gives information about how long each benchmark takes.

| Benchmark/Subject Benchmark count | Java | PCollections | Kotlin | Clojure |                                     |
| --------------------------------- | ---- | ------------ | ------ | ------- | ----------------------------------- |
| Addition                          | 8    | 5            | 5      | 7       | 5 * 1M ops Warmup, 5 * 1M ops Measurement |
| Removal                           | 8    | 5            | 5      | 6       | 5 * 1M ops Warmup, 5 * 1M ops Measurement  |
| Lookup                            | 8    | 5            | 5      | 7       | 5 * 10s Warmup, 3 * 10s Measurement |

Here are all the collections that are benchmarked. Clojure is missing the removal benchmark for its `PersistentVector`, because it doesn't define an index-based removal operation.

| Data Structure/Library Equivalent   | Java            | PCollections   | Kotlin                 | Clojure             |
| ----------------------------------- | --------------- | -------------- | ---------------------- | ------------------- |
| Random Access List                  | `ArrayList`     | `TreePVector`  | `PersistentVector`     | `PersistentVector`  |
| Queue (FIFO)                        | `LinkedList`    | -              | -                      | `PersistentQueue`   |
| Stack (LIFO)                        | `LinkedList`    | `ConsPStack`   | -                      | `PersistentList`    |
| Unordered Set                       | `HashSet`       | `HashTreePSet` | `PersistentHashSet`    | `PersistentHashSet` |
| Unordered Map                       | `HashMap`       | `HashTreePMap` | `PersistentHashMap`    | `PersistentHashMap` |
| Linked Set (entry order)            | `LinkedHashSet` | -              | `PersistentOrderedSet` | -                   |
| Linked Map (entry order)            | `LinkedHashMap` | -              | `PersistentOrderedMap` | -                   |
| Sorted Set                          | `TreeSet`       | -              | -                      | `PersistentTreeSet` |
| Sorted Map                          | `TreeMap`       | -              | -                      | `PersistentTreeMap` |
| Bag (unordered, duplicate elements) | -               | `HashTreePBag` | -                      | -                   |

- The addition benchmarks fill an empty collection with random strings each iteration

- The removal benchmarks remove elements from a fresh collection each iteration

- The lookup benchmarks lookup random elements/indices from a collection

Every single benchmark is forked 3 times to make up for differences in VM configurations and other environmental factors.

## How to run

If you do want to run everything, do the following:

```
$ git clone https://github.com/johnnyjayjay/persistent-data-structures-benchmark
$ cd persistent-data-structures-benchmark
$ ./gradlew jmh
```

## Results