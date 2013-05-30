config-utils
=========

config-utils is a small library that can be used to configure your application from an arbitrary source. Provided is a loader for reading configuration from DNS TXT records. The configuration is cached using com.google.common.cache.LoadingCache. The implemented cache loader is implemented in a non blocking fashion, and will serve stale data while a refresh is underway.

### Example usage
One possible use case could be to rely on the search domain to get different configurations for different zones. E.g. with the following DNS records:
    
    config.nyc.acme.com TXT "coast:east anotherkey:value"
    config.la.acme.com TXT "coast:west anotherkey:value"
    
the following code can be used to find out where the application is executed (assuming the search domain is either "nyc.acme.com" or "la.acme.com"):

```java
//will try to read TXT-record from config.<searchdomain>
ConfigurationLoader loader = new DnsConfigurationLoader("config");

//set up a cache loader
ConfigurationCacheLoader cacheLoader = new ConfigurationCacheLoader(loader);

//create your cached configurator
CachedConfigurator configurator = new CachedConfigurator(2, TimeUnit.MINUTES, cacheLoader);

//try to read a value from the cached configuration
Optional<String> coast = configurator.get("coast");

if(coast.isPresent()){
    System.out.println(String.format("you are on the %s coast", coast.get()));            
}
```

````
Note!
The maven project contains dependencies to a private repository.
To compile outside of hitta.se, remove these dependencies and replace with whatever is needed.
````
