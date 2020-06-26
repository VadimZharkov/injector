package com.vzharkov.injector;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class InjectorTest {
    static Injector injector;

    @BeforeAll
    static void beforeAll() {
        injector = Injector.of(ServiceProvider.class, StaticServiceProvider.class);
    }

    @Test
    void shouldRegisterProviders() {
        assertThat(injector.isRegistered(ServiceProvider.class), is(true));
        assertThat(injector.isRegistered(StaticServiceProvider.class), is(true));
    }

    @Test
    void shouldCreateInstanceWithInjectedFields() {
        Repository repository = injector.getInstance(Repository.class);

        assertThat(repository, is(notNullValue()));
        assertThat(repository.service, is(notNullValue()));
        assertThat(repository.staticService, is(notNullValue()));
    }
}