package com.vzharkov.injector;

import com.vzharkov.injector.annotation.Provide;

public class ServiceProvider {
    @Provide
    Service provideService() {
        return new Service();
    }
}
