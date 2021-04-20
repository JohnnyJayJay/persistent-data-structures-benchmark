package com.github.johnnyjayjay.benchmarks;

import java.util.Random;

public final class Global {

    public static final Random RANDOM = new Random();

    public static final int ADD_MEAS_ITER = 10;
    public static final int ADD_WARMUP_ITER = 5;
    public static final int REMOVE_MEAS_ITER = 10;
    public static final int REMOVE_WARMUP_ITER = 5;
    public static final int ADD_OPS = 1_000_000;
    public static final int REMOVE_OPS = 1_000_000;
    public static final int ELEMENTS_SIZE = 1_001_000;

    private static String[] elements = null;

    public static String[] elements() {
        if (elements != null) {
            return elements;
        }
        final String[] array = new String[ELEMENTS_SIZE];
        for (int i = 0; i < ELEMENTS_SIZE; i++) {
            array[i] = RandomString.create();
        }
        elements = array;
        return array;
    }

    public static void shuffleElements() {
        for (int i = ELEMENTS_SIZE - 1; i >= 0; i--) {
            final int j = RANDOM.nextInt(i + 1);
            final String temp = elements[i];
            elements[i] = elements[j];
            elements[j] = temp;
        }
    }

    public static int randomIndex() {
        return RANDOM.nextInt(ELEMENTS_SIZE);
    }

    public static String randomElement() {
        return elements[randomIndex()];
    }
}
