package com.wing.annotation;

public class Application {
    public static void main(String[] args) {
        com.wing.annotation.Observable printService = PrintService.getProxied();
        printService.print1();
    }
}
