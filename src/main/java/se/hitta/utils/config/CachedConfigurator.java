package se.hitta.utils.config;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public class CachedConfigurator
{
    private static final String CACHE_KEY = "config";
    private final LoadingCache<String, Configuration> cache;

    public CachedConfigurator(int refreshTime, TimeUnit refreshTimeUnit, ConfigurationCacheLoader loader)
    {
        this.cache = CacheBuilder.newBuilder().maximumSize(1).refreshAfterWrite(refreshTime, refreshTimeUnit).build(loader);
    }
    
    public Optional<String> get(String key)
    {
        try
        {
            Configuration config = cache.get(CACHE_KEY);

            return config.get(key);
            
        } catch (Throwable e)
        {
            return Optional.absent();
        }
    }
    

}
