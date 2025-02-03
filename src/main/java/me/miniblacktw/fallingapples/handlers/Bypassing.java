package me.miniblacktw.fallingapples.handlers;

import me.miniblacktw.fallingapples.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Bypassing {

    private final Main plugin;
    private final File file;
    private FileConfiguration config;
    private final Set<UUID> bypassedPlayers;

    public Bypassing(Main plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "bypassed.yml");
        this.bypassedPlayers = new HashSet<>();

        reloadBypassedPlayers();
    }

    public void reloadBypassedPlayers() {
        config = YamlConfiguration.loadConfiguration(file);
        bypassedPlayers.clear();

        if (config.contains("bypassed")) {
            for (String uuid : config.getStringList("bypassed")) {
                bypassedPlayers.add(UUID.fromString(uuid));
            }
        }
    }

    public void saveBypassedPlayers() {
        config.set("bypassed", bypassedPlayers.stream().map(UUID::toString).toList());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadBypassedPlayers();
    }

    public void addBypassedPlayer(Player player) {
        bypassedPlayers.add(player.getUniqueId());
        saveBypassedPlayers();
    }

    public void removeBypassedPlayer(Player player) {
        bypassedPlayers.remove(player.getUniqueId());
        saveBypassedPlayers();
    }

    public boolean isBypassed(Player player) {
        return bypassedPlayers.contains(player.getUniqueId());
    }
}