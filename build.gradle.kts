plugins {
    java
    id("me.champeau.jmh") version "0.6.2"
}

group = "com.github.johnnyjayjay"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    jmh("org.jetbrains.kotlinx", "kotlinx-collections-immutable", "0.3.3")
    jmh("org.clojure", "clojure", "1.10.3")
    jmh("org.pcollections", "pcollections", "3.1.4")
}

jmh {
    //this.includes.set(listOf("PCollectionsAddition\\..*"))
    fork.set(3)
    forceGC.set(true)
    resultFormat.set("csv")
}
