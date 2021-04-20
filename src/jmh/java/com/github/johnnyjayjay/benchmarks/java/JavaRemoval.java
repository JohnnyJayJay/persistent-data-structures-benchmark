package com.github.johnnyjayjay.benchmarks.java;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(batchSize = REMOVE_OPS)
public class JavaRemoval {

    @State(Scope.Thread)
    public static class ArrayListState {
        ArrayList<String> arrayList;

        @Setup(Level.Iteration)
        public void setUp() {
            arrayList = new ArrayList<>(Arrays.asList(elements()));
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            arrayList = null;
        }

    }

    @State(Scope.Thread)
    public static class LinkedListState {
        LinkedList<String> linkedList;

        @Setup(Level.Iteration)
        public void setUp() {
            linkedList = new LinkedList<>(Arrays.asList(elements()));
            shuffleElements();
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            linkedList = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        int index;
        HashSet<String> hashSet;

        @Setup(Level.Iteration)
        public void setUp() {
            hashSet = new HashSet<>(Arrays.asList(elements()));
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            hashSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        int index;
        HashMap<String, String> hashMap;

        @Setup(Level.Iteration)
        public void setUp() {
            hashMap = new HashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
                hashMap.put(element, "");
            }
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            hashMap = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class LinkedHashSetState {
        int index;
        LinkedHashSet<String> linkedHashSet;

        @Setup(Level.Iteration)
        public void setUp() {
            linkedHashSet = new LinkedHashSet<>(Arrays.asList(elements()));
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            linkedHashSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class LinkedHashMapState {
        int index;
        LinkedHashMap<String, String> linkedHashMap;

        @Setup(Level.Iteration)
        public void setUp() {
            linkedHashMap = new LinkedHashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
                linkedHashMap.put(element, "");
            }
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            linkedHashMap = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class TreeSetState {
        int index;
        TreeSet<String> treeSet;

        @Setup(Level.Iteration)
        public void setUp() {
            treeSet = new TreeSet<>(Arrays.asList(elements()));
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            treeSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class TreeMapState {
        int index;
        TreeMap<String, String> treeMap;

        @Setup(Level.Iteration)
        public void setUp() {
            treeMap = new TreeMap<>();
            for (String element : elements()) {
                treeMap.put(element, "");
            }
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            treeMap = null;
            index = 0;
        }
    }

    @Benchmark
    public void benchmarkArrayList(ArrayListState state) {
        state.arrayList.remove(randomIndex());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedList(LinkedListState state) {
        state.linkedList.pollFirst();
        state.linkedList.pollLast();
    }

    @Benchmark
    public void benchmarkHashSet(HashSetState state) {
        state.hashSet.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkHashMap(HashMapState state) {
        state.hashMap.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkLinkedHashSet(LinkedHashSetState state) {
        state.linkedHashSet.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkHashLinkedMap(LinkedHashMapState state) {
        state.linkedHashMap.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkTreeMap(TreeMapState state) {
        state.treeMap.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkTreeSet(TreeSetState state) {
        state.treeSet.remove(elements()[state.index++]);
    }

}
