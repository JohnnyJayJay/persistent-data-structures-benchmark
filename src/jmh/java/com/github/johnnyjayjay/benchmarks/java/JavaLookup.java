package com.github.johnnyjayjay.benchmarks.java;

import com.github.johnnyjayjay.benchmarks.RandomString;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 3)
public class JavaLookup {

    @State(Scope.Thread)
    public static class ArrayListState {
        ArrayList<String> arrayList;

        @Setup
        public void setUp() {
            arrayList = new ArrayList<>(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            arrayList = null;
        }
    }

    @State(Scope.Thread)
    public static class LinkedListState {
        LinkedList<String> linkedList;

        @Setup
        public void setUp() {
            linkedList = new LinkedList<>(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            linkedList = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        HashSet<String> hashSet;

        @Setup
        public void setUp() {
            hashSet = new HashSet<>(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            hashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        HashMap<String, String> hashMap;

        @Setup
        public void setUp() {
            hashMap = new HashMap<>(SIZE);
            for (String element : ELEMENTS) {
                hashMap.put(element, "");
            }
        }

        @TearDown
        public void tearDown() {
            hashMap = null;
        }
    }

    @State(Scope.Thread)
    public static class LinkedHashSetState {
        LinkedHashSet<String> linkedHashSet;

        @Setup
        public void setUp() {
            linkedHashSet = new LinkedHashSet<>(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            linkedHashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class LinkedHashMapState {
        LinkedHashMap<String, String> linkedHashMap;

        @Setup
        public void setUp() {
            linkedHashMap = new LinkedHashMap<>(SIZE);
            for (String element : ELEMENTS) {
                linkedHashMap.put(element, "");
            }
        }

        @TearDown
        public void tearDown() {
            linkedHashMap = null;
        }
    }

    @State(Scope.Thread)
    public static class TreeSetState {
        TreeSet<String> treeSet;

        @Setup
        public void setUp() {
            treeSet = new TreeSet<>(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            treeSet = null;
        }
    }

    @State(Scope.Thread)
    public static class TreeMapState {
        TreeMap<String, String> treeMap;

        @Setup
        public void setUp() {
            treeMap = new TreeMap<>();
            for (String element : ELEMENTS) {
                treeMap.put(element, "");
            }
        }

        @TearDown
        public void tearDown() {
            treeMap = null;
        }
    }

    @Benchmark
    public void benchmarkArrayList(ArrayListState state) {
        Object x = state.arrayList.get(randomIndex());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedList(LinkedListState state) {
        Object x = state.linkedList.getFirst();
        Object y = state.linkedList.getLast();
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(HashSetState state) {
        boolean included = state.hashSet.contains(randomElement());
        boolean notIncluded = state.hashSet.contains(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(HashMapState state) {
        Object included = state.hashMap.get(randomElement());
        Object notIncluded = state.hashMap.get(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedHashSet(LinkedHashSetState state) {
        boolean included = state.linkedHashSet.contains(randomElement());
        boolean notIncluded = state.linkedHashSet.contains(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashLinkedMap(LinkedHashMapState state) {
        Object included = state.linkedHashMap.get(randomElement());
        Object notIncluded = state.linkedHashMap.get(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeMap(TreeMapState state) {
        Object included = state.treeMap.get(randomElement());
        Object notIncluded = state.treeMap.get(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeSet(TreeSetState state) {
        boolean included = state.treeSet.contains(randomElement());
        boolean notIncluded = state.treeSet.contains(RandomString.create());
    }
}
