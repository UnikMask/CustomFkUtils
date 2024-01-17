package dev.guerville.customfkutils;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import fr.devsylone.fallenkingdom.Fk;
import dev.guerville.customfkutils.utils.*;
import lombok.Getter;

public class CustomFkUtils extends JavaPlugin implements Listener, Saveable {
    @Getter
    private static CustomFkUtils instance;

    @Getter
    private HubChestManager hubChestManager = new HubChestManager();

    private Map<String, Saveable> saveables =
            Map.ofEntries(Map.entry("HubChests", hubChestManager));

    public CustomFkUtils() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // 1. Configuration loading
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        ConfigurationSection config = loadConfigDir(getDataFolder());
        if (config == null) {
            getLogger().log(Level.INFO,
                    "No default save file, loading FallenKingdom's plugin save.");
            config = loadConfigDir(Fk.getInstance().getDataFolder());
        }
        if (config != null) {
            loadNullable(config);
        }

        // TODO: 2. Load commands
    }

    // Load a given config from a config directory
    private ConfigurationSection loadConfigDir(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File f = new File(dir, Constants.DEFAULT_SAVE);
        if (!f.exists() || f.isDirectory()) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(f);
    }

    public void load(ConfigurationSection config) {
        for (Map.Entry<String, Saveable> entry : saveables.entrySet()) {
            entry.getValue().loadNullable(config.getConfigurationSection(entry.getKey()));
        }
    }

    public void save(ConfigurationSection config) {
        if (!config.contains("HubChests")) {

        }
    }

    public boolean check(ConfigurationSection config) {
        for (Map.Entry<String, Saveable> entry : saveables.entrySet()) {
            if (!config.contains(entry.getKey())) {
                return false;
            }
        }
        return true;
    }
}
