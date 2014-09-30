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

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chormon.ultimateclans.Perm;
import pl.chormon.ultimatelib.utils.MsgUtils;
import pl.chormon.ultimatelib.utils.StringUtils;

/**
 *
 * @author Chormon
 */
public class UCCommand implements CommandExecutor {

    protected String desc = "brak opisu";
    protected List<String> aliases = new ArrayList<>();
    protected List<UCCommand> subCommands = new ArrayList<>();
    protected UCCommand parent = null;
    protected List<String> reqArgs = new ArrayList<>();
    protected List<String> optionalArgs = new ArrayList<>();
    protected List<Perm> perms = new ArrayList();
    protected boolean errorOnToManyArgs = true;
    protected boolean senderIsConsole = false;
    protected CommandSender sender;
    protected List<String> args = new ArrayList<>();

    protected void addSubCommand(UCCommand command) {
        command.parent = this;
        this.subCommands.add(command);
    }

    public List<UCCommand> getSubCommands() {
        return this.subCommands;
    }

    protected void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }

    protected void addAlias(String alias) {
        this.aliases.add(alias);
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public UCCommand getParent() {
        return this.parent;
    }

    public void addArg(String arg) {
        this.reqArgs.add(arg);
    }

    public List<String> getArgs() {
        return this.reqArgs;
    }

    public void addOptionalArg(String arg, String def) {
        this.optionalArgs.add(arg + "=" + def);
    }

    public List<String> getOptionalArgs() {
        return this.optionalArgs;
    }

    protected void addPerm(Perm perm) {
        this.perms.add(perm);
    }

    protected void setErrorOnToManyArgs(boolean errorOnToManyArgs) {
        this.errorOnToManyArgs = errorOnToManyArgs;
    }

    public void perform(CommandSender sender, String label, List<String> args) {
        this.sender = sender;
        senderIsConsole = true;
        if (sender instanceof Player) {
            senderIsConsole = false;
        }
        this.args = args;
        perform();
    }

    public void perform() {
        MsgUtils.msg(sender, "&2" + this.desc);
        for (UCCommand cmd : subCommands) {
            MsgUtils.msg(sender, printUsage(cmd));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        parse(sender, this, label, new ArrayList<>(Arrays.asList(args)));
        return true;
    }

    private void parse(CommandSender sender, UCCommand command, String label, List<String> args) {
        if (!hasPerms(sender, command)) {
            noPerms(sender);
            return;
        }
        for (UCCommand cmd : command.getSubCommands()) {
            if (args.size() > 0 && StringUtils.containsIgnoreCase(cmd.getAliases(), args.get(0))) {
                args.remove(0);
                parse(sender, cmd, label, args);
                return;
            }
        }
        if (args.size() > (command.getArgs().size() + command.getOptionalArgs().size()) && command.errorOnToManyArgs) {
            tooManyParams(sender);
            printUsage(sender, command);
            return;
        }
        if (args.size() < command.getArgs().size()) {
            notEnoughParams(sender);
            printUsage(sender, command);
            return;
        }
        command.perform(sender, label, args);
    }

    private boolean hasPerms(CommandSender sender, UCCommand command) {
        for (Perm perm : command.perms) {
            if (!perm.has(sender)) {
                return false;
            }
        }
        return true;
    }

    protected void tooManyParams(CommandSender sender) {
        MsgUtils.msg(sender, "&4Za dużo argumentów!");
    }

    protected void notEnoughParams(CommandSender sender) {
        MsgUtils.msg(sender, "&4Za mało argumentów!");
    }

    protected void printUsage(CommandSender sender, UCCommand command) {
        MsgUtils.msg(sender, "&2Użycie: &f" + printUsage(command, false));
    }

    protected void noPerms(CommandSender sender) {
        MsgUtils.msg(sender, "&4Nie masz uprawnień do użycia tej komendy!");
    }

    protected String printUsage(UCCommand command) {
        return printUsage(command, true, true, true);
    }

    protected String printUsage(UCCommand command, boolean addDesc) {
        return printUsage(command, true, true, addDesc);
    }

    protected String printUsage(UCCommand command, boolean addArgs, boolean addOptionalArgs, boolean addDesc) {
        List<String> paths = new ArrayList<>();
        String d = command.getDesc();
        List<String> a = command.getArgs();
        List<String> oa = command.getOptionalArgs();
        while (command != null) {
            paths.add(StringUtils.implode(command.getAliases(), ","));
            command = command.getParent();
        }
        StringBuilder sb = new StringBuilder();
        String path = StringUtils.implode(Lists.reverse(paths), " ");
        sb.append("/");
        sb.append(path);
        if (addArgs) {
            for (String s : a) {
                sb.append(" <");
                sb.append(s);
                sb.append(">");
            }
        }
        if (addOptionalArgs) {
            for (String s : oa) {
                sb.append(" [");
                sb.append(s);
                sb.append("]");
            }
        }
        if (addDesc) {
            sb.append(" &7");
            sb.append(d);
        }
        return sb.toString();
    }

    protected void noClan(CommandSender sender) {
        MsgUtils.msg(sender, "&4Nie jesteś w żadnej wiosce!");
    }

}
