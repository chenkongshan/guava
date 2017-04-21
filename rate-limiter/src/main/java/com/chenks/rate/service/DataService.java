package com.chenks.rate.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.chenks.rate.util.PrintUtil.printGreen;
import static com.chenks.rate.util.PrintUtil.printRed;


/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/20
 * Version: since
 */
public class DataService {

    //日志打印队列
    private static final PriorityBlockingQueue<Log> logQueue = new PriorityBlockingQueue<Log>();

    private Thread logPrint;

    public void startLogPrint() {
        logPrint = new Thread(new LogPrintRunner());
        logPrint.start();
    }

    public void stopLogPrint() {
        while (!logQueue.isEmpty()) {

        }
        logPrint.interrupt();
    }

    public void service(String msg) {
        logQueue.put(new Log(new Date(), msg));
    }

    private static class Log implements Comparable<Log> {
        public final Date date;
        public final String msg;

        public Log(Date date, String msg) {
            this.date = date;
            this.msg = msg;
        }

        @Override
        public int compareTo(Log o) {
            return date.before(o.date) ? -1 : (date.after(o.date) ? 1 : 0);
        }
    }

    private static class LogPrintRunner implements Runnable {

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void run() {
            int startSec = 0;
            int count = 0;
            while (!Thread.interrupted()) {
                try {
                    Log log = logQueue.take();
                    Calendar c = Calendar.getInstance();
                    c.setTime(log.date);
                    String time = sdf.format(log.date);
                    int sec = c.get(Calendar.SECOND);
                    if (startSec == sec)
                        count++;
                    else {
                        startSec = sec;
                        count = 0;
                        count++;
                    }
                    //秒为奇数，则打印红色，否则打印绿色
                    if (sec % 2 != 0) {
                        printRed(count + "|", time, "==>", log.msg);
                    } else {
                        printGreen(count + "|", time, "==>", log.msg);
                    }
                } catch (InterruptedException e) {
                    //System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            TimeUnit.MILLISECONDS.sleep(50);
            logQueue.put(new Log(new Date(), "test" + i));
            System.out.println("put....");
        }
        Thread t1 = new Thread(new LogPrintRunner());
        t1.start();
        t1.join();
    }
}
