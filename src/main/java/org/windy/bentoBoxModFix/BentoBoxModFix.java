package org.windy.iridiumskyblockforgetweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

    public final class IridiumSkyblockForgeTweaks extends JavaPlugin implements Listener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onEnable() {
        // 注册事件监听器
        //getServer().getPluginManager().registerEvents(new ForgeEventListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        LOGGER.info("日志测试！！！！！！！！！！！！！！！！！！");
    }

    @Override
    public void onDisable() {
        // 在插件禁用时执行的代码（如果有）
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onWrenchEvent(PlayerInteractEvent.RightClickBlock event) {
        LOGGER.info("扳手事件前触发");
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        // 基本检查：防止事件被取消
        if (event.isCanceled()) return;
        if (event.getLevel() == null) return;
        if (player == null || !player.mayBuild()) return;
        if (itemStack.isEmpty()) return;

        // 从配置文件获取 CREATE_WRENCH 材质名称
        String wrenchMaterialName = "CREATE_WRENCH";
        Material wrenchMaterial = Material.getMaterial(wrenchMaterialName);

        if (wrenchMaterial == null || !itemStack.getItem().toString().contains(wrenchMaterial.name())) {
            return; // 如果没有持有 CREATE_WRENCH，直接返回
        }

        // 检查该物品是否为扳手
        if (AllItemTags.WRENCH.matches(itemStack.getItem())) {
            LOGGER.info("监听到Forge扳手事件，准备取消");

            // 获取方块状态和扳手操作
            BlockState blockState = event.getLevel().getBlockState(event.getPos());
            Block block = blockState.getBlock();

            if (!(block instanceof IWrenchable)) return;

            // 执行扳手操作（这里不需要具体执行，只需要取消事件）
            event.setCanceled(true); // 取消事件，防止继续执行
            LOGGER.info("Forge扳手事件已取消");
        }
    }
}
