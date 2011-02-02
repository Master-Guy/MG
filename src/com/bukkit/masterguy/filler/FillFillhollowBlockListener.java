package com.bukkit.masterguy.filler;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

/**
 * Filler block listener
 * @author Master-Guy
 */

public class FillFillhollowBlockListener extends BlockListener {
    private final FillFillhollow plugin;
    private int I;
    private boolean allowPlayer;
	private HashMap<String, Integer> stickMap;
	private final Settings Settings = new Settings();
	
    public FillFillhollowBlockListener(final FillFillhollow plugin) {
        this.plugin = plugin;
        log("block loaded");
    }
    
    public void onBlockRightClick(BlockRightClickEvent event) {
    	allowPlayer = false;
    	I = 0;
    	while (I < Settings.getSetting("settings/FillFillhollow.ini", "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",").length) {
    		if(event.getPlayer().getName().equalsIgnoreCase(Settings.getSetting("settings/FillFillhollow.ini", "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",")[I])) {
    			allowPlayer = true;
    		}
    		I = I + 1;
    	}
    	if(Settings.getSetting("settings/FillFillhollow.ini", "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",")[0].equalsIgnoreCase("*")) {
    		allowPlayer = true;
    	}
	    if(event.getItemInHand().getTypeId() == Integer.parseInt(Settings.getSetting("settings/FillFillhollow.ini", "toolID", "280")[0])) {
	    	if(allowPlayer) {
	            stickMap = this.plugin.stickMap;
				if(!stickMap.containsKey(event.getPlayer().getName()+"1X") || stickMap.containsKey(event.getPlayer().getName()+"2X")) {
					stickMap.put(event.getPlayer().getName()+"1X", event.getBlockAgainst().getX());
					stickMap.put(event.getPlayer().getName()+"1Y", event.getBlockAgainst().getY());
					stickMap.put(event.getPlayer().getName()+"1Z", event.getBlockAgainst().getZ());
					stickMap.remove(event.getPlayer().getName()+"2X");
					stickMap.remove(event.getPlayer().getName()+"2Y");
					stickMap.remove(event.getPlayer().getName()+"2Z");
					event.getPlayer().sendMessage(ChatColor.DARK_RED+"Position 1 set at "+event.getBlockAgainst().getX()+"/"+event.getBlockAgainst().getY()+"/"+event.getBlockAgainst().getZ());
				} else {
					stickMap.put(event.getPlayer().getName()+"2X", event.getBlockAgainst().getX());
					stickMap.put(event.getPlayer().getName()+"2Y", event.getBlockAgainst().getY());
					stickMap.put(event.getPlayer().getName()+"2Z", event.getBlockAgainst().getZ());
					event.getPlayer().sendMessage(ChatColor.GREEN+"Position 2 set at "+event.getBlockAgainst().getX()+"/"+event.getBlockAgainst().getY()+"/"+event.getBlockAgainst().getZ());
				}
	    	} else {
	    		event.getPlayer().sendMessage("You are not allowed to use this!");
	    	}
    	}
    	return;
    }

    public void log(String logText) {
    	System.out.println(logText);
    }
    
    public void logToAll(String logText) {
    	System.out.println(logText);
    	I = 0;
    	while (I < this.plugin.getServer().getOnlinePlayers().length) {
    		this.plugin.getServer().getOnlinePlayers()[I].sendMessage(logText);
    		I = I + 1;
    	}
    }
}
