package me.miniblacktw.fallingapples.listeners;

import me.miniblacktw.fallingapples.handlers.AppleEvents;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class Join implements Listener {

    private final AppleEvents appleManager;

    public Join(AppleEvents appleManager) {
        this.appleManager = appleManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        appleManager.startAppleFallingCheck(player);
    }
}