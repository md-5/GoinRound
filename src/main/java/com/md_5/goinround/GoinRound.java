package com.md_5.goinround;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoinRound extends JavaPlugin {

    public static GoinRound instance;

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {
        Api.disable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ScanCommand.execute((Player) sender, args[0]);
        }
        return true;
    }
}
