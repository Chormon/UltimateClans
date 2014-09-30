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
package pl.chormon.ultimateclans.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.chormon.ultimateclans.Config;
import pl.chormon.ultimateclans.Role;
import pl.chormon.ultimateclans.UltimateClans;
import pl.chormon.ultimatelib.utils.MsgUtils;
import pl.chormon.ultimatelib.utils.StringUtils;

/**
 *
 * @author Chormon
 */
public class Clan implements Serializable, Comparable<Clan> {

    private final int ID;
    private final String tag;
    private final String name;
//    private UCPlayer leader;
    private final List<String> board = new ArrayList<>();
//    private final SortedSet<UCPlayer> moderators = new TreeSet<>();
    private final SortedSet<UCPlayer> members = new TreeSet<>();
    private final SortedSet<UCPlayer> invitedPlayers = new TreeSet<>();
    private final SortedSet<Clan> allies = new TreeSet<>();
    private final SortedSet<Clan> enemies = new TreeSet<>();
    private Location home;
    private double balance;
    private final Date cretionDate;
    private Date expirationDate;

    public Clan(int ID, String tag, String name, UCPlayer leader) {
        this.ID = ID;
        this.tag = tag;
        this.name = name;
//        this.leader = leader;
        leader.setRole(Role.LEADER);
        members.add(leader);
        this.cretionDate = new Date();
    }

    public static Clan getClan(String tag) {
        Clan clan = UltimateClans.get().getClan(tag);
        if (clan == null) {

        }
        return clan;
    }

    public static List<Clan> getClans(int page) {
        return new ArrayList<>();
    }

    public static List<Clan> getClans() {
        return getClans(0);
    }

    public int getID() {
        return ID;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public UCPlayer getLeader() {
        for (UCPlayer player : members) {
            if (player.getRole().equals(Role.LEADER)) {
                return player;
            }
        }
        return null;
    }

    public void setLeader(UCPlayer leader) {
        UCPlayer oldLeader = getLeader();
        oldLeader.setRole(Role.MODERATOR);
        leader.setRole(Role.LEADER);
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void extend() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(expirationDate);
        cl.add(Calendar.DATE, Config.getExtensionTime());
        expirationDate = cl.getTime();
    }

    public List<String> getBoard() {
        return board;
    }

    public SortedSet<UCPlayer> getModerators() {
        SortedSet<UCPlayer> moderators = new TreeSet<>();
        for (UCPlayer player : members) {
            if (player.getRole().equals(Role.MODERATOR)) {
                moderators.add(player);
            }
        }
        return moderators;
    }

    public SortedSet<UCPlayer> getMembers() {
        return members;
    }

    public SortedSet<UCPlayer> getOnlineMembers() {
        SortedSet<UCPlayer> onlineMembers = new TreeSet<>();
        for (UCPlayer player : members) {
            if (player.isOnline()) {
                onlineMembers.add(player);
            }
        }
        return onlineMembers;
    }

    public void addMember(String name) {

    }

    public void addMember(UCPlayer player) {

    }

    public void removeMember(String name) {

    }

    public void removeMember(UCPlayer player) {

    }

    public void addInvitedPlayer(String name) {

    }

    public void addInvitedPlayer(UCPlayer player) {

    }

    public static void create(UCPlayer player, String tag, String name) {
        List<Clan> clans = Clan.getClans();
        for (Clan clan : clans) {
            if (clan.tag.equals(tag)) {
                return;
            }
            if (clan.name.equals(name)) {
                return;
            }
        }
        int ID = 0;
        Clan clan = new Clan(ID, tag, name, player);
        player.setClan(clan);
        UltimateClans.get().addClan(clan);
    }

    public static void disband(String tag) {

    }

    public static void disband(int ID) {

    }

    public void disband() {

    }

    public static void save(String tag) {

    }

    public static void save(int ID) {

    }

    public void save() {

    }

    public String getBasicInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(tag).append(" - ");
        sb.append(name).append(": ");
        sb.append(members.size());
        return sb.toString();
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informacje o wiosce ").append(tag).append(" (").append(name).append("):\n");
        sb.append("Data założenia: ").append(cretionDate).append("\n");
        sb.append("Data ważności: ").append(expirationDate).append("\n");
        sb.append("Sojusznicy: ").append(cretionDate).append("\n");
        sb.append("Wrogowie: ").append(cretionDate).append("\n");
//        sb.append("W stanie wojny z ").append(cretionDate).append("\n");
        sb.append("Członkowie: ").append(StringUtils.implode(new ArrayList(members), ", ")).append("\n");
        return sb.toString();
    }

    public void msg(String message) {
        msg(message, (Object) null);
    }

    public void msg(String message, Object... vars) {
        for (UCPlayer ucp : members) {
            Player p = UltimateClans.get().getServer().getPlayer(ucp.getUuid());
            if (p != null) {
                MsgUtils.msg(p, message, vars);
            }
        }
    }
    
    public String getColor(Player player) {
        return "";
    }

    @Override
    public String toString() {
        return tag;
    }

    @Override
    public int compareTo(Clan o) {
        return tag.compareToIgnoreCase(o.getTag());
    }

}
