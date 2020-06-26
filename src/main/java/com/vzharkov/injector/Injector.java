package com.vzharkov.injector;

import com.vzharkov.injector.annotation.Inject;
import com.vzharkov.injector.annotation.Provide;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Injector {
    private final Map<Class<?>, Supplier<?>> providers = new HashMap<>();

    public static Injector of(Class<?>... classes) {
        return new Injector(classes);
    }

    public Injector(Class<?>... classes) {
        this.register(classes);
    }

    public void register(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            final Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                final Provide provide = method.getAnnotation(Provide.class);
                if (provide != null) {
                    final Class<?> returnType = method.getReturnType();
                    final Supplier<?> supplier = () -> {
                        try {
                            if (!Modifier.isStatic(method.getModifiers())) {
                                Object o = clazz.getConstructor().newInstance();
                                return method.invoke(o);
                            } else {
                                return method.invoke(null);
                            }
                        } catch (Exception e) {
                            throw new InjectorException(e);
                        }
                    };
                    providers.put(returnType, supplier);
                }
            }
        }
    }

    public <T> T getInstance(final Class<T> clazz) {
        try {
            final T t = clazz.getConstructor().newInstance();
            final Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                final Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    final Class<?> injectedFieldType = field.getType();
                    final Supplier<?> supplier = providers.get(injectedFieldType);
                    final Object objectToInject = supplier.get();
                    field.setAccessible(true);
                    field.set(t, objectToInject);
                }
            }
            return t;
        } catch (Exception e) {
            throw new InjectorException(e);
        }
    }

    public <T> boolean isRegistered(final Class<T> clazz) {
        return providers.containsKey(clazz);
    }
}