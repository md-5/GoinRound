package com.md_5.goinround;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericCheckBox;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI {

    private GoinRound plugin;

    public GUI(GoinRound plugin) {
        this.plugin = plugin;
    }

    public static void open(Plugin plugin, SpoutPlayer splayer) {
        //ArrayList<Player> playerList = getPlayers((Player) splayer);
        ArrayList<String> playerList = new ArrayList<String>();
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");
        playerList.add("herp");
        playerList.add("derp");
        playerList.add("perp");


        // Container
        GenericContainer container = new GenericContainer();
        for (String p : playerList) {
            GenericCheckBox box = new GenericCheckBox(p);
            container.addChild(box);
        }
        container.setHeight(480);
        container.setWidth(854);
        container.setAnchor(WidgetAnchor.TOP_CENTER);
        container.setAuto(true);

        // Button
        GenericContainer buttonContainer = new GenericContainer();
        buttonContainer.setAnchor(WidgetAnchor.BOTTOM_CENTER);
        buttonContainer.setAuto(true);
        GenericButton button = new GenericButton("GO!");
        button.setWidth(50);
        button.setHeight(50);
        buttonContainer.addChild(button);

        // Popup
        GenericPopup popup = new GenericPopup();
        popup.attachWidget(plugin, container);
        //popup.attachWidget(plugin, button);
        splayer.getMainScreen().attachPopupScreen(popup);
    }

    public static ArrayList<Player> getPlayers(Player player) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (!p.hasPermission("goinround.ignore") && !p.equals(player)) {
                playerList.add(p);
            }
        }
        return playerList;
    }
}
