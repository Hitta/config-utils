package se.hitta.utils.config;

import com.google.common.base.Optional;
import junit.framework.Assert;
import org.junit.Test;

public class DnsConfigurationLoaderTest {
    @Test
    public void run() {
        final DnsConfigurationLoader loader = new DnsConfigurationLoader("hit-config");

        final Optional<Configuration> configurationOptional = loader.get();

        Assert.assertFalse(configurationOptional.isPresent());
    }
}
