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

import com.google.common.base.Optional;

public interface Configuration
{

    public static final Configuration EMPTY = new Configuration() {
        @Override
        public Optional<String> get(String key)
        {
            return Optional.absent();
        }
    };

    public abstract Optional<String> get(String key);
}