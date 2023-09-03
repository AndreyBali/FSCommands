package net.forscore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    private static Main Instance;
    @Override
    public void onEnable() {
        Instance = this;
        new FScommand(this);
        Bukkit.getPluginManager().registerEvents(new Listener(), this);

        getLogger().info("Plugin successfully started up!");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info("Plugin successfully disabled!");
    }

    public static Main getInstance() {
        return Instance;
    }
}
