package se.hitta.utils.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class DnsConfigurationTest
{
    @Test
    public void canParseTxt()
    {
        String input = "key1:value1 key2:value2    key3:value3";
        
        Configuration config = new DnsConfiguration(input);
        
        assertEquals("value1", config.get("key1").get());
        assertEquals("value2", config.get("key2").get());
        assertEquals("value3", config.get("key3").get());
    }
}
