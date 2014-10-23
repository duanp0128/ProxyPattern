package com.wing.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object proxied;

    public MyInvocationHandler(Object proxied) {
            this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /*
            Params:
                proxy - the proxy instance that the method was invoked on
                method - the Method instance corresponding to the interface method invoked on the proxy instance. The declaring class of the Method object will be the interface that the method was declared in, which may be a superinterface of the proxy interface that the proxy class inherits the method through.
         */

        /*
            At the moment `method` is invoked from `proxy` which only implements the specified interfaces (in this case `Observable`),
            thus has no knowledge of annotations in the leafmost class (in this case `PrintService`).

            To acknowledge the intercepted `method` of the existence of annotations,
            one has to transform `method` to `m` which is of proxied class `PrintService`.
        */
        Method m = this.proxied.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (m.isAnnotationPresent(Before.class)) {
            System.out.println("LALALA");
        }
        return method.invoke(proxied, args);
    }
}
