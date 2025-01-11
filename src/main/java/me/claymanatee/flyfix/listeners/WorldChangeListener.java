package me.claymanatee.flyfix.listeners;

import me.claymanatee.flyfix.FlyFix;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.AbstractMap;

public class WorldChangeListener implements Listener {

    private final FlyFix plugin;

    public WorldChangeListener(FlyFix plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        final AbstractMap.SimpleEntry<Boolean, Boolean> entry = plugin.worldTeleports.remove(event.getPlayer().getUniqueId());
        if (entry != null && event.getPlayer().hasPermission("essentials.fly")) {
            event.getPlayer().setAllowFlight(entry.getKey());
            event.getPlayer().setFlying(entry.getValue());
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        if (event.getFrom().getWorld() != event.getTo().getWorld()) {
            Boolean anyFlight = event.getPlayer().isFlying() || event.getPlayer().getAllowFlight();
            Boolean flightPerm = event.getPlayer().hasPermission("essentials.fly");
            if (anyFlight && flightPerm) {
                plugin.worldTeleports.put(
                        event.getPlayer().getUniqueId(),
                        new AbstractMap.SimpleEntry<>(
                                event.getPlayer().getAllowFlight(),
                                event.getPlayer().isFlying()
                        )
                );
            }
        }
    }
}
