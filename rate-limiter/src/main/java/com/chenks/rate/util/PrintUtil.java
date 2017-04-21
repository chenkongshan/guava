package com.chenks.rate.util;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.AnsiConsole.*;
import static org.fusesource.jansi.AnsiConsole.systemInstall;

/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/20
 * Version: since
 */
public class PrintUtil {

    static {
        System.setProperty("jansi.passthrough", "true");
        systemInstall();
    }


    public static void printGreen(String... msgs) {
        print(GREEN, msgs);
    }

    public static void printRed(String... msgs) {
        print(RED, msgs);
    }

    private static void print(Color color, String... msgs) {
        Ansi ansi = ansi().eraseScreen();
        for (String msg : msgs) {
            ansi = ansi.fg(color).a(msg);
        }
        System.out.println(ansi);
    }

    public static void main(String[] args) {
        printRed("hello World!");
        printGreen("这是绿色");
    }
}
