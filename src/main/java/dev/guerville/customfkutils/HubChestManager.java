package dev.guerville.customfkutils;

import org.bukkit.configuration.ConfigurationSection;
import dev.guerville.customfkutils.utils.Saveable;
import java.util.*;

public class HubChestManager implements Saveable {
    private Map<String, HubChest> chests = new HashMap<>();

    @Override
    public void save(ConfigurationSection config) {
        chests.forEach((key, chest) -> chest.save(config.createSection(key)));
    }

    @Override
    public void load(ConfigurationSection config) {
        chests = new HashMap<>();
        for (String key: config.getKeys(false)) {
            HubChest next = new HubChest();
            next.loadNullable(config.getConfigurationSection(key));
            chests.put(key, next);
        }
    }

    @Override
    public boolean check(ConfigurationSection config) {
        boolean ret = true;
        for(String key: config.getKeys(false)) {
            ret &= config.isConfigurationSection(key);
        }
        return ret;
    }
}
