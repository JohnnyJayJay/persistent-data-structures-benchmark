package com.github.johnnyjayjay.benchmarks;

import java.util.Random;

import static com.github.johnnyjayjay.benchmarks.Global.RANDOM;

public final class RandomString {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 42;
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    private RandomString() {

    }

    public static String create() {
        int length = RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH) + MIN_LENGTH;
        StringBuilder builder = new StringBuilder();
        int bound = ALPHABET.length;
        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET[RANDOM.nextInt(bound)]);
        }
        return builder.toString();
    }

}
