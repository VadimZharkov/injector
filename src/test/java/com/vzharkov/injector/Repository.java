package com.vzharkov.injector;

import com.vzharkov.injector.annotation.Inject;

public class Repository {
    @Inject
    Service service;
    @Inject
    StaticService staticService;
}
