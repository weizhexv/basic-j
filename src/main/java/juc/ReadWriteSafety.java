package juc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteSafety {
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private volatile int num = 0;

    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.println("read num:" + num);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void write() {
        rwLock.writeLock().lock();
        try {
            num++;
            System.out.println("write num:" + num);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteSafety safety = new ReadWriteSafety();
        safety.read();
        ThreadFactory threadFactory = Executors.privilegedThreadFactory();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3, threadFactory);
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                safety.read();
            }
        }, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                safety.read();
            }
        }, 500, TimeUnit.MILLISECONDS);
        scheduler.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                safety.write();
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
