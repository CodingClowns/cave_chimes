package com.fincode.cavechimes.util;

public class Logic {
    public static boolean xor(boolean a, boolean b) {
        return (a || b) && !(a && b);
    }
}
