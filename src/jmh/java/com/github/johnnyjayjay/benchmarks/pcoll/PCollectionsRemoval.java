package com.github.johnnyjayjay.benchmarks.pcoll;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
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
@Measurement(batchSize = REMOVE_OPS)
public class PCollectionsRemoval {

    @State(Scope.Thread)
    public static class VectorState {
        TreePVector<String> vector;

        @Setup(Level.Iteration)
        public void setUp() {
            vector = TreePVector.from(Arrays.asList(elements()));
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Thread)
    public static class StackState {
        private ConsPStack<String> stack;

        @Setup(Level.Iteration)
        public void setUp() {
            stack = ConsPStack.from(Arrays.asList(elements()));
            shuffleElements();
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            stack = null;
        }
    }

    @State(Scope.Thread)
    public static class SetState {
        int index;
        private MapPSet<String> set;

        @Setup(Level.Iteration)
        public void setUp() {
            set = HashTreePSet.from(Arrays.asList(elements()));
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            set = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class MapState {
        int index;
        private HashPMap<String, String> map;

        @Setup(Level.Iteration)
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>(ELEMENTS_SIZE);
            for (String element : elements()) {
                transientMap.put(element, "");
            }
            map = HashTreePMap.from(transientMap);
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            map = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class BagState {
        int index;
        MapPBag<String> bag;

        @Setup(Level.Iteration)
        public void setUp() {
            bag = HashTreePBag.from(Arrays.asList(elements()));
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            bag = null;
            index = 0;
        }
    }

    @Benchmark
    public void benchmarkVector(VectorState state) {
        state.vector = state.vector.minus(randomIndex());
    }

    @Benchmark
    public void benchmarkStack(StackState state) {
        state.stack = state.stack.minus(0);
    }

    @Benchmark
    public void benchmarkSet(SetState state) {
        state.set = state.set.minus(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkMap(MapState state) {
        state.map = state.map.minus(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkBag(BagState state) {
        state.bag = state.bag.minus(elements()[state.index++]);
    }

}
