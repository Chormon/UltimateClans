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
package pl.chormon.ultimateclans.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 *
 * @author Chormon
 */
public class MsgUtils {

    private static ConsoleCommandSender console;
    private final static String prefix = "&e[UltimateClans]&r";
    private final static String info = "&a[INFO]&r ";
    private final static String warning = "&e[WARNING]&r ";
    private final static String error = "&c[ERROR]&r ";

    public static void setConsole(ConsoleCommandSender console) {
        MsgUtils.console = console;
    }
    
    private static void log(String msg, String prefix, Object... vars) {
        String[] split = fixMsg(msg, vars).split("\n");
        String pre = fixMsg(MsgUtils.prefix + prefix);
        for(String str : split) {
            console.sendMessage(pre + str);
        }
    }

    public static void info(String msg) {
        MsgUtils.log(msg, info, (Object) null);
    }

    public static void info(String msg, Object... vars) {
        MsgUtils.log(msg, info, vars);
    }

    public static void warning(String msg) {
        MsgUtils.log(msg, warning, (Object) null);
    }

    public static void warning(String msg, Object... vars) {
        MsgUtils.log(msg, warning, vars);
    }

    public static void error(String msg) {
        MsgUtils.log(msg, error, (Object) null);
    }

    public static void error(String msg, Object... vars) {
        MsgUtils.log(msg, error, vars);
    }

    public static void msg(CommandSender cs, String msg) {
        msg(cs, msg, (Object) null);
    }

    public static void msg(CommandSender cs, String msg, Object... vars) {
        String[] split = fixMsg(msg, vars).split("\n");
        for(String str : split) {
            cs.sendMessage(str);
        }
    }
    
    public static void broadcast(String msg) {
        broadcast(msg, (Object) null);
    }
    
    public static void broadcast(String msg, Object... vars) {
        String[] split = fixMsg(msg, vars).split("\n");
        for(String str : split) {
            Bukkit.getServer().broadcastMessage(str);
        }
    }

    private static String fixMsg(String msg, Object... vars) {
        if (vars != null) {
            for (int i = 0; i < vars.length; i++) {
                if (vars[i] == null) {
                    break;
                }
                msg = msg.replace(vars[i].toString(), vars[++i].toString());
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
