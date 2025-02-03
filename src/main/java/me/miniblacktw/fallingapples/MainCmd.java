package me.miniblacktw.fallingapples;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCmd implements CommandExecutor {

    private final Main plugin;

    public MainCmd(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("fallingapples.cmd")) {
            sender.sendMessage("§cYou don't have permission");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUsage: /fallingapples < bypass | unbypass > <player>");
            return true;
        }

        String action = args[0].toLowerCase();
        String playerName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            sender.sendMessage("§cPlayer not found or not online");
            return true;
        }

        if (action.equals("bypass")) {
            plugin.getBypassManager().addBypassedPlayer(target);
            sender.sendMessage("§b" + target.getName() + " §ais now bypassed from Falling Apples");
        } else if (action.equals("unbypass")) {
            if (plugin.getBypassManager().isBypassed(target)) {
                plugin.getBypassManager().removeBypassedPlayer(target);
                sender.sendMessage("§b" + target.getName() + " §ahas been removed from the bypass list");
                plugin.getAppleManager().startAppleFallingCheck(target);
            } else {
                sender.sendMessage("§b" + target.getName() + " §6is not bypassed");
            }
        } else {
            sender.sendMessage("§cUsage: /fallingapples < bypass | unbypass > <player>");
        }

        return true;
    }
}