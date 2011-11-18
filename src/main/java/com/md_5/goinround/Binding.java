package com.md_5.goinround;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.gui.ScreenType;

public class Binding implements BindingExecutionDelegate {

    private GoinRound plugin;

    public Binding(GoinRound plugin) {
        this.plugin = plugin;
    }

    public void keyPressed(KeyBindingEvent event) {
        if (event.getPlayer().getActiveScreen() != ScreenType.GAME_SCREEN) {
            return;
        }
        if (event.getBinding().getId().equals("ScanKey")) {
            if (!event.getPlayer().hasPermission("goinround.scan")){
                return;
            }
            GUI.open(plugin, event.getPlayer());
        }
    }

    public void keyReleased(KeyBindingEvent arg0) {
    }
}
