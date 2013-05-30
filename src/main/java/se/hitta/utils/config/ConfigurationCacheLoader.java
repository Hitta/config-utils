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