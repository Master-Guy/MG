package com.bukkit.mg.mg;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

/**
 * MG block listener
 * @author Master-Guy
 */

public class MGBlockListener extends BlockListener {
    private final MG plugin;
    private int I;
	private HashMap<String, Integer> stickMap;

    public MGBlockListener(final MG plugin) {
        this.plugin = plugin;
    }
    
    public void onBlockRightClicked(BlockRightClickEvent event) {
    	if(event.getItemInHand().getTypeId() == 280) {
            stickMap = this.plugin.stickMap;
    		log("stickMap size: " + stickMap.size());
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
