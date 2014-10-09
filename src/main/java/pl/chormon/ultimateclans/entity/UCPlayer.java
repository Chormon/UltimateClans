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

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.chormon.ultimateclans.Role;
import pl.chormon.ultimateclans.UltimateClans;

/**
 *
 * @author Chormon
 */
public class UCPlayer implements Comparable<UCPlayer> {

    private final int ID;
    private final UUID uuid;
    private final String name;
    private Clan clan = null;
    private Role role = Role.MEMBER;
    private CommandSender sender = null;
    private boolean senderInitialized = false;
    
    public CommandSender getSender() {
        if(!senderInitialized) {
            sender = Bukkit.getPlayer(uuid);
            senderInitialized = true;
        }
        return sender;
    }

    public UCPlayer(int ID, UUID uuid, String name) {
        this.ID = ID;
        this.uuid = uuid;
        this.name = name;
    }

    public static UCPlayer getPlayer(String name) {
        UCPlayer ucp = UltimateClans.get().getPlayer(name);
        if (ucp == null) {
            
        }
        return ucp;
    }

    public static UCPlayer getPlayer(UUID uuid) {
        UCPlayer ucp = null;
        return ucp;
    }

    public static UCPlayer getPlayer(Player player) {
        if (player == null) {
            return null;
        }
        UCPlayer ucp = UltimateClans.get().getPlayer(player.getName());
        if (ucp == null) {
            ucp = getPlayer(player.getUniqueId());
        }
        if(ucp == null) {
            ucp = new UCPlayer(0, player.getUniqueId(), player.getName());
        }
        return ucp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(role.prefix()).append(") ");
        sb.append(name).append(": ");
        sb.append(getHealth()).append(" ");
        sb.append(getHunger()).append(" ");
        sb.append(getArmor()).append(" ");
        sb.append(getWeapons());
        return sb.toString();
    }

    public String getInfo2() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(role.prefix()).append(") ");
        sb.append(name).append(": ");
        sb.append(getHealth2()).append(" ");
        sb.append(getHunger2()).append(" ");
        sb.append(getArmor()).append(" ");
        sb.append(getWeapons());
        return sb.toString();
    }

    public String getInfo3() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(role.prefix()).append(") ");
        sb.append(name).append(": ");
        sb.append(getHealth3()).append(" ");
        sb.append(getHunger3()).append(" ");
        sb.append(getArmor()).append(" ");
        sb.append(getWeapons());
        return sb.toString();
    }

    private Player getPlayer() {
        return UltimateClans.get().getServer().getPlayer(uuid);
    }

    private String getHealth() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        double health = p.getHealth();
        double max = p.getMaxHealth();
        for (int i = 0; i < max; i++) {
            if (i < health) {
                sb.append("&c");
                sb.append("♥");
            } else {
                sb.append("&7");
                sb.append("♡");
            }
        }
        return sb.toString();
    }

    private String getHunger() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        double food = p.getFoodLevel();
        double max = 20;
        for (int i = 0; i < max; i++) {
            if (i < food) {
                sb.append("&6");
                sb.append("♣");
            } else {
                sb.append("&7");
                sb.append("♧");
            }
        }
        return sb.toString();
    }

    private String getHealth2() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        double health = p.getHealth();
        double max = p.getMaxHealth();
        long percent = Math.round(100.0 * health / max);
        return "&c" + percent + "%";
    }

    private String getHunger2() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        double food = p.getFoodLevel();
        double max = 20;
        long percent = Math.round(100.0 * food / max);
        return "&6" + percent + "%";
    }

    private String getHealth3() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        int health = (int) p.getHealth();
        int max = (int) p.getMaxHealth();
        return "&c" + health + "/" + max;
    }

    private String getHunger3() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        int food = p.getFoodLevel();
        int max = 20;
        return "&6" + food + "/" + max;
    }

    private String getArmor() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ItemStack helmet = p.getInventory().getHelmet();
        ItemStack chestplate = p.getInventory().getChestplate();
        ItemStack leggings = p.getInventory().getLeggings();
        ItemStack boots = p.getInventory().getBoots();
        if (helmet == null || helmet.getType().equals(Material.AIR)) {
            sb.append("&7");
        } else {
            switch (helmet.getType()) {
                case AIR:
                    sb.append("&7");
                    break;
                case LEATHER_HELMET:
                    sb.append("&6");
                    break;
                case GOLD_HELMET:
                    sb.append("&e");
                    break;
                case CHAINMAIL_HELMET:
                    sb.append("&8");
                    break;
                case IRON_HELMET:
                    sb.append("&f");
                    break;
                case DIAMOND_HELMET:
                    sb.append("&b");
                    break;
            }
            sb.append("H");
        }
        if (chestplate == null || chestplate.getType().equals(Material.AIR)) {
            sb.append("&7");
        } else {
            switch (chestplate.getType()) {
                case AIR:
                    sb.append("&7");
                    break;
                case LEATHER_CHESTPLATE:
                    sb.append("&6");
                    break;
                case GOLD_CHESTPLATE:
                    sb.append("&e");
                    break;
                case CHAINMAIL_CHESTPLATE:
                    sb.append("&8");
                    break;
                case IRON_CHESTPLATE:
                    sb.append("&f");
                    break;
                case DIAMOND_CHESTPLATE:
                    sb.append("&b");
                    break;
            }
            sb.append("C");
        }
        if (leggings == null || leggings.getType().equals(Material.AIR)) {
            sb.append("&7");
        } else {
            switch (leggings.getType()) {
                case AIR:
                    sb.append("&7");
                    break;
                case LEATHER_LEGGINGS:
                    sb.append("&6");
                    break;
                case GOLD_LEGGINGS:
                    sb.append("&e");
                    break;
                case CHAINMAIL_LEGGINGS:
                    sb.append("&8");
                    break;
                case IRON_LEGGINGS:
                    sb.append("&f");
                    break;
                case DIAMOND_LEGGINGS:
                    sb.append("&b");
                    break;
            }
            sb.append("L");
        }
        if (boots == null || boots.getType().equals(Material.AIR)) {
            sb.append("&7");
        } else {
            switch (boots.getType()) {
                case AIR:
                    sb.append("&7");
                    break;
                case LEATHER_BOOTS:
                    sb.append("&6");
                    break;
                case GOLD_BOOTS:
                    sb.append("&e");
                    break;
                case CHAINMAIL_BOOTS:
                    sb.append("&8");
                    break;
                case IRON_BOOTS:
                    sb.append("&f");
                    break;
                case DIAMOND_BOOTS:
                    sb.append("&b");
                    break;
            }
            sb.append("B");
        }
        return sb.toString();
    }

    private String getWeapons() {
        Player p = getPlayer();
        if (p == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        PlayerInventory inv = p.getInventory();
        int count = getItemCount(inv.all(Material.DIAMOND_SWORD));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&bS").append(countString);
        }
        count = getItemCount(inv.all(Material.IRON_SWORD));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&fS").append(countString);
        }
        count = getItemCount(inv.all(Material.STONE_SWORD));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&8S").append(countString);
        }
        count = getItemCount(inv.all(Material.GOLD_SWORD));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&eS").append(countString);
        }
        count = getItemCount(inv.all(Material.WOOD_SWORD));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&6S").append(countString);
        }
        count = getItemCount(inv.all(Material.BOW));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&fB").append(countString);
        }
        count = getItemCount(inv.all(Material.ARROW));
        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            sb.append("&fA").append(countString);
        }
        return sb.toString();
    }

    private int getItemCount(HashMap<Integer, ? extends ItemStack> items) {
        int count = 0;
        for (ItemStack is : items.values()) {
            count += is.getAmount();
        }
        return count;
    }

    public boolean isOnline() {
        Player p = UltimateClans.get().getServer().getPlayer(uuid);
        if (p == null) {
            return false;
        }
        return p.isOnline();
    }

    @Override
    public String toString() {
//        if (clan == null) {
        return name;
//        }
//        return role.prefix() + " " + name;
    }

    @Override
    public int compareTo(UCPlayer o) {
        if (role.id() < o.getRole().id()) {
            return -1;
        }
        if (role.id() > o.getRole().id()) {
            return 1;
        }
        return name.compareToIgnoreCase(o.getName());
    }

}
