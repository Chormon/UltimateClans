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

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Chormon
 */
public class StringUtils {
    
    public static String implode (List strings, String separator) {
        StringBuilder sb = new StringBuilder();        
        Iterator ite = strings.iterator();
        while(ite.hasNext()) {
            sb.append(ite.next().toString());
            if(ite.hasNext())
                sb.append(separator);
        }
        return sb.toString();
    }
    
    public static boolean containsIgnoreCase(List<String> strings, String str) {
        for(String string : strings) {
            if(string.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
    
    public static String milisecondsToTime(int miliseconds) {
        String sec = "";
        if(miliseconds / 1000 > 0) sec = secondsToTime(miliseconds/1000);
        miliseconds %= 1000;
        
        StringBuilder sb = new StringBuilder();
        if(!sec.equals("")) sb.append(sec);
        if(miliseconds > 0) sb.append(miliseconds).append(" milisekund");
        return sb.toString();
    }
    
    public static String secondsToTime(int seconds) {
        if(seconds == 0) {
            return "0 sekund";
        }
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;
        int days = hours / 24;
        hours %= 24;
        StringBuilder sb = new StringBuilder();
        if(days > 0) sb.append(days).append(" dni ");
        if(hours > 0) sb.append(hours).append(" godzin ");
        if(minutes > 0) sb.append(minutes).append(" minut ");
        if(seconds > 0) sb.append(seconds).append(" sekund");
        return sb.toString();
    }
    
}
