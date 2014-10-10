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

import java.util.SortedSet;
import java.util.TreeSet;
import pl.chormon.ultimateclans.Perm;
import pl.chormon.ultimateclans.entity.Clan;
import pl.chormon.ultimatelib.commands.req.ReqHasPerm;
import pl.chormon.ultimatelib.utils.MsgUtils;

/**
 *
 * @author Chormon
 */
public class CmdList extends UCCommand {

    public CmdList() {
        this.addAlias("lista");

        this.setDesc("Lista wiosek");

        this.addOptionalArg("strona", "1");

        this.addReq(new ReqHasPerm(Perm.LIST.node));
    }

    @Override
    public void perform() {
        int page = 1;
        if (args.size() > 0) {
            try {
                page = Integer.parseInt(args.get(0));
            } catch (NumberFormatException ex) {
                return;
            }
        }
        SortedSet<Clan> clans = new TreeSet<>();
        clans.addAll(Clan.getClans(page));

        MsgUtils.msg(sender, "Lista Wiosek:");
        if (clans.isEmpty()) {
            MsgUtils.msg(sender, "---BRAK---");
            return;
        }
        for (Clan clan : clans) {
            MsgUtils.msg(sender, clan.getBasicInfo());
        }
    }

}
