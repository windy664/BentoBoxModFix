package org.windy.bentoBoxModFix;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;

import java.util.List;
import java.util.Objects;

public final class BentoBoxModFix extends JavaPlugin implements Listener {

    private List<String> enabledWorlds;
    private String cannotUseMessage;
    private String cannotUseItem;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();
        String version = this.getDescription().getVersion();
        String serverName = this.getServer().getName();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            this.getServer().getConsoleSender().sendMessage(Texts.logo);
            this.getServer().getConsoleSender().sendMessage("v"+"§a" + version + "运行环境：§e " + serverName + "\n");
        } else {
            getLogger().warning("你未安装前置PlaceholderAPI！插件没法启动！");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void loadConfigValues() {
        FileConfiguration config = getConfig();
        enabledWorlds = config.getStringList("enabledWorlds");
        cannotUseMessage = config.getString("messages.cannotUseRightClick");
        cannotUseItem = config.getString("messages.cannotUseItem");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        ItemStack heldItem = event.getItem();

        if (enabledWorlds.contains(worldName)) {
            if (event.getAction().name().contains("RIGHT_CLICK")) {
                if (player.isOp()) {
                    return;
                }
                // 使用PAPI检查占位符的值
                String canBuildPlaceholder = PlaceholderAPI.setPlaceholders(player, "%bskyblock_on_island%");
                boolean canBuild = Boolean.parseBoolean(canBuildPlaceholder);

                if (!canBuild) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                    event.setUseItemInHand(Event.Result.DENY);
                    event.setCancelled(true);
                    if (cannotUseMessage != null) {
                        player.sendMessage(cannotUseMessage);
                    }
                }
            }
        }
    }
    private boolean isItemDisabled(Material itemType) {
        FileConfiguration config = getConfig();
        return config.getStringList("disabledItems").contains(itemType.name());
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        String canBuildPlaceholder = PlaceholderAPI.setPlaceholders(player, "%bskyblock_on_island%");
        boolean canBuild = Boolean.parseBoolean(canBuildPlaceholder);
        Location location = player.getLocation();
        String worldName = Objects.requireNonNull(location.getWorld()).getName();

        if (enabledWorlds.contains(worldName)) {
            if (player.isOp()) {
                return;
            }

            if (newItem != null && isItemDisabled(newItem.getType()) && !canBuild) {
                event.setCancelled(true);
                if (cannotUseItem != null) {
                    player.sendMessage(cannotUseItem);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(Texts.logo);
        this.getServer().getConsoleSender().sendMessage("已卸载！\n");
        this.getServer().getConsoleSender().sendMessage("§6+--------------------------------------+");
    }
}