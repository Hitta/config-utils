package se.hitta.utils.config;

import com.google.common.base.Optional;

public interface ConfigurationLoader
{
    Optional<Configuration> get();
}