package me.claymanatee.flyfix;

import me.claymanatee.flyfix.commands.CheckCommand;
import me.claymanatee.flyfix.commands.PurgeCommand;
import me.claymanatee.flyfix.listeners.WorldChangeListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class FlyFix extends JavaPlugin {

    public final ChatColor mainColor = ChatColor.WHITE;
    public final ChatColor accentColor = ChatColor.GREEN;
    public final Map<UUID, AbstractMap.SimpleEntry<Boolean, Boolean>> worldTeleports = new HashMap<>();

    @Override
    public void onEnable() {
        String pluginPrefix = "[FlyFix] ";
        System.out.println(pluginPrefix + "started successfully.");
        System.out.println(pluginPrefix + "data folder: " + this.getDataFolder ());

        getCommand("flyfixcheck").setExecutor(new CheckCommand(this));
        getCommand("flyfixpurge").setExecutor(new PurgeCommand(this));

        getServer().getPluginManager().registerEvents(new WorldChangeListener(this), this);
    }
}
