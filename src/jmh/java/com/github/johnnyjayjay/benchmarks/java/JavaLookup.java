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
import org.openjdk.jmh.infra.Blackhole;

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
@Measurement(iterations = 3)
public class JavaLookup {

    @State(Scope.Thread)
    public static class ArrayListState {
        ArrayList<String> arrayList;

        @Setup
        public void setUp() {
            arrayList = new ArrayList<>(Arrays.asList(elements()));
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
            linkedList = new LinkedList<>(Arrays.asList(elements()));
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
            hashSet = new HashSet<>(Arrays.asList(elements()));
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
            hashMap = new HashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
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
            linkedHashSet = new LinkedHashSet<>(Arrays.asList(elements()));
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
            linkedHashMap = new LinkedHashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
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
            treeSet = new TreeSet<>(Arrays.asList(elements()));
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
            for (String element : elements()) {
                treeMap.put(element, "");
            }
        }

        @TearDown
        public void tearDown() {
            treeMap = null;
        }
    }

    @Benchmark
    public String benchmarkArrayList(ArrayListState state) {
        return state.arrayList.get(randomIndex());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedList(Blackhole blackhole, LinkedListState state) {
        blackhole.consume(state.linkedList.getFirst());
        blackhole.consume(state.linkedList.getLast());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(Blackhole blackhole, HashSetState state) {
        blackhole.consume(state.hashSet.contains(randomElement()));
        blackhole.consume(state.hashSet.contains(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(Blackhole blackhole, HashMapState state) {
        blackhole.consume(state.hashMap.get(randomElement()));
        blackhole.consume(state.hashMap.get(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedHashSet(Blackhole blackhole, LinkedHashSetState state) {
        blackhole.consume(state.linkedHashSet.contains(randomElement()));
        blackhole.consume(state.linkedHashSet.contains(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashLinkedMap(Blackhole blackhole, LinkedHashMapState state) {
        blackhole.consume(state.linkedHashMap.get(randomElement()));
        blackhole.consume(state.linkedHashMap.get(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeMap(Blackhole blackhole, TreeMapState state) {
        blackhole.consume(state.treeMap.get(randomElement()));
        blackhole.consume(state.treeMap.get(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeSet(Blackhole blackhole, TreeSetState state) {
        blackhole.consume(state.treeSet.contains(randomElement()));
        blackhole.consume(state.treeSet.contains(RandomString.create()));
    }
}
