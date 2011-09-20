package com.md_5.goinround;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoinRound extends JavaPlugin {

    static final Logger logger = Bukkit.getServer().getLogger();
    public static HashMap<Player, Scanner> journeys = new HashMap<Player, Scanner>();

    public void onEnable() {
        logger.info(String.format("GoinRound v%1$s by md_5 enabled", this.getDescription().getVersion()));
    }

    public void onDisable() {
        logger.info(String.format("GoinRound v%1$s by md_5 disabled", this.getDescription().getVersion()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            return onPlayerCommand((Player) sender, command, label, args);
        } else {
            return onConsoleCommand(sender, command, label, args);
        }
    }

    public boolean onPlayerCommand(Player player, Command command, String label, String[] args) {
        if (args.length == 1) {
            try {
                int time = Integer.parseInt(args[0]);
                scan(player, time);
                return true;
            } catch (NumberFormatException ex) {
                if (args[0].equalsIgnoreCase("stop")) {
                    journeys.get(player).interrupt();
                    return true;
                }
                /*if (args[0].equalsIgnoreCase("continue")) {
                    return true;
                    //journeys.get(player).run();
                }*/
                player.sendMessage(ChatColor.RED + "GoinRound: " + args[0] + " is not a valid number!");
                return false;
            }
        }
        player.sendMessage(ChatColor.RED + "GoinRound: Invalid number of arguments");
        player.sendMessage(ChatColor.RED + "Usage: /gr <stop time>");
        return false;
    }

    public boolean onConsoleCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("GoinRound v" + this.getDescription().getVersion() + " by md_5");
        sender.sendMessage("GoinRound: No other console functionality is available at this time");
        /*Player player = this.getServer().getPlayer(args[0]);
        switch (args.length) {
        case 0:
        sender.sendMessage("GoinRound v" + this.getDescription().getVersion() + " by md_5");
        sender.sendMessage("Options: <player> <time> ");
        break;
        case 1:
        case 2:
        logger.info(args[0]);
        sender.sendMessage(player.getName().toString() + "Has been sent to patrol the server");
        }*/
        return true;
    }

    public void scan(Player player, int time) {
        if (!journeys.containsKey(player)) {
            ArrayList<Player> playerList = new ArrayList<Player>();
            for (Player p : this.getServer().getOnlinePlayers()) {
                if (!p.hasPermission("goinround.ignore") && p != player) {
                    playerList.add(p);
                }
            }
            Scanner handle = new Scanner(player, time, playerList);
            journeys.put(player, handle);
            journeys.get(player).start();
            player.sendMessage(ChatColor.GOLD + "Your journey has started. Stopping at each player for " + time + " seconds");
            player.sendMessage(ChatColor.GOLD + "Use /gr stop to stop at the current player and end your journey");
            //TODO /gr continue functionality
            //player.sendMessage(ChatColor.GOLD + "Use /gr back to go back one player and /gr forward to go forward one player");
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are already on a journey. Use /gr stop to end it");
        }
    }
}
