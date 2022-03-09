package com.semivanilla.announcer;

import com.semivanilla.announcer.listener.MainListener;
import com.semivanilla.announcer.manager.ConfigManager;
import com.semivanilla.announcer.object.TitleUpdateRunnable;
import lombok.Getter;
import net.badbird5907.blib.bLib;
import net.badbird5907.blib.util.Tasks;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class Announcer extends JavaPlugin {
    @Getter
    private static Announcer instance;
    @Getter
    private static ConfigManager configManager;
    private FileConfiguration config = null;

    @Getter
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        bLib.create(this);
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        configManager = new ConfigManager();
        configManager.init();
        Bukkit.getLogger().info("Loaded (" + ConfigManager.getMessages().size() + ") messages!");
        Bukkit.getPluginManager().registerEvents(new MainListener(), this);
        Tasks.runAsyncTimer(() -> {
            String s = ConfigManager.getNextMessage();
            if (s != null) {
                Component component = miniMessage.deserialize(s.trim());
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(component));
            }
        }, ConfigManager.getTicks(), ConfigManager.getTicks());
        new TitleUpdateRunnable().runTaskTimer(this, 10, 1);
    }

    @Override
    public @NotNull FileConfiguration getConfig() {
        if (config == null) {
            config = YamlConfiguration.loadConfiguration(new File(getDataFolder() + "/config.yml"));
        }
        return config;
    }
}
