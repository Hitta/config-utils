package se.hitta.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.TXTRecord;
import org.xbill.DNS.Type;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;

public class DnsConfigurationLoader implements ConfigurationLoader
{
    private final Logger log = LoggerFactory.getLogger(DnsConfigurationLoader.class);
    private final String record;
    
    public DnsConfigurationLoader(String record)
    {
        this.record = record;
        
    }
    
    @Override
    public Optional<Configuration> get()
    {
        try
        {
            Record[] records = new Lookup(this.record, Type.TXT).run();
            
            if (records.length > 0 && records[0] instanceof TXTRecord)
            {
                TXTRecord record = (TXTRecord) records[0];
                return Optional.of((Configuration)new DnsConfiguration(Joiner.on("").join(record.getStrings().iterator())));
            }
            else
            {
                return Optional.of(Configuration.EMPTY);
            }
        } catch (Exception e)
        {
            log.warn("Failed to load DNS TXT record from source: " + this.record, e);
        }
        
        return Optional.absent();
    }
}
