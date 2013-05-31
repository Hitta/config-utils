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
    private final String name;
    
    /**
     * This loader will query the given DNS name for a TXT-record and use the value returned to build a {@link DnsConfiguration}
     * It expects the TXT-record to contain comma separated key/value pairs separated by one or more white spaces e.g.:<br/>
     * "key1:value1 key2:value2 key3:value3"
     * @param name the DNS name to query 
     */
    public DnsConfigurationLoader(String name)
    {
        this.name = name;
        
    }
    
    @Override
    public Optional<Configuration> get()
    {
        try
        {
            Record[] records = new Lookup(this.name, Type.TXT).run();
            
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
            log.warn("Failed to load DNS TXT record from source: " + this.name, e);
        }
        
        return Optional.absent();
    }
}
