package com.bukkit.MasterGuy;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

/**
 * Filler block listener
 * @author Master-Guy
 */

public class FillerBlockListener extends BlockListener {
    private final Filler plugin;
    private int I;
    private boolean allowPlayer;
	private HashMap<String, Integer> stickMap;
	private final Settings Settings = new Settings();
	
    public FillerBlockListener(final Filler plugin) {
        this.plugin = plugin;
        Settings.testFolderExists(plugin.iniPath);
    }
    
    public void onBlockRightClick(BlockRightClickEvent event) {
    	allowPlayer = false;
    	I = 0;
    	while (I < Settings.getSetting(plugin.iniFile, "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",").length) {
    		if(event.getPlayer().getName().equalsIgnoreCase(Settings.getSetting(plugin.iniFile, "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",")[I])) {
    			allowPlayer = true;
    		}
    		I = I + 1;
    	}
    	if(Settings.getSetting(plugin.iniFile, "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",")[0].equalsIgnoreCase("*")) {
    		allowPlayer = true;
    	}
	    if(event.getItemInHand().getTypeId() == Integer.parseInt(Settings.getSetting(plugin.iniFile, "toolID", "280")[0])) {
	    	if(allowPlayer) {
	            stickMap = this.plugin.stickMap;
				if(!stickMap.containsKey(event.getPlayer().getName()+"1X") || stickMap.containsKey(event.getPlayer().getName()+"2X")) {
					stickMap.put(event.getPlayer().getName()+"1X", event.getBlock().getX());
					stickMap.put(event.getPlayer().getName()+"1Y", event.getBlock().getY());
					stickMap.put(event.getPlayer().getName()+"1Z", event.getBlock().getZ());
					stickMap.remove(event.getPlayer().getName()+"2X");
					stickMap.remove(event.getPlayer().getName()+"2Y");
					stickMap.remove(event.getPlayer().getName()+"2Z");
					event.getPlayer().sendMessage(ChatColor.DARK_RED+"Position 1 set at "+event.getBlock().getX()+"/"+event.getBlock().getY()+"/"+event.getBlock().getZ());
				} else {
					stickMap.put(event.getPlayer().getName()+"2X", event.getBlock().getX());
					stickMap.put(event.getPlayer().getName()+"2Y", event.getBlock().getY());
					stickMap.put(event.getPlayer().getName()+"2Z", event.getBlock().getZ());
					event.getPlayer().sendMessage(ChatColor.GREEN+"Position 2 set at "+event.getBlock().getX()+"/"+event.getBlock().getY()+"/"+event.getBlock().getZ());
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
