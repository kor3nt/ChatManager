package com.kor3nt.chatmanager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatFilter implements Listener {

    FileConfiguration config;
    public ChatFilter(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(config.getInt("MessageFilter") == 1){
            ArrayList<String> words = (ArrayList<String>) config.getStringList("disableMessage");
            String message = event.getMessage();
            String prefix = config.getString("prefix.default");
            for(String i : words){
                if(message.contains(i)){
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(format(prefix) + " " + format(config.getString("MessageShowFilter")));
                }
            }
        }
    }

    private String format(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
