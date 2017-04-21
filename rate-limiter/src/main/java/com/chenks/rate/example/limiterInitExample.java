package com.chenks.rate.example;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/21
 * Version: since
 */
public class limiterInitExample {

    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(5);
        //TimeUnit.MILLISECONDS.sleep(500);
        limiter.acquire(15);
        limiter.acquire(1);
        /*for(int i = 0; i < 3; i++) {
            limiter.acquire(1);
            System.out.println("========================================================");
        }*/
        //System.out.println(33.0 / 33.333333333333336);
        //System.out.println(Long.MAX_VALUE / 1000 / 1000 / 60 / 60 / 24 / 365);
    }
}
