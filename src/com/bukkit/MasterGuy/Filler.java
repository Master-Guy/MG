package com.bukkit.MasterGuy;

import java.io.File;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * Filler for Bukkit
 *
 * @author Master-Guy
 */

public class Filler extends JavaPlugin {
    private final FillerPlayerListener playerListener = new FillerPlayerListener(this);
    private final FillerBlockListener blockListener = new FillerBlockListener(this);
    private final Settings Settings = new Settings();
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	public HashMap<String, Integer> stickMap = new HashMap<String, Integer>();

	public String pluginName = "Filler";
	public String pluginVersion = "0.2";
	public String pluginNotes = "The configuration file can be found in /settings/";

    public Filler(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
        log("Plugin enabled: "+pluginName+" version "+pluginVersion);
        if(pluginNotes.length() > 0) { log(pluginNotes); }
        Settings.testFolderExists("settings");
        Settings.getSetting("settings/Filler.ini", "fillCommand", "/fill");
        Settings.getSetting("settings/Filler.ini", "fillHollowCommand", "/fillhollow");
        Settings.getSetting("settings/Filler.ini", "allowedPlayers", "MasterGuy013,AdminAccount1,ModAccount2,PlayerAccount3", ",");
        Settings.getSetting("settings/Filler.ini", "toolID", "280");
        Settings.getSetting("settings/Filler.ini", "allowedBlocks", "0,1,2,3,4,5,7,8,10,12,13,14,15,16,17,18,19,20,21,22,23,24,25,35,37,38,39,40,41,42,43,44,45,46,47,48,49,52,53,54,56,57,58,60,61,67,73,74,79,80,81,82,83,84,85,86,87,88,89,91,92");
    }
    
    public void onDisable() {
        System.out.println("Plugin disabled: "+pluginName+" version "+pluginVersion);
    }
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
    
    public void log(String logText) {
    	System.out.println(logText);
    }
}
