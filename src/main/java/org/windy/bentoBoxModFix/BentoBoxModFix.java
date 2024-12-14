package org.windy.iridiumskyblockforgetweaks;

import com.simibubi.create.AllTags.AllItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.mohistmc.api.event.BukkitHookForgeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class IridiumSkyblockForgeTweaks extends JavaPlugin implements Listener {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onEnable() {
        // 注册 Bukkit 和 Forge 事件监听器
        Bukkit.getPluginManager().registerEvents(this, this);
        // 注册 Forge 事件监听器
        net.minecraftforge.eventbus.api.IEventBus modEventBus = net.minecraftforge.common.MinecraftForge.EVENT_BUS;
        modEventBus.register(this);
        LOGGER.info("插件已启用，日志测试！！！！！！！！！！！！！！！！！！");
    }

    @Override
    public void onDisable() {
        // 在插件禁用时执行的代码（如果有）
    }

    // 处理 Bukkit 事件
    @EventHandler
    public void onPlayerInteractBukkit(PlayerInteractEvent event) {


        org.bukkit.entity.Player player = event.getPlayer();
        org.bukkit.inventory.ItemStack itemStack = event.getItem();

        if (player == null || itemStack == null || itemStack.getType() == Material.AIR) return;

        // 从配置文件获取 CREATE_WRENCH 材质名称
        String wrenchMaterialName = "CREATE_WRENCH";
        Material wrenchMaterial = Material.getMaterial(wrenchMaterialName);

        if (wrenchMaterial == null || !itemStack.getType().toString().contains(wrenchMaterial.name())) {
            return;
        }

        // 检查是否是 Create Mod 的扳手
        if (AllItemTags.WRENCH.matches(ItemStack.fromBukkitCopy(itemStack))) {
            LOGGER.info("监听到Bukkit扳手事件，准备取消");

            // 执行扳手操作（这里只需要取消事件）
            event.setCancelled(true);
            LOGGER.info("Bukkit扳手事件已取消");
        }
    }

    // Forge 事件监听示例
    @SubscribeEvent
    public void onForgeWrenchEvent(net.minecraftforge.event.entity.player.PlayerInteractEvent event) {
        LOGGER.info("Forge事件触发");

        // 处理该事件
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        if (event.isCanceled()) return;
        if (player == null) return;

        // 从配置文件获取 CREATE_WRENCH 材质名称
        String wrenchMaterialName = "CREATE_WRENCH";
        // 这里你需要使用 Forge 的 Material 获取方式
        // 但是 Forge 中并没有直接的 `Material` 类，通常要通过 Forge 自己的物品系统来处理

        if (itemStack == null || !itemStack.toString().contains(wrenchMaterialName)) {
            return;
        }

        // 使用 Create API 检查是否为扳手
        if (AllItemTags.WRENCH.matches(itemStack)) {
            LOGGER.info("监听到Forge扳手事件，准备取消");

            // 执行扳手操作（这里只需要取消事件）
            event.setCanceled(true);
            LOGGER.info("Forge扳手事件已取消");
        }
    }
}
