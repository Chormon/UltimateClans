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

import org.bukkit.entity.Player;
import pl.chormon.ultimateclans.Perm;
import pl.chormon.ultimateclans.entity.Clan;
import pl.chormon.ultimateclans.entity.UCPlayer;
import pl.chormon.ultimatelib.commands.req.ReqHasPerm;
import pl.chormon.ultimatelib.utils.MsgUtils;

/**
 *
 * @author Chormon
 */
public class CmdInfo extends UCCommand {

    public CmdInfo() {
        this.addAlias("info");

        this.setDesc("Informacje o wiosce");

        this.addOptionalArg("wioska", "twoja");
        
        this.addReq(new ReqHasPerm(Perm.INFO.node));
    }

    @Override
    public void perform() {
        Clan clan;
        if (args.isEmpty()) {
            if (sender instanceof Player) {
                UCPlayer ucp = UCPlayer.getPlayer((Player)sender);
                if (ucp == null) {
                    return;
                }
                clan = ucp.getClan();
                if (clan == null) {
                    noClan(sender);
                    return;
                }
            } else {
                notEnoughParams(sender);
                printUsage(sender, this);
                return;
            }
        } else {
            clan = Clan.getClan(args.get(0));
            if (clan == null) {
                MsgUtils.msg(sender, "&4Wioska z tagiem &f{tag} &4nie istnieje!", "{tag}", args.get(0));
                return;
            }
        }
        MsgUtils.msg(sender, clan.getInfo());
    }

}
