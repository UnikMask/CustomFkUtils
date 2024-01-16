package dev.guerville.customfkutils;

import net.kyori.adventure.text.Component;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

public class CustomFkUtils extends JavaPlugin implements Listener {
    @Getter
    private static CustomFkUtils instance;

    public CustomFkUtils() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().log(Level.INFO, "Plugin enabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component.text("CustomFkUtils speaking, " + event.getPlayer().getName() + "!"));
    }
}
