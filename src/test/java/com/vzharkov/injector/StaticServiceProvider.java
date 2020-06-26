package com.vzharkov.injector;

import com.vzharkov.injector.annotation.Provide;

public class StaticServiceProvider {
    @Provide
    public static StaticService provideService() {
        return new StaticService();
    }
}
