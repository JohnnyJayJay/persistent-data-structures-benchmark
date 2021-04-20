package com.github.johnnyjayjay.benchmarks.pcoll;

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
import org.pcollections.ConsPStack;
import org.pcollections.HashPMap;
import org.pcollections.HashTreePBag;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPBag;
import org.pcollections.MapPSet;
import org.pcollections.TreePVector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PCollectionsLookup {

    @State(Scope.Thread)
    public static class VectorState {
        private TreePVector<String> vector;

        @Setup
        public void setUp() {
            vector = TreePVector.from(Arrays.asList(elements()));
        }

        @TearDown
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Thread)
    public static class StackState {
        private ConsPStack<String> stack;

        @Setup
        public void setUp() {
            stack = ConsPStack.from(Arrays.asList(elements()));
        }

        @TearDown
        public void tearDown() {
            stack = null;
        }
    }

    @State(Scope.Thread)
    public static class SetState {
        private MapPSet<String> set;

        @Setup
        public void setUp() {
            set = HashTreePSet.from(Arrays.asList(elements()));
        }

        @TearDown
        public void tearDown() {
            set = null;
        }
    }

    @State(Scope.Thread)
    public static class MapState {
        private HashPMap<String, String> map;

        @Setup
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
                transientMap.put(element, "");
            }
            map = HashTreePMap.from(transientMap);
        }

        @TearDown
        public void tearDown() {
            map = null;
        }
    }

    @State(Scope.Thread)
    public static class BagState {
        MapPBag<String> bag;

        @Setup
        public void setUp() {
            bag = HashTreePBag.from(Arrays.asList(elements()));
        }

        @TearDown
        public void tearDown() {
            bag = null;
        }
    }

    @Benchmark
    public String benchmarkVector(VectorState state) {
        return state.vector.get(randomIndex());
    }

    @Benchmark
    public String benchmarkList(StackState state) {
        return state.stack.get(0);
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkSet(Blackhole blackhole, SetState state) {
        blackhole.consume(state.set.contains(randomElement()));
        blackhole.consume(state.set.contains(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkMap(Blackhole blackhole, MapState state) {
        blackhole.consume(state.map.get(randomElement()));
        blackhole.consume(state.map.get(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkBag(Blackhole blackhole, BagState state) {
        blackhole.consume(state.bag.contains(randomElement()));
        blackhole.consume(state.bag.contains(RandomString.create()));
    }
}
