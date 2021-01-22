package ru.clevertec.tests;

import ru.clevertec.annotations.LogThisMethod;

public class TestClass {
String str = "=-=-===--=-=-=-=-=-";

    public void printA() {
        System.out.println("A");
    }

    @LogThisMethod
    public int getInt() {
        return 33333;
    }

}
