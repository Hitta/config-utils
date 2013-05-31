/*
 * Copyright 2013 Hittapunktse AB (http://www.hitta.se/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.hitta.utils.config;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public class CachedConfigurator
{
    private static final String CACHE_KEY = "config";
    private final LoadingCache<String, Configuration> cache;

    /**
     * The cached configurator will use a {@link ConfigurationCacheLoader} to load key/value based configuration.
     * The cached configurator utilizes a {@link LoadingCache} to handle cached versions of the configuration.
     * The cache will be automatically refreshed using the provided interval. The refresh is non-blocking. Stale data will be served while the refresh is underway.
     * @param refreshTime refresh time
     * @param refreshTimeUnit time unit for refresh time
     * @param loader the loader to use.
     * 
     * @see DnsConfigurationLoader
     */
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
