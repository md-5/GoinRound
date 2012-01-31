package com.md_5.goinround;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Api {

    public static HashMap<Player, Scanner> journeys = new HashMap<Player, Scanner>();

    public static void scan(Player player, int time) {
        if (!journeys.containsKey(player)) {
            ArrayList<Player> playerList = new ArrayList<Player>();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!p.hasPermission("goinround.ignore") && !p.equals(player)) {
                    playerList.add(p);
                }
            }
            Scanner handle = new Scanner(player, time, playerList);
            journeys.put(player, handle);
            scheduleTask(handle);
            player.sendMessage(ChatColor.GOLD + "Your journey has started. Stopping at each player for " + time + " seconds");
            player.sendMessage(ChatColor.GOLD + "Use /gr stop to stop at the current player and end your journey");
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are already on a journey. Use /gr stop to end it");
        }
    }

    public static void scan(Player player, int time, ArrayList<Player> playerList) {
        if (!journeys.containsKey(player)) {
            Scanner handle = new Scanner(player, time, playerList);
            journeys.put(player, handle);
            scheduleTask(handle);
            player.sendMessage(ChatColor.GOLD + "Your journey has started. Stopping at each player for " + time + " seconds");
            player.sendMessage(ChatColor.GOLD + "Use /gr stop to stop at the current player and end your journey");
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are already on a journey. Use /gr stop to end it");
        }
    }

    public static void removeAndDisableScanner(Scanner s, Player p) {
        Bukkit.getServer().getScheduler().cancelTask(s.getTaskId());
        journeys.remove(p);
    }

    private static void scheduleTask(Scanner scanner) {
        int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(GoinRound.instance, scanner, 0L, scanner.getInterval());
        scanner.setTaskId(id);
    }

    public static void disable() {
        Iterator<Scanner> i = journeys.values().iterator();
        while (i.hasNext()) {
            Scanner s = i.next();
            Bukkit.getServer().getScheduler().cancelTask(s.getTaskId());
            i.remove();
        }
    }
}
