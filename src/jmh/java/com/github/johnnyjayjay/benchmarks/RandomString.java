package com.github.johnnyjayjay.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class RandomString {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 42;
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static final int SIZE = 500_000_000;
    public static final String[] ELEMENTS = new String[SIZE];

    static {
        for (int i = 0; i < SIZE; i++) {
            ELEMENTS[i] = create();
        }
    }

    private RandomString() {

    }

    public static int randomIndex() {
        return ThreadLocalRandom.current().nextInt(SIZE);
    }

    public static String randomElement() {
        return ELEMENTS[ThreadLocalRandom.current().nextInt(SIZE)];
    }

    public static String create() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int length = random.nextInt(MIN_LENGTH, MAX_LENGTH);
        StringBuilder builder = new StringBuilder();
        int bound = ALPHABET.length;
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET[random.nextInt(bound)]);
        }
        return builder.toString();
    }

}
