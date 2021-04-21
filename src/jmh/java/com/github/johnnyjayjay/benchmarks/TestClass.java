package com.github.johnnyjayjay.benchmarks;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.ISeq;
import clojure.lang.PersistentTreeSet;
import clojure.lang.RT;
import clojure.lang.Ref;
import clojure.lang.Var;

public class TestClass {

    public static void main(String[] args) {
        var v = (Var) Clojure.var("clojure.core/*loaded-libs*");
/*        ((Ref) v.getRawRoot()).addWatch("my-watch", new IFn() {
            @Override
            public Object invoke() {
                return null;

            }

            @Override
            public Object invoke(Object arg1) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
                System.out.println("changed");
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16, Object arg17) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16, Object arg17, Object arg18) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16, Object arg17, Object arg18, Object arg19) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20) {
                return null;
            }

            @Override
            public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13, Object arg14, Object arg15, Object arg16, Object arg17, Object arg18, Object arg19, Object arg20, Object... args) {
                return null;
            }

            @Override
            public Object applyTo(ISeq arglist) {
                return null;
            }

            @Override
            public void run() {

            }

            @Override
            public Object call() throws Exception {
                return null;
            }
        })*/;
        System.out.println(PersistentTreeSet.EMPTY);
    }
}
