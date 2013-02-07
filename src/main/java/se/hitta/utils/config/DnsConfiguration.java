package se.hitta.utils.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Splitter;

public class DnsConfiguration implements Configuration
{
    private final Map<String, String> data = new HashMap<String, String>();
    
    DnsConfiguration(String text)
    {
        Iterable<String> kvPairIterable = Splitter.on(' ').trimResults().omitEmptyStrings().split(text);
        
        for(String kvPair : kvPairIterable)
        {
            Iterator<String> pair = Splitter.on(':').trimResults().split(kvPair).iterator();
            
            String key;
            String value;
            if(pair.hasNext())
            {
                key = pair.next();
                if(pair.hasNext())
                {
                    value = pair.next();
                    data.put(key, value);
                }
            }   
        }
    }
    
    @Override
    public Optional<String> get(String key)
    {
        return this.data.containsKey(key) ? Optional.of(this.data.get(key)) : Optional.<String>absent();
    }
}
