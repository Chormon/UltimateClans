/*
 * The MIT License
 *
 * Copyright 2014 Chormon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.chormon.ultimateclans;

import org.bukkit.plugin.Plugin;

/**
 *
 * @author Chormon
 */
public class Config {

    private static Plugin plugin;

    public static void initConfig() {
        plugin = UltimateClans.get();
        plugin.reloadConfig();
        plugin.saveDefaultConfig();
    }
    
    public static int getExtensionTime() {
        return plugin.getConfig().getInt("extensionTime");
    }
    
    public static int getMaxPlayers() {
        return plugin.getConfig().getInt("maxPlayers");
    }
    
    public static int getMinPlayers() {
        return plugin.getConfig().getInt("minPlayers");
    }
    
    public static boolean getBanksEnabled() {
        return plugin.getConfig().getBoolean("banksEnabled");
    }
    
    public static double getStartingBalance() {
        return plugin.getConfig().getDouble("startingBalance");
    }
    
    public static boolean getChatColorsEnabled() {
        return plugin.getConfig().getBoolean("chatColorsEnabled");
    }
    
    public static boolean getTagColorsEnabled() {
        return plugin.getConfig().getBoolean("tagColorsEnabled");
    }
    
    public static String getColorAlly() {
        return plugin.getConfig().getString("colorAlly");
    }
    
    public static String getColorEnemy() {
        return plugin.getConfig().getString("colorEnemy");
    }
}
