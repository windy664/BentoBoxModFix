<div align="center">

<img src="assets/banner.svg" alt="BentoBoxModFix" width="100%">

# BentoBoxModFix

**BentoBox 空岛世界 Mod 物品保护** —— 在 BentoBox 空岛世界里拦截对他人岛屿使用模组物品（扳手等）的行为。

![Platform](https://img.shields.io/badge/Platform-Spigot%20%2F%20Paper-62bda8?style=flat-square&logo=minecraft&logoColor=white)
![Minecraft](https://img.shields.io/badge/MC-1.20.x-8a9a5b?style=flat-square)
![Java](https://img.shields.io/badge/Java-17-f89820?style=flat-square&logo=openjdk&logoColor=white)
![Depends](https://img.shields.io/badge/softdepend-PlaceholderAPI-e2b90f?style=flat-square)

[功能](#-功能) · [配置](#️-配置)

</div>

## ✨ 功能

- **岛屿边界保护**：只在 `enabledWorlds` 列出的 BentoBox 空岛世界生效，非空岛世界零干扰
- **Mod 物品拦截**：玩家在非自己的岛屿上对 `disabledItems` 中的物品执行右键操作时取消事件
- **自定义提示**：拦截时发送可配置的提示消息（支持颜色代码）

> 同系列插件：[ResidenceModFix](https://github.com/windy664/ResidenceModFix) · [HusktownsPrevent](https://github.com/windy664/HusktownsPrevent) · [IridiumSkyblockModTweaks](https://github.com/windy664/IridiumSkyblockModTweaks) · [PlotsquaredModFix](https://github.com/windy664/PlotsquaredModFix)

## ⚙️ 配置

```yaml
enabledWorlds:
  - bskyblock_world
  - bskyblock_world_nether
  - bskyblock_world_the_end

disabledItems:
  - CREATE_WRENCH

messages:
  cannotUseRightClick: "这岛屿不属于你!"
  cannotUseItem: "你不能在这里使用这个东西！"
```

## 🔨 构建

```bash
./gradlew build
```
