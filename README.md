## Injector

**Usage example:**

For example we need to inject a service into a repository.
```java
class Repository {
    @Inject
    Service service;
}
```   

First we define the provider class.
```java
class ServiceProvider {
    @Provide
    Service provideService() {
        return new Service();
    }
}
```  

Then register it in the injector and create an instance of the repository.
```java
Injector injector = Injector.of(ServiceProvider.class);
Repository repository = injector.getInstance(Repository.class);
```  
