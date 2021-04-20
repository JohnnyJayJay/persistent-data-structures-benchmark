package com.github.johnnyjayjay.benchmarks.java;

import com.github.johnnyjayjay.benchmarks.RandomString;
import com.github.johnnyjayjay.benchmarks.Global;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(batchSize = Global.ADD_OPS)
public class JavaAddition {

    private ArrayList<String> arrayList;
    private LinkedList<String> linkedList;
    private HashSet<String> hashSet;
    private HashMap<String, String> hashMap;
    private LinkedHashSet<String> linkedHashSet;
    private LinkedHashMap<String, String> linkedHashMap;
    private TreeSet<String> treeSet;
    private TreeMap<String, String> treeMap;

    @Setup(Level.Iteration)
    public void setUp() {
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        hashSet = new HashSet<>();
        hashMap = new HashMap<>();
        linkedHashSet = new LinkedHashSet<>();
        linkedHashMap = new LinkedHashMap<>();
        treeSet = new TreeSet<>();
        treeMap = new TreeMap<>();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        arrayList = null;
        linkedList = null;
        hashSet = null;
        hashMap = null;
        linkedHashSet = null;
        linkedHashMap = null;
        treeSet = null;
        treeMap = null;
    }

    @Benchmark
    public void benchmarkArrayList() {
        arrayList.add(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkLinkedList() {
        linkedList.addFirst(RandomString.create());
        linkedList.addLast(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashSet() {
        hashSet.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashMap() {
        hashMap.put(RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkLinkedHashSet() {
        linkedHashSet.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkLinkedHashMap() {
        linkedHashMap.put(RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkTreeSet() {
        treeSet.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkTreeMap() {
        treeMap.put(RandomString.create(), "");
    }

}
