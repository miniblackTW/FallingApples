package me.miniblacktw.fallingapples.handlers;

import me.miniblacktw.fallingapples.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppleEvents implements Listener {

    private final Main plugin;
    private final Random random = new Random();
    private final Set<Item> fallingApples = new HashSet<>();

    public AppleEvents(Main plugin) {
        this.plugin = plugin;
    }

    public void startAppleFallingCheck(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || plugin.getBypassManager().isBypassed(player)) {
                    cancel();
                    return;
                }

                World world = player.getWorld();
                Location playerLocation = player.getLocation();
                int px = playerLocation.getBlockX();
                int py = playerLocation.getBlockY();
                int pz = playerLocation.getBlockZ();

                for (int y = py + 1; y <= py + 10; y++) {
                    Location checkLocation = new Location(world, px, y, pz);
                    if (checkLocation.getBlock().getType() == Material.OAK_LEAVES) {
                        spawnFallingApple(checkLocation);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }

    @EventHandler
    public void onAppleEat(PlayerItemConsumeEvent event) {
        ItemStack apple = event.getItem();

        if (isFallingApple(apple)) {
            Player player = event.getPlayer();

            if (random.nextDouble() < 0.75) {
                player.sendMessage("§7Too bad, you ate a §cBad Apple");
                player.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 200, 1)); // 挖掘疲勞 (10 秒)
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 200, 1)); // 反胃 (10 秒)
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 200, 1)); // 緩慢 (10 秒)
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 1)); // 失明 (10 秒)
                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1)); // 虛弱 (10 秒)
            }
        }
    }

    private void spawnFallingApple(Location leafLocation) {
        World world = leafLocation.getWorld();
        Location appleLocation = leafLocation.clone().add(0, -0.75, 0);

        ItemStack apple = new ItemStack(Material.APPLE);
        ItemMeta meta = apple.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Falling Apple");
            List<String> lore = new ArrayList<>();
            lore.add("§8§k|| §bThat's Gravity §8§k||");
            meta.setLore(lore);
            apple.setItemMeta(meta);
        }

        Item fallingApple = world.dropItem(appleLocation, apple);
        fallingApple.setPickupDelay(30);
        fallingApples.add(fallingApple);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            fallingApples.remove(fallingApple);
            fallingApple.remove();
        }, 50L);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        for (Item apple : fallingApples) {
            if (apple.getWorld().equals(player.getWorld()) && apple.getLocation().distanceSquared(player.getLocation()) < 1) {
                player.damage(1.0);
                apple.remove();
                fallingApples.remove(apple);
                break;
            }
        }
    }

    private boolean isFallingApple(ItemStack item) {
        if (item.getType() == Material.APPLE && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return "§6Falling Apple".equals(item.getItemMeta().getDisplayName());
        }
        return false;
    }
}