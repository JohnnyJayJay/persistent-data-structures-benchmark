package com.github.johnnyjayjay.benchmarks.clojure;

import clojure.java.api.Clojure;

class ClojureFix {

    static void run() {
        // I don't know why, but if I don't do this,
        // Clojure has an error internally when loading the PersistentTreeSet/Map classes.
        Clojure.var("clojure.core/*loaded-libs*");
    }

}
