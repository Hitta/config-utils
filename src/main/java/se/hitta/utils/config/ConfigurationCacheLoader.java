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

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Optional;
import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

public class ConfigurationCacheLoader extends CacheLoader<String, Configuration>
{
    private final Executor executor;
    
    final ConfigurationLoader configurationLoader;
    
    /**
     * This is simply an async {@link CacheLoader} for loading data from a {@link ConfigurationLoader}
     * @param configurationLoader the {@link ConfigurationLoader} to use when loading the Configuration
     */
    public ConfigurationCacheLoader(final ConfigurationLoader configurationLoader)
    {
        this.configurationLoader = configurationLoader;
        
        this.executor = Executors.newFixedThreadPool(1, new ThreadFactory() {
            
            @Override
            public Thread newThread(Runnable runnable)
            {
                Thread thread = new Thread(runnable);
                thread.setName(CachedConfigurator.class.getName() + " configuration data loader (" + configurationLoader.getClass().getSimpleName() + ")");
                thread.setDaemon(true);
                return thread;
            }
        });
        
    }
    @Override
    public Configuration load(String key) throws Exception
    {
        Future<Configuration> future = reload(key, null);
        return future.get();
    }
    
    @Override
    @GwtIncompatible("Futures")
    public ListenableFuture<Configuration> reload(String key, final Configuration oldValue) throws Exception
    {
        ListenableFutureTask<Configuration> task = ListenableFutureTask.create(new Callable<Configuration>() {
            public Configuration call() {
                Optional<Configuration> config = configurationLoader.get();
                
                return config.isPresent() ? config.get() : oldValue;
            }
          });
          executor.execute(task);
          return task;
    }
}