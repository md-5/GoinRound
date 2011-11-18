package com.md_5.goinround;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.keyboard.Keyboard;

public class GoinRound extends JavaPlugin {
    
    static final Logger logger = Bukkit.getServer().getLogger();
    public HashMap<Player, Scanner> journeys = new HashMap<Player, Scanner>();
    
    public void onEnable() {
        /*if (getServer().getPluginManager().getPlugin("Spout").isEnabled()) {
            Binding binding = new Binding(this);
            SpoutManager.getKeyBindingManager().registerBinding("ScanKey", Keyboard.KEY_G, "Brings up the scan GUI", binding, this);
        }*/
        logger.info(String.format("GoinRound v%1$s by md_5 enabled", this.getDescription().getVersion()));
    }
    
    public void onDisable() {
        Iterator<Scanner> i = journeys.values().iterator();
        while (i.hasNext()) {
            Scanner s = i.next();
            getServer().getScheduler().cancelTask(s.getTaskId());
            i.remove();
        }
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
        if (!player.hasPermission("goinround.scan")) {
            return false;
        }
        if (args.length == 1) {
            try {
                int time = Integer.parseInt(args[0]);
                scan(player, time);
                return true;
            } catch (NumberFormatException ex) {
                if (args[0].equalsIgnoreCase("stop")) {
                    if (journeys.containsKey(player)) {
                        player.sendMessage(ChatColor.RED + "GoinRound: Scan stopped at " + journeys.get(player).getCurrentPlayerName());
                        removeAndDisableScanner(journeys.get(player), player);
                    } else {
                        player.sendMessage(ChatColor.RED + "GoinRound: You are not on any journey");
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("return")) {
                    if (journeys.containsKey(player)) {
                        journeys.get(player).quit();
                    } else {
                        player.sendMessage(ChatColor.RED + "GoinRound: You are not on any journey");
                    }
                    return true;
                }
                player.sendMessage(ChatColor.RED + "GoinRound: " + args[0] + " is not a valid number!");
                return false;
            }
        }
        player.sendMessage(ChatColor.RED + "GoinRound: Invalid number of arguments");
        player.sendMessage(ChatColor.RED + "Usage: /gr <stop time>");
        return false;
    }
    
    private void scheduleTask(Scanner scanner) {
        scanner.setTaskId(getServer().getScheduler().scheduleSyncRepeatingTask(this, scanner, 0L, scanner.getInterval()));
    }
    
    public boolean onConsoleCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("GoinRound v" + this.getDescription().getVersion() + " by md_5");
        sender.sendMessage("GoinRound: No other console functionality is available at this time");
        return true;
    }
    
    public void scan(Player player, int time) {
        if (!journeys.containsKey(player)) {
            ArrayList<Player> playerList = new ArrayList<Player>();
            for (Player p : this.getServer().getOnlinePlayers()) {
                if (!p.hasPermission("goinround.ignore") && !p.equals(player)) {
                    playerList.add(p);
                }
            }
            Scanner handle = new Scanner(player, time, playerList, this);
            journeys.put(player, handle);
            scheduleTask(handle);
            player.sendMessage(ChatColor.GOLD + "Your journey has started. Stopping at each player for " + time + " seconds");
            player.sendMessage(ChatColor.GOLD + "Use /gr stop to stop at the current player and end your journey");
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are already on a journey. Use /gr stop to end it");
        }
    }
    
    public void scan(Player player, int time, ArrayList<Player> playerList) {
        if (!journeys.containsKey(player)) {
            Scanner handle = new Scanner(player, time, playerList, this);
            journeys.put(player, handle);
            scheduleTask(handle);
            player.sendMessage(ChatColor.GOLD + "Your journey has started. Stopping at each player for " + time + " seconds");
            player.sendMessage(ChatColor.GOLD + "Use /gr stop to stop at the current player and end your journey");
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are already on a journey. Use /gr stop to end it");
        }
    }
    
    public void removeAndDisableScanner(Scanner s, Player p) {
        getServer().getScheduler().cancelTask(s.getTaskId());
        journeys.remove(p);
    }
}
