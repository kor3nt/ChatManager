package com.kor3nt.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChatAdmins implements CommandExecutor {
    FileConfiguration config;

    public ChatAdmins(FileConfiguration config) {
        this.config = config;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player admin = (Player) sender;
        String prefix = format(config.getString("prefix.admin"));
        String reason =  admin.getName() + ": ";

        if(command.getName().equalsIgnoreCase("ac")){
            if(args.length>0){
                for(int i=0; i<args.length; i++){
                    reason = reason + args[i] + " ";
                }
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(p.hasPermission("chat.admin")){
                        p.sendMessage(prefix + " " + ChatColor.RESET + reason);
                    }
                }
                return true;
            }
            else{
                admin.sendMessage(ChatColor.RED + "Usage: /ac <message>");
                return true;
            }
        }

        return true;
    }

    private String format(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}