config-utils
=========

config-utils is a small library that can be used to configure your application from an arbitrary source. Provided is a loader for reading configuration from DNS TXT records. The configuration is cached using com.google.common.cache.LoadingCache. The implemented cache loader is implemented in a non blocking fashion, and will serve stale data while a refresh is underway.

````
Note!
The maven project contains dependencies to a private repository.
To compile outside of hitta.se, remove these dependencies and replace with whatever is needed.
````
