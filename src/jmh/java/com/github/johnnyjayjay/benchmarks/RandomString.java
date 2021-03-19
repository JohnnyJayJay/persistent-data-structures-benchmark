package com.github.johnnyjayjay.benchmarks;

import java.util.Random;

public final class RandomString {

    private static final Random RANDOM = new Random();

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

    public static String[] shuffledElements() {
        String[] shuffled = ELEMENTS.clone();
        for (int i = 0; i < SIZE; i++) {
            int swapIndex = randomIndex();
            String temp = shuffled[swapIndex];
            shuffled[swapIndex] = shuffled[i];
            shuffled[i] = temp;
        }
        return shuffled;
    }

    public static int randomIndex() {
        return RANDOM.nextInt(SIZE);
    }

    public static String randomElement() {
        return ELEMENTS[randomIndex()];
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
