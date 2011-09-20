package com.md_5.goinround;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Scanner extends Thread {

    private int interval;
    private ArrayList<Player> playerList = null;
    private Player player;
    private Location playerLoc;

    public Scanner(Player playerArg, int intervalArg, ArrayList<Player> playerListArg) {
        player = playerArg;
        playerLoc = player.getLocation();
        interval = (intervalArg * 1000);
        playerList = playerListArg;
    }

    @Override
    public void run() {
        for (Player target: playerList) {
            player.teleport(target);
            if (target.isOnline()) {
                player.sendMessage(ChatColor.GREEN + "You are now at " + target.getName());
            } else {
                player.sendMessage(ChatColor.GREEN + "You are now where " + target.getName() + " was");
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                player.sendMessage(ChatColor.RED + "GoinRound: Scan stopped at " + target.getName());
                GoinRound.journeys.remove(player);
                return;
            }
        }
        player.teleport(playerLoc);
        player.sendMessage(ChatColor.GOLD + "Your journey has ended and you have been returned to your original location");
        GoinRound.journeys.remove(player);
    }
}
