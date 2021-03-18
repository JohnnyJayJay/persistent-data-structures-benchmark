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

First of all and most importantly:
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

## How to run

## Results