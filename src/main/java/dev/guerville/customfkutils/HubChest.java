package dev.guerville.customfkutils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import dev.guerville.customfkutils.utils.Saveable;
import lombok.Getter;

public class HubChest implements Saveable {
    @Getter
    private Location loc;

    @Getter
    private Map<Integer, HubChestLayout> layouts;

    // -- Saveable -- //

    public void save(ConfigurationSection config) {
        // Set HubChest info
        config.createSection("Loc");
        config.set("Loc.World", loc.getWorld().getName());
        config.set("Loc.x", loc.getBlockX());
        config.set("Loc.y", loc.getBlockY());
        config.set("Loc.z", loc.getBlockZ());

        // Set HubChest layouts
        config.createSection("days");
        layouts.forEach((day, layout) -> {
            layout.save(config.createSection("days." + day));
        });
    }

    @Override
    public void load(ConfigurationSection config) {
        // Load Hub Chest info
        loc = new Location(Bukkit.getWorld(config.getString("Loc.World")), config.getInt("Loc.x"),
                config.getInt("Loc.y"), config.getInt("Loc.z"));

        // Load hub chests
        layouts = new HashMap<>();
        for (String key : config.getConfigurationSection("days").getKeys(false)) {
            layouts.put(Integer.parseInt(key),
                    HubChestLayout.from(config.getConfigurationSection("days." + key)));
        }
    }

    @Override
    public boolean check(ConfigurationSection config) {
        boolean ret = config.isConfigurationSection("Loc") && config.isInt("Loc.x")
                && config.isInt("Loc.y") && config.isInt("Loc.z") && config.isString("Loc.World")
                && config.isConfigurationSection("days");
        if (!ret) {
            return false;
        }
        for (String k: config.getConfigurationSection("days").getKeys(false)) {
            ret &= StringUtils.isNumeric(k) && config.isConfigurationSection("days." + k);
        }
        return ret;
    }

}
