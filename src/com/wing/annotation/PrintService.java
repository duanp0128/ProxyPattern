package com.wing.annotation;

import java.lang.reflect.Proxy;

interface Observable {
    public void print1();
    public void print2();
}

public class PrintService implements Observable {
    private static PrintService instance;
    private static Observable proxied;
    private PrintService() {

    }
    public static Observable getProxied() {
        // called to instantiate PrintService so that all annotated methods are intercepted
        if (proxied == null) {
            Class<?>[] interfaces = {Observable.class};
            /*
                Proxy provides static methods for creating dynamic proxy classes and instances,
                and it is also the superclass of all dynamic proxy classes created by those methods.

                Reference:
                    public static Object newProxyInstance(ClassLoader loader,
                                                          Class<?>[] interfaces,
                                                          InvocationHandler h)

                    Returns an instance of a proxy class for the specified interfaces that dispatches method invocations to the specified invocation handler.

                Params:
                    loader - the class loader to define the proxy class
                    interfaces - the list of interfaces for the proxy class to implement
                    h - the invocation handler to dispatch method invocations to
              */
            proxied = (Observable) Proxy.newProxyInstance(MyInvocationHandler.class.getClassLoader(),
                                                          interfaces,
                                                          new MyInvocationHandler(getInstance()));
        }
        return proxied;
    }

    private static Observable getInstance() {
        // for internal use only
        if (instance == null) {
            instance = new PrintService();
        }
        return instance;
    }

    @Before
    public void print1() {
        System.out.println("print1");
    }

    @Override
    public void print2() {
        System.out.println("print2");
    }
}
