package com.md_5.goinround;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScanCommand {

    public static void execute(Player player, String[] args) {
        if (args[0].isEmpty()) {
            scan(player, args[1]);
        }
        if (args[0].equalsIgnoreCase("stop")) {
            stop(player);
        }
        if (args[0].equalsIgnoreCase("return")) {
            abort(player);
        }
    }

    private static void scan(Player player, String args) {
        try {
            int time = Integer.parseInt(args);
            Api.scan(player, time);
        } catch (NumberFormatException ex) {
            player.sendMessage(ChatColor.RED + "GoinRound: " + args + " is not a valid number");
        }
    }

    private static void stop(Player player) {
        if (Api.journeys.containsKey(player)) {
            player.sendMessage(ChatColor.RED + "GoinRound: Scan stopped at " + Api.journeys.get(player).getCurrentPlayerName());
            Api.removeAndDisableScanner(Api.journeys.get(player), player);
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are not on any journey");
        }
    }

    private static void abort(Player player) {
        if (Api.journeys.containsKey(player)) {
            Api.journeys.get(player).quit();
        } else {
            player.sendMessage(ChatColor.RED + "GoinRound: You are not on any journey");
        }
    }
}
