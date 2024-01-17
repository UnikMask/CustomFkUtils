package dev.guerville.customfkutils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
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
            getLogger().log(Level.INFO, "No plugin directory found! Creating it...");
            getDataFolder().mkdir();
        }
        ConfigurationSection config = loadConfigDir(getDataFolder());
        if (config != null) {
            loadNullable(config);
        } else {
            getLogger().log(Level.INFO, "No configuration found, starting empty.");
        }
    }

    @Override
    public void onDisable() {
        // Save file to config
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            public void run() {
                YamlConfiguration config = new YamlConfiguration();
                save(config);
                File f = new File(getDataFolder(), Constants.DEFAULT_SAVE);
                try {
                    config.save(f);
                } catch (IOException e) {
                    getLogger().log(Level.SEVERE, "Error occured saving to " + f.getName() + ": ", e);
                }
                getLogger().log(Level.INFO, "Config saved to " + f.getName());
            }
        });
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


    // -- Saveable -- //


    @Override
    public void load(ConfigurationSection config) {
        for (Map.Entry<String, Saveable> entry : saveables.entrySet()) {
            entry.getValue().loadNullable(config.getConfigurationSection(entry.getKey()));
        }
    }

    @Override
    public void save(ConfigurationSection config) {
        saveables.entrySet()
                .forEach(entry -> entry.getValue().save(config.createSection(entry.getKey())));
    }

    @Override
    public boolean check(ConfigurationSection config) {
        for (Map.Entry<String, Saveable> entry : saveables.entrySet()) {
            if (!config.contains(entry.getKey())) {
                return false;
            }
        }
        return true;
    }
}
