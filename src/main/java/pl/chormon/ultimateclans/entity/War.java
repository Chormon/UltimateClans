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

import java.util.Date;

/**
 *
 * @author Chormon
 */
public class War {

    private final Clan clan1;
    private final Clan clan2;
    private final Date firstStartDate;
    private Date startDate;
    private boolean agreed1 = false;
    private boolean agreed2 = false;

    public War(Clan clan1, Clan clan2, Date startDate) {
        this.clan1 = clan1;
        this.clan2 = clan2;
        this.firstStartDate = startDate;
        this.startDate = startDate;
        agreed1 = true;
    }

    public Clan getClan1() {
        return clan1;
    }

    public Clan getClan2() {
        return clan2;
    }

    public Date getFirstStartDate() {
        return firstStartDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean isAgreed1() {
        return agreed1;
    }

    public boolean isAgreed2() {
        return agreed2;
    }

    public void changeDate(Date startDate, Clan clan) {
        boolean sameDate = false;
        if (this.startDate.equals(startDate)) {
            sameDate = true;
        }
        if (clan1.getID() == clan.getID()) {
            agreed1 = true;
            agreed2 = sameDate;
        } else if (clan2.getID() == clan.getID()) {
            agreed1 = sameDate;
            agreed2 = true;
        } else {
            return;
        }
        this.startDate = startDate;
        if (agreed1 && agreed2) {

        }
    }

}
