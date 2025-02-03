package me.miniblacktw.fallingapples;

import me.miniblacktw.fallingapples.handlers.AppleEvents;
import me.miniblacktw.fallingapples.handlers.Bypassing;
import me.miniblacktw.fallingapples.listeners.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;
    private Bypassing bypassManager;
    private AppleEvents appleManager;

    @Override
    public void onEnable() {
        instance = this;
        bypassManager = new Bypassing(this);
        appleManager = new AppleEvents(this);

        Bukkit.getPluginManager().registerEvents(appleManager, this);
        Bukkit.getPluginManager().registerEvents(new Join(appleManager), this);
        getCommand("fallingapples").setExecutor(new MainCmd(this));
        getLogger().info("Apples will fall from now on.");
    }

    @Override
    public void onDisable() {
        bypassManager.saveBypassedPlayers();
        getLogger().info("Apples are no longer falling.");
    }

    public AppleEvents getAppleManager() {
        return appleManager;
    }

    public Bypassing getBypassManager() {
        return bypassManager;
    }

}