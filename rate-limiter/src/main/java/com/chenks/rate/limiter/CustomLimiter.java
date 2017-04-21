package com.chenks.rate.limiter;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Company: PAJK
 * Author: chenkongshan
 * Created: 2017/4/20
 * Version: since
 */
public class CustomLimiter {

    private Sync sync = new Sync();

    private ScheduledThreadPoolExecutor executor;

    public CustomLimiter(int permit) {
        //初始化定时执行器
        executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sync.setToken(permit);
                sync.releaseShared(1);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void acquire(int permit) {
        sync.acquireShared(permit);
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        public void setToken(int token) {
            setState(token);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int state = getState();
                int current = state - arg;
                if (current < 0 || compareAndSetState(state, current)) {
                    return current;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            return true;
        }
    }
}
