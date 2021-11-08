package com.kor3nt.chatmanager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatManager extends JavaPlugin{
    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        config.options().copyDefaults(true);
        saveConfig();

        ChatCommands Chat = new ChatCommands(config);
        getCommand("chat").setExecutor(Chat);
        getServer().getPluginManager().registerEvents(Chat, this);
        getCommand("ac").setExecutor(new ChatAdmins(config));

        ChatFilter Filter = new ChatFilter(config);
        getServer().getPluginManager().registerEvents(Filter, this);
    }
}
