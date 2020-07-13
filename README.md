## Injector

**Usage example:**

For example we need to inject a repository into a service.
```java
class Service {
    @Inject
    Repository repository;
}
```   

First we define the provider class.
```java
class RepositoryProvider {
    @Provide
    Repository provideRepository() {
        return new Repository();
    }
}
```  

Then register it in the injector and create an instance of the service.
```java
Injector injector = Injector.of(RepositoryProvider.class);
Service service = injector.getInstance(Service.class);
```  
