package dev.guerville.customfkutils;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import dev.guerville.customfkutils.utils.*;
import lombok.Getter;

public class HubChestLayout implements Saveable {
    @Getter
    private List<ItemStack> inventory = new ArrayList<>();
    @Getter
    private Integer time;

    /**
     * Build a new HubChestLayout object from a config. Returns null if the config is invalid.
     *
     * @param config {@link ConfigurationSection} where the object data is stored.
     * @return The hub chest with the config's data. Null if config incorrect.
     */
    public static HubChestLayout from(ConfigurationSection config) {
        HubChestLayout ret = new HubChestLayout();
        if (!ret.check(config)) {
            return null;
        }
        ret.load(config);
        return ret;
    }

    // -- Saveable -- //

    @Override
    public void save(ConfigurationSection config) {
        config.set("time", time);
        for (int i = 0; i < inventory.size(); i++) {
            config.set("inventory." + i, inventory.get(i));
        }
    }

    @Override
    public void load(ConfigurationSection config) {
        time = config.getInt("time");
        inventory = config.getConfigurationSection("inventory").getKeys(false).stream()
                .map(key -> config.getItemStack("inventory." + key)).toList();
    }

    @Override
    public boolean check(ConfigurationSection config) {
        if (!config.contains("time") || !config.isInt("time")) {
            return false;
        }
        if (!config.contains("inventory") || !config.isConfigurationSection("inventory")) {
            return false;
        }
        ConfigurationSection inventoryConfig = config.getConfigurationSection("inventory");
        for (String key : inventoryConfig.getKeys(false)) {
            if (!inventoryConfig.isItemStack(key)) {
                return false;
            }
        }
        return true;
    }

}
