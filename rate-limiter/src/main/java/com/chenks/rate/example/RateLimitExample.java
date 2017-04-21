package com.chenks.rate.example;

import com.chenks.rate.service.DataService;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/20
 * Version: since
 */
public class RateLimitExample {

    public static void main(String[] args) throws InterruptedException {
        int count = 8;
        ExecutorService service = Executors.newFixedThreadPool(count);
        DataService dataService = new DataService();
        dataService.startLogPrint();

        RateLimiter limiter = RateLimiter.create(10);
        //CustomLimiter limiter = new CustomLimiter(500);
        for (int i = 0; i < count; i++) {
            service.execute(new RequestRunner(dataService, limiter));
        }


        TimeUnit.SECONDS.sleep(2);
        service.shutdownNow();
        dataService.stopLogPrint();
        System.exit(0);
    }

    private static class RequestRunner implements Runnable {

        private DataService service;

        private RateLimiter limiter;

        private int count;

        public RequestRunner(DataService service, RateLimiter limiter) {
            this.service = service;
            this.limiter = limiter;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                limiter.acquire(1);
                service.service(Thread.currentThread().getName() + ":" + ++count);
            }
        }
    }
}
