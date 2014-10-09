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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import pl.chormon.ultimateclans.commands.CmdUC;
import pl.chormon.ultimateclans.entity.Clan;
import pl.chormon.ultimateclans.entity.UCPlayer;
import pl.chormon.ultimateclans.listeners.PlayerListener;
import pl.chormon.ultimatelib.UltimateLib;
import pl.chormon.ultimatelib.utils.MsgUtils;

/**
 *
 * @author Chormon
 */
public class UltimateClans extends UltimateLib {
    
    private static UltimateClans plugin;
    private Map<String, Clan> clans;
    private Map<String, UCPlayer> players;
    
    @Override
    public void onEnable() {
        plugin = this;
        pdf = this.getDescription();
        msgUtils = new MsgUtils("&e[UltimateClans]&r");
        msgUtils.setConsole(Bukkit.getConsoleSender());
        Config.initConfig();        
        clans = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();
        
        getCommand("klan").setExecutor(new CmdUC());

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        msgUtils.info("{name} {version} enabled!", "{name}", pdf.getName(), "{version}", pdf.getVersion());
    }
    
    @Override
    public void onDisable() {
        msgUtils.info("{name} {version} disabled!", "{name}", pdf.getName(), "{version}", pdf.getVersion());        
    }
    
    public static UltimateClans get() {
        return (UltimateClans) plugin;
    }
    
    public Map<String, UCPlayer> getPlayers() {
        return players;
    }
    
    public UCPlayer getPlayer(String name) {
        return players.get(name.toLowerCase());
    }
    
    public boolean playerExists(String name) {
        return players.containsKey(name.toLowerCase());
    }
    
    public UCPlayer addPlayer(UCPlayer player) {
        return players.put(player.getName().toLowerCase(), player);
    }
    
//    public UCPlayer removePlayer(UCPlayer player) {
//        return removePlayer(player.getName());
//    }
    
    public UCPlayer removePlayer(String name) {
        return players.remove(name.toLowerCase());
    }
    
    public Map<String, Clan> getClans() {
        return clans;
    }
    
    public Clan getClan(String tag) {
        return clans.get(tag.toLowerCase());
    }
    
    public boolean clanExists(String tag) {
        return clans.containsKey(tag.toLowerCase());
    }
    
    public Clan addClan(Clan clan) {
        return clans.put(clan.getTag().toLowerCase(), clan);
    }
    
//    public Clan removeClan(Clan clan) {
//        return removeClan(clan.getTag());
//    }
    
    public Clan removeClan(String tag) {
        return clans.remove(tag.toLowerCase());
    }
    
}
