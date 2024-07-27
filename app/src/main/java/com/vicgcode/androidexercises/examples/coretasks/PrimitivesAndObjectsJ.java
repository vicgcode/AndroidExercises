package com.vicgcode.androidexercises.examples.coretasks;

public class PrimitivesAndObjectsJ {
    public static void main(String[] args) {
        Integer int1 = 1;
        Integer int2 = 1;

        System.out.println(int1 == int2);
        System.out.println(int1.equals(int2));

        int int3 = 1;
        int int4 = 1;

        System.out.println(int1 == int2);

        String string1 = "Hello";
        String string2 = "Hello";
        // Create new string in string pool
        String string3 = new String("Hello");

        System.out.println(string1 == string2);
        System.out.println(string1.equals(string2));
        System.out.println(string1.equals(string3));
    }
}
