package me.claymanatee.flyfix.commands;

import me.claymanatee.flyfix.FlyFix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.AbstractMap;
import java.util.Map;

public class CheckCommand implements CommandExecutor {
    private final FlyFix plugin;

    public CheckCommand(FlyFix plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(worldTeleportToString(true));
            return true;
        }

        else if (sender instanceof Player player) {

            if (!player.hasPermission("flyfix.flyfixall")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            player.sendMessage(worldTeleportToString(false));
            return true;
        }

        return true;
    }

    public String worldTeleportToString(Boolean forConsole)
    {
        if (plugin.worldTeleports.isEmpty ()) {
            String emptyTeleports;
            if (forConsole) {
                emptyTeleports = "FlyFix: No pending teleport information.";
            }
            else {
                emptyTeleports = plugin.accentColor + "FlyFix: " + plugin.mainColor + "No pending teleport information.";
            }
            return emptyTeleports;
        }

        String listHeader;
        if (forConsole) {
            listHeader = "> [FlyFix Teleports List] <";
        }
        else {
            listHeader = plugin.mainColor + "> [" + plugin.accentColor + "FlyFix Teleports List" + plugin.mainColor + "] <";
        }

        StringBuilder wtList = new StringBuilder(listHeader);
        for (Map.Entry<UUID, AbstractMap.SimpleEntry<Boolean, Boolean>> entry : plugin.worldTeleports.entrySet()) {
            String entryName = Bukkit.getPlayer(entry.getKey()).getName();
            String entryAllowFlight = entry.getValue().getKey() ? "fly enabled" : "fly not enabled";
            String entryIsFlying = entry.getValue().getKey() ? "is flying" : "is not flying";

            String entryInfo;
            if (forConsole) {
                entryInfo = "\n" + entryName + ": " + entryAllowFlight + ", " + entryIsFlying;
            }
            else {
                entryInfo = "\n" + plugin.accentColor + entryName + ": " + plugin.mainColor + entryAllowFlight + ", " + entryIsFlying;
            }
            wtList.append(entryInfo);
        }
        return wtList.toString();
    }
}
