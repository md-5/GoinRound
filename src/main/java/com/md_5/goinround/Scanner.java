package com.md_5.goinround;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Scanner implements Runnable {

    private int interval;
    private int currentPosition;
    private ArrayList<Player> unseenPlayerList;
    private Player player;
    private Location playerLoc;
    private Player currentPlayer;
    private int taskId;
    private GoinRound plugin;

    public Scanner(Player playerArg, int intervalArg, ArrayList<Player> playerListArg) {
        player = playerArg;
        playerLoc = player.getLocation();
        interval = (intervalArg * 20);
        unseenPlayerList = playerListArg;
        currentPosition = 0;
        taskId = 0;
    }

    public void run() {
        if (!player.isOnline()) {
            Api.removeAndDisableScanner(this, player);
        }
        if (currentPosition < unseenPlayerList.size()) {
            currentPlayer = unseenPlayerList.get(currentPosition);
            if (currentPlayer.isOnline()) {
                player.teleport(currentPlayer);
                player.sendMessage(ChatColor.GREEN + "You are now at " + currentPlayer.getName());
            } else {
                player.teleport(currentPlayer);
                player.sendMessage(ChatColor.GREEN + "You are now at where " + currentPlayer.getName() + " was");
            }
            currentPosition++;
            return;
        } else {
            quit();
            return;
        }
    }

    public void quit() {
        player.teleport(playerLoc);
        player.sendMessage(ChatColor.GOLD + "Your journey has ended and you have been returned to your original location");
        Api.removeAndDisableScanner(this, player);
        unseenPlayerList.clear();
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getInterval() {
        return interval;
    }

    public void addToUnseenPlayers(Player p) {
        unseenPlayerList.add(p);
    }

    public void removeFromUnseenPlayers(Player p) {
        if (unseenPlayerList.contains(p)) {
            int index = unseenPlayerList.indexOf(p);
            if (index < currentPosition) {
                currentPosition++;
            }
        }
        unseenPlayerList.remove(p);
    }

    public boolean isInPlayerList(Player p) {
        return unseenPlayerList.contains(p);
    }

    public boolean hasYetToBeSeen(Player player) {
        if (unseenPlayerList.indexOf(player) >= currentPosition) {
            return true;
        }
        return false;
    }
}