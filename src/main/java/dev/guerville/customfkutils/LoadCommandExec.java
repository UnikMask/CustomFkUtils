package dev.guerville.customfkutils;

import java.io.File;
import java.lang.reflect.*;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import fr.devsylone.fallenkingdom.Fk;
import fr.devsylone.fkpi.FkPI;

public class LoadCommandExec implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }
        String fp = args[0];
        File f = new File(CustomFkUtils.getInstance().getDataFolder(), fp);
        if (!f.exists()) {
            return false;
        }
        ConfigurationSection config = YamlConfiguration.loadConfiguration(f);
        if (config == null) {
            return false;
        }
        try {
            Object fk = Bukkit.getPluginManager().getPlugin("FallenKingdom");
            Object fkpi = fk.getClass().getMethod("getFkPI").invoke(fk);
            fkpi.getClass().getMethod("load", ConfigurationSection.class).invoke(fkpi, config.getConfigurationSection("FkPI"));
        } catch (InvocationTargetException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        }
        CustomFkUtils.getInstance().load(config);
        CustomFkUtils.getInstance().getLogger().log(Level.INFO, "FK instance loaded!");
        return true;
    }
}
