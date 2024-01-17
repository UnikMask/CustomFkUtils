package dev.guerville.customfkutils;

import org.bukkit.configuration.ConfigurationSection;
import dev.guerville.customfkutils.utils.Saveable;
import java.util.*;

public class HubChestManager implements Saveable {
    private HashMap<String, HubChest> chests = new HashMap<>();

    public void save(ConfigurationSection config) {

    }

    public void load(ConfigurationSection config) {

    }

    public boolean check(ConfigurationSection config) {
        return false;
    }
}
