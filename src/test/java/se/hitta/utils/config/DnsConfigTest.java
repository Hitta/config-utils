package se.hitta.utils.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.base.Optional;

public class DnsConfigTest
{
    @Test
    public void canGetConfigValue() throws InterruptedException
    {
        Configuration config = mock(Configuration.class);
        ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
        when(configurationLoader.get()).thenReturn(Optional.of(config));
        when(config.get(anyString())).then(new Answer<Optional<String>>() {

            @Override
            public Optional<String> answer(InvocationOnMock invocation) throws Throwable
            {
                Object key = invocation.getArguments()[0];
                return Optional.of(key + "-test");
            }
        });

        ConfigurationCacheLoader cacheLoader = new ConfigurationCacheLoader(configurationLoader);

        CachedConfigurator configurator = new CachedConfigurator(2, TimeUnit.SECONDS, cacheLoader);

        assertEquals("site-test", configurator.get("site").get());
    }
}
