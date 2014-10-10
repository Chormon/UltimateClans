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
package pl.chormon.ultimateclans.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.chormon.ultimateclans.Config;
import pl.chormon.ultimateclans.commands.req.ReqHasntClan;
import pl.chormon.ultimateclans.commands.req.ReqIsPlayer;
import pl.chormon.ultimateclans.entity.UCPlayer;
import pl.chormon.ultimateclans.utils.VisUtil;
import pl.chormon.ultimatelib.utils.MsgUtils;

/**
 *
 * @author Chormon
 */
public class CmdVisualize extends UCCommand {

    private final Material materialOne = Config.getVisualizationMaterialOne();
    private final Material materialTwo = Config.getVisualizationMaterialTwo();

    public CmdVisualize() {
        this.addAlias("vis");

        this.setDesc("");
        
        this.addReq(new ReqHasntClan());
        this.addReq(new ReqIsPlayer());
    }

    @Override
    public void perform() {
        Player player = (Player) sender;
        UCPlayer ucp = UCPlayer.getPlayer(player);
        if (ucp != null && ucp.getClan() != null) {
            MsgUtils.msg(sender, "&cTylko gracze bez wioski mogą wykonać tą komendę!");
            return;
        }
        Location loc = player.getLocation();
        int x = loc.getBlockX() - Config.getWidth() / 2;
        int z = loc.getBlockZ() - Config.getLength() / 2;
        showPillar(player, x, z, Corner.NW);
        x += Config.getWidth();
        showPillar(player, x, z, Corner.NE);
        z += Config.getLength();
        showPillar(player, x, z, Corner.SE);
        x -= Config.getWidth();
        showPillar(player, x, z, Corner.SW);
    }

    private void showPillar(Player player, int x, int z, Corner corner) {
        for (int y = 0; y < Config.getHeight(); y++) {
            Location loc = new Location(player.getWorld(), x, player.getLocation().getBlockY() - 1 + y, z);
            if (y == 0 || y == Config.getHeight() - 1) {
                showCorner(player, loc, corner);
            }
            if (!locIsValid(loc)) {
                continue;
            }
            Material material = y == 0 || y == Config.getHeight() - 1 ? materialTwo : materialOne;
            VisUtil.addLocation(player, loc, material);
        }
    }

    private void showCorner(Player player, Location location, Corner corner) {
        int x = 0, z = 0;
        Material material = materialOne;
        switch (corner) {
            case NW:
                z = 1;
                x = 1;
                break;
            case NE:
                z = 1;
                x = -1;
                break;
            case SW:
                z = -1;
                x = 1;
                break;
            case SE:
                z = -1;
                x = -1;
                break;
        }
        Location loc = new Location(location.getWorld(), location.getBlockX() + x, location.getBlockY(), location.getBlockZ());
        if (locIsValid(loc)) {
            VisUtil.addLocation(player, loc, material);
        }
        loc = new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ() + z);
        if (locIsValid(loc)) {
            VisUtil.addLocation(player, loc, material);
        }
    }

    private boolean locIsValid(Location location) {
        if (Config.getVisualizeOnNonAirBlocks()) {
            return true;
        }
        return location.getBlock().getType() == Material.AIR;

    }

    private enum Corner {

        NW,
        NE,
        SW,
        SE;
    }

}
