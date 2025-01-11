package me.claymanatee.flyfix.commands;

import me.claymanatee.flyfix.FlyFix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class PurgeCommand implements CommandExecutor {
    private final FlyFix plugin;

    public PurgeCommand(FlyFix plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            plugin.worldTeleports.clear();
            sender.sendMessage("FlyFix: Purged teleport information.");
            return true;
        }

        else if (sender instanceof Player player) {

            if (!player.hasPermission("flyfix.flyfixall")) {
                sender.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            plugin.worldTeleports.clear();
            player.sendMessage(plugin.accentColor + "FlyFix: " + plugin.mainColor + "Purged teleport information.");
        }

        return true;
    }
}
