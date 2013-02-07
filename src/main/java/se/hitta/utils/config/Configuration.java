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