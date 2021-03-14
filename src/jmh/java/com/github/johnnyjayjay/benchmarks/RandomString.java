package com.github.johnnyjayjay.benchmarks;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class RandomString {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 42;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private RandomString() {

    }

    public static String create() {
        int length = ThreadLocalRandom.current().nextInt(MIN_LENGTH, MAX_LENGTH);
        return ThreadLocalRandom.current().ints(length, 0, ALPHABET.length())
                .map(ALPHABET::charAt)
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
    }

}
