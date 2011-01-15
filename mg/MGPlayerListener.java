package com.bukkit.mg.mg;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Master-Guy
 */

public class MGPlayerListener extends PlayerListener {
    private final MG plugin;
	private HashMap<String, Integer> stickMap;
	private Integer minX, minY, minZ, maxX, maxY, maxZ, newDataType;
	private World world;

    public MGPlayerListener(MG instance) {
        plugin = instance;
    }
    
    public void onPlayerCommand(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" ");
        stickMap = this.plugin.stickMap;
        final String command = split[0].toString();
        
        // Fill cubic
        if(command.equalsIgnoreCase("/fill")) {
        	if(split.length == 2) {
        		try {
        			newDataType = Integer.parseInt(split[1]);
		    		if(stickMap.containsKey(event.getPlayer().getName()+"2X")) {
		    			if(!fillCubic(newDataType, stickMap.get(event.getPlayer().getName()+"1X"), stickMap.get(event.getPlayer().getName()+"1Y"), stickMap.get(event.getPlayer().getName()+"1Z"), stickMap.get(event.getPlayer().getName()+"2X"), stickMap.get(event.getPlayer().getName()+"2Y"), stickMap.get(event.getPlayer().getName()+"2Z"))) {
		    				event.getPlayer().sendMessage("Something went wrong while replacing the blocks. Please try again or ask an admin to check the problem.");
		    			}
		    		} else {
		    			event.getPlayer().sendMessage("You need to set two positions first.");
		    		}
        		} finally {
	    		}
    		} else {
    			event.getPlayer().sendMessage("This command requires a blocktype number as parameter.");
    		}
        	event.setCancelled(true);
        }
        
        // Fill hollow cubic
        if(command.equalsIgnoreCase("/fillhollow")) {
        	if(split.length == 2) {
        		try {
        			newDataType = Integer.parseInt(split[1]);
		    		if(stickMap.containsKey(event.getPlayer().getName()+"2X")) {
		    			if(!fillHollowCubic(newDataType, stickMap.get(event.getPlayer().getName()+"1X"), stickMap.get(event.getPlayer().getName()+"1Y"), stickMap.get(event.getPlayer().getName()+"1Z"), stickMap.get(event.getPlayer().getName()+"2X"), stickMap.get(event.getPlayer().getName()+"2Y"), stickMap.get(event.getPlayer().getName()+"2Z"))) {
		    				event.getPlayer().sendMessage("Something went wrong while replacing the blocks. Please try again or ask an admin to check the problem.");
		    			}
		    		} else {
		    			event.getPlayer().sendMessage("You need to set two positions first.");
		    		}
        		} finally {
	    		}
    		} else {
    			event.getPlayer().sendMessage("This command requires a blocktype number as parameter.");
    		}
        	event.setCancelled(true);
        }
    }

    public void log(String logText) {
    	System.out.println(logText);
    }
    
	private boolean fillCubic(int blockType, int X1, int Y1, int Z1, int X2, int Y2, int Z2) {
		if(X1 < X2) {
			minX = X1;
			maxX = X2;
		} else {
			maxX = X1;
			minX = X2;
		}
		while(minX <= maxX) {
			if(Y1 < Y2) {
				minY = Y1;
				maxY = Y2;
			} else {
				maxY = Y1;
				minY = Y2;
			}
			while(minY <= maxY) {
    			if(Z1 < Z2) {
    				minZ = Z1;
    				maxZ = Z2;
    			} else {
    				maxZ = Z1;
    				minZ = Z2;
    			}
				while(minZ <= maxZ) {
					try {
						world = this.plugin.getServer().getWorlds()[0];
						world.getBlockAt(minX, minY, minZ).setTypeID(blockType);
					} catch (Exception e) {
						log("Fail at "+minX+"/"+minY+"/"+minZ+"!");
						return false;
					} finally {
					}
					minZ = minZ + 1;
				}
				minY = minY + 1;
			}
			minX = minX + 1;
		}
		return true;
	}
	
	private boolean fillHollowCubic(int blockType, int X1, int Y1, int Z1, int X2, int Y2, int Z2) {
		fillCubic(blockType, X1, Y1, Z1, X2, Y2, Z2);
		if(X1 < X2) {
			minX = X1+1;
			maxX = X2-1;
		} else {
			maxX = X1-1;
			minX = X2+1;
		}
		while(minX <= maxX) {
			if(Y1 < Y2) {
				minY = Y1+1;
				maxY = Y2-1;
			} else {
				maxY = Y1-1;
				minY = Y2+1;
			}
			while(minY <= maxY) {
    			if(Z1 < Z2) {
    				minZ = Z1+1;
    				maxZ = Z2-1;
    			} else {
    				maxZ = Z1-1;
    				minZ = Z2+1;
    			}
				while(minZ <= maxZ) {
					try {
						world = this.plugin.getServer().getWorlds()[0];
						world.getBlockAt(minX, minY, minZ).setTypeID(0);
					} catch (Exception e) {
						log("Fail at "+minX+"/"+minY+"/"+minZ+"!");
						return false;
					} finally {
					}
					minZ = minZ + 1;
				}
				minY = minY + 1;
			}
			minX = minX + 1;
		}
		return true;
	}
}
