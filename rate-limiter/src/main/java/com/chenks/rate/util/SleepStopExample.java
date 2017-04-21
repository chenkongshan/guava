package com.chenks.rate.util;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Uninterruptibles;

import java.util.Scanner;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/20
 * Version: since
 */
public class SleepStopExample {

    abstract static class SleepingStopwatch {
        /**
         * Constructor for use by subclasses.
         */
        protected SleepingStopwatch() {
        }

        protected abstract long readMicros();

        protected abstract void sleepMicrosUninterruptibly(long micros);

        public static final SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() {
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override
                protected long readMicros() {
                    return stopwatch.elapsed(MILLISECONDS);
                }

                @Override
                protected void sleepMicrosUninterruptibly(long micros) {
                    if (micros > 0) {
                        Uninterruptibles.sleepUninterruptibly(micros, MICROSECONDS);
                    }
                }
            };
        }
    }

    public static void main(String[] args) {
        SleepingStopwatch watch = SleepingStopwatch.createFromSystemTimer();
        Scanner scanner = new Scanner(System.in);
        String str;
        while (!(str = scanner.next()).equalsIgnoreCase("exit")) {
            System.out.println("elapsed:" + watch.readMicros());
        }
    }
}
