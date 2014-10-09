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
package pl.chormon.ultimateclans.listeners;

import java.util.SortedSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pl.chormon.ultimateclans.Config;
import pl.chormon.ultimateclans.UltimateClans;
import pl.chormon.ultimateclans.entity.Clan;
import pl.chormon.ultimateclans.entity.UCPlayer;
import pl.chormon.ultimateclans.utils.VisUtil;
import pl.chormon.ultimatelib.utils.MsgUtils;

/**
 *
 * @author Chormon
 */
public class PlayerListener implements Listener {

    private final UltimateClans plugin;

    public PlayerListener(Plugin plugin) {
        this.plugin = (UltimateClans) plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }
//        UCPlayer ucp = new UCPlayer(0, player.getUniqueId(), player.getName());
        UCPlayer ucp = UCPlayer.getPlayer(player);
        plugin.addPlayer(ucp);
        Clan clan = ucp.getClan();
        if (clan != null && !plugin.clanExists(clan.getTag())) {
            plugin.addClan(clan);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }
        UCPlayer ucp = plugin.removePlayer(player.getName());
        if (ucp == null) {
            return;
        }
        Clan clan = ucp.getClan();
        if (clan != null) {
            SortedSet<UCPlayer> onlineMembers = clan.getOnlineMembers();
            if (onlineMembers.isEmpty() || (onlineMembers.size() == 1 && onlineMembers.first().equals(ucp))) {
                plugin.removeClan(clan.getTag());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p == null) {
            return;
        }
        String message = e.getMessage();
        UCPlayer ucp = UCPlayer.getPlayer(p);

        if (ucp == null) {
            return;
        }

        Clan clan = ucp.getClan();
        String tag = "";
        String role = "";
        if (clan != null) {
            tag = clan.getTag();
            role = ucp.getRole().prefix();
        }

        if (e.getFormat().contains("{role}")) {
            e.setFormat(e.getFormat().replace("{role}", role));
        }
        if (e.getFormat().contains("{tag}")) {
            e.setFormat(e.getFormat().replace("{tag}", tag));
        }
        if (e.getFormat().contains("{relcolor}")) {
            if (Config.getChatColorsEnabled() && clan != null) {
                for (Player player : e.getRecipients()) {
                    String msg = e.getFormat();
                    MsgUtils.msg(player, msg.replace("{relcolor}", clan.getColor(player)));
                }
                e.setCancelled(true);
            } else {
                e.setFormat(e.getFormat().replace("{relcolor}", ""));
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMoveClearVisualizations(PlayerMoveEvent event) {
        if (isSameBlock(event)) {
            return;
        }
        VisUtil.clear(event.getPlayer());
    }

    public static boolean isSameBlock(PlayerMoveEvent event) {
        return isSameBlock(event.getFrom(), event.getTo());
    }

    public static boolean isSameBlock(Location one, Location two) {
        if (one.getBlockX() != two.getBlockX()) {
            return false;
        }
        if (one.getBlockZ() != two.getBlockZ()) {
            return false;
        }
        if (one.getBlockY() != two.getBlockY()) {
            return false;
        }
        return one.getWorld().equals(two.getWorld());
    }

}
