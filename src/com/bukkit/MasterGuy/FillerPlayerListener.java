package com.bukkit.MasterGuy;

import java.util.HashMap;
import org.bukkit.World;
//import org.bukkit.Material;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author Master-Guy
 */

public class FillerPlayerListener extends PlayerListener {
    private final Filler plugin;
	private HashMap<String, Integer> stickMap;
	private Integer minX, minY, minZ, maxX, maxY, maxZ, newDataType;
	private World world;
	private final Settings Settings = new Settings();

    public FillerPlayerListener(Filler instance) {
        plugin = instance;
        Settings.testFolderExists(plugin.iniPath);
    }
    
    public void onPlayerCommand(PlayerChatEvent event) {
    	String[] split = event.getMessage().split(" "), setting;
        stickMap = this.plugin.stickMap;
        final String command = split[0].toString();
        boolean allowPlayer, allowBlock;
        Integer I;
        
        if(
            	command.equalsIgnoreCase(Settings.getSetting(plugin.iniFile, "fillCommand", "/fill")[0]) ||
            	command.equalsIgnoreCase(Settings.getSetting(plugin.iniFile, "fillHollowCommand", "/fillhollow")[0])
        ) {
	    	allowPlayer = false;
	    	I = 0;
	    	setting = Settings.getSetting(plugin.iniFile, "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",");
	    	while (I < setting.length) {
	    		if(event.getPlayer().getName().equalsIgnoreCase(setting[I])) {
	    			allowPlayer = true;
	    		}
	    		I = I + 1;
	    	}
	    	if(setting[0].equalsIgnoreCase("*")) {
	    		allowPlayer = true;
	    	}
	    	
	    	allowBlock = false;
	    	I = 0;
	    	setting = Settings.getSetting(plugin.iniFile, "allowedBlocks", "1,2,3,4,5,7,8,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,35,37,38,39,40,41,42,43,44,45,46,47,48,49,52,53,54,56,57,58,60,61,67,73,74,79,80,81,82,83,84,85,86,87,88,89,91,92", ",");
	    	while (I < setting.length) {
	    		if(split.length > 1) {
		    		if(split[1].equalsIgnoreCase(setting[I])) {
		    			allowBlock = true;
		    		}
	    		}
	    		I = I + 1;
	    	}
	    	if(setting[0].equalsIgnoreCase("*")) {
	    		allowBlock = true;
	    	}
	        
	        // Fill cubic
	        if(command.equalsIgnoreCase(Settings.getSetting(plugin.iniFile, "fillCommand", "/fill")[0])) {
	        	if(allowPlayer) {
	        		if(allowBlock) {
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
			        		} catch (Exception e) {
			        			event.getPlayer().sendMessage("You need to enter a valid blocktype.");
			        		} finally {
				    		}
			    		} else {
			    			event.getPlayer().sendMessage("This command requires a blocktype number as parameter.");
			    		}
	        		} else {
	        			event.getPlayer().sendMessage("This block is not allowed!");
	        		}
	        	} else {
	        		event.getPlayer().sendMessage("You do not have access to this command!");
	        	}
	        	event.setCancelled(true);
	        }
	        
	        // Fill hollow cubic
	        if(command.equalsIgnoreCase(Settings.getSetting(plugin.iniFile, "fillHollowCommand", "/fillhollow")[0])) {
	        	if(allowPlayer) {
	            	if(allowBlock) {
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
	            	} else {
	            		event.getPlayer().sendMessage("This block is not allowed!");
	            	}
	        	} else {
	        		event.getPlayer().sendMessage("You do not have access to this command!");
	        	}
	        	event.setCancelled(true);
	        }
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
						world.getBlockAt(minX, minY, minZ).setTypeId(blockType);
					} catch (Exception e) {
						log("Fill/FillHollow: Fail at "+minX+"/"+minY+"/"+minZ+"!");
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
						world.getBlockAt(minX, minY, minZ).setTypeId(0);
					} catch (Exception e) {
						log("Fill/FillHollow: Fail at "+minX+"/"+minY+"/"+minZ+"!");
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
