package com.kor3nt.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatCommands implements CommandExecutor, Listener {

    Boolean chatDisable = false;

    FileConfiguration config;
    ChatCommands(FileConfiguration config){
        this.config = config;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player admin = (Player) sender;
        String prefix = config.getString("prefix.default");

        if(command.getName().equalsIgnoreCase("chat")){
            if(args.length == 0){
                admin.sendMessage(ChatColor.BLUE + "ChatManager commands:");
                admin.sendMessage(ChatColor.AQUA + "/chat on");
                admin.sendMessage(ChatColor.AQUA + "/chat off");
                admin.sendMessage(ChatColor.AQUA + "/chat clear");
                admin.sendMessage(ChatColor.AQUA + "/ac " + ChatColor.WHITE + "- chat for admins");
                return true;
            }
            else if(args[0].equalsIgnoreCase("on")){
                if(admin.hasPermission("chat.on")){
                    if (chatDisable) {
                        Bukkit.broadcastMessage(format(prefix) + " " + format(config.getString("messages.setOnlineChat")));
                        chatDisable = false;
                        return true;
                    }
                    else {
                        admin.sendMessage(format(prefix) + " " + format(config.getString("messages.isOnlineChat")));
                        return true;
                    }
                }
                else{
                    admin.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("off")) {
                if(admin.hasPermission("chat.off")){
                    if(!chatDisable){
                        Bukkit.broadcastMessage(format(prefix) + " " + format(config.getString("messages.setOfflineChat")));
                        chatDisable = true;
                        return true;
                    }
                    else {
                        admin.sendMessage(format(prefix) + " " + format(config.getString("messages.isOfflineChat")));
                        return true;
                    }
                }
                else{
                    admin.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("clear")){
                if(admin.hasPermission("chat.clear")){
                    for(int i=0; i<150; i++){
                        Bukkit.broadcastMessage(" ");
                    }
                    Bukkit.broadcastMessage(format(config.getString("prefix.default")) + " " + format(config.getString("messages.clearChat")));
                    return true;
                }
                else{
                    admin.sendMessage(ChatColor.RED + "You don't have permission.");
                    return true;
                }
            }
            else{
                admin.sendMessage(ChatColor.RED + "Usage: /chat <on/off:/clear>");
                return true;
            }
        }



        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String prefix = config.getString("prefix.default");
        if(chatDisable){
            if(!(event.getPlayer().hasPermission("chat.write"))) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(format(prefix) + " " + format(config.getString("messages.setOfflineChat")));
            }
        }
    }

    private String format(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}