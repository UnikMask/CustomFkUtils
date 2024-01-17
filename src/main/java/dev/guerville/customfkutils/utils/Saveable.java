package dev.guerville.customfkutils.utils;

import org.bukkit.configuration.ConfigurationSection;

public interface Saveable {
    /**
     * Save object into the given config
     *
     * @param config {@link ConfigurationSection} in which to store object data.
     */
    public void save(ConfigurationSection config);

    /**
     * Load object from the given config.
     *
     * @param config {@link ConfigurationSection} in which object data is stored.
     */
    public void load(ConfigurationSection config);

    /**
     * Checks if the given config is valid for loading.
     *
     * @param config {@link ConfigurationSection} to check.
     */
    public boolean check(ConfigurationSection config);

    /**
     * Load object with support for invalid config.
     *
     * @param config {@link ConfigurationSection} in which object data is stored.
     */
    default void loadNullable(ConfigurationSection config) {
        if (config != null && check(config)) {
            load(config);
        }
    }
}
