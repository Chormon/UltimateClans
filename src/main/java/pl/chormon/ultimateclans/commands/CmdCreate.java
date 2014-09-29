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

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chormon.ultimateclans.entity.Clan;
import pl.chormon.ultimateclans.entity.UCPlayer;
import pl.chormon.ultimateclans.utils.MsgUtils;
import pl.chormon.ultimateclans.utils.StringUtils;

/**
 *
 * @author Chormon
 */
public class CmdCreate extends UCCommand {

    public CmdCreate() {
        this.addAlias("stworz");

        this.setDesc("");

        this.addArg("tag");

        this.addArg("nazwa");

        this.setErrorOnToManyArgs(false);
    }

    @Override
    public void perform(CommandSender sender, String label, List<String> args) {
        if (!(sender instanceof Player)) {
            MsgUtils.msg(sender, "&cMusisz być graczem, żeby wykonać tą komendę!");
            return;
        }
        String tag = args.get(0);
        args.remove(0);
        String name = StringUtils.implode(args, " ");
        List<Clan> clans = Clan.getClans();
        for (Clan clan : clans) {
            if (clan.getTag().equals(tag)) {
                MsgUtils.msg(sender, "&4Wioska o tagu &f{tag} &4już istnieje!", "{tag}", tag);
                return;
            }
            if (clan.getName().equals(name)) {
                MsgUtils.msg(sender, "&4Wioska o nazwie &f{name} &4już istnieje!", "{name}", name);
                return;
            }
        }
        UCPlayer player = UCPlayer.getPlayer((Player)sender);
        if (player == null) {
            MsgUtils.msg(sender, "&4Wioska o tagu &f{tag} &4już istnieje!", "{tag}", tag);
            return;
        }
        try {
            Clan.create(player, tag, name);
        } catch (Exception ex) {
            MsgUtils.error(ex.getMessage());
            MsgUtils.msg(sender, "&4Wystąpił błąd podczas tworzenia wioski. Jeśli to się powtórzy skontaktuj się z Administratorem serwera!");
            return;
        }
        MsgUtils.broadcast("&f{player} &2założył nową wioskę &f{tag} &2(&f{name}&2)!", "{player}", player.getName(), "{tag}", tag, "{name}", name);
    }

}