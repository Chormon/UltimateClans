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

import pl.chormon.ultimateclans.Perm;
import pl.chormon.ultimatelib.commands.req.ReqIsPerm;

/**
 *
 * @author Chormon
 */
public class CmdUC extends UCCommand {
    
    private final CmdList cmdList = new CmdList();
    private final CmdCreate cmdCreate = new CmdCreate();
    private final CmdInvite cmdInvite = new CmdInvite();
    private final CmdInfo cmdInfo = new CmdInfo();
    private final CmdPlayers cmdPlayers = new CmdPlayers();
    private final CmdBoard cmdBoard = new CmdBoard();
    private final CmdTell cmdTell = new CmdTell();
    private final CmdAlly cmdAlly = new CmdAlly();
    private final CmdEnemy cmdEnemy = new CmdEnemy();
    private final CmdDisband cmdDisband = new CmdDisband();
    private final CmdVisualize cmdVisualize = new CmdVisualize();

    public CmdUC() {
        this.addAlias("wioska");

        this.setDesc("Komendy pluginu UltimateClans");
        
        this.addReq(new ReqIsPerm(Perm.UC.node));
        
        this.addSubCommand(cmdList);
        this.addSubCommand(cmdCreate);
        this.addSubCommand(cmdInvite);
        this.addSubCommand(cmdInfo);
        this.addSubCommand(cmdPlayers);
        this.addSubCommand(cmdBoard);
        this.addSubCommand(cmdTell);
        this.addSubCommand(cmdAlly);
        this.addSubCommand(cmdEnemy);
        this.addSubCommand(cmdDisband);
        this.addSubCommand(cmdVisualize);
    }
    
}
