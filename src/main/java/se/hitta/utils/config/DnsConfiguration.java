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
