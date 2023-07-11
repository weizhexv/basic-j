package juc;

import java.util.concurrent.locks.StampedLock;

public class StampedReadWrite {
    private volatile int sum = 0;
    private final StampedLock LOCK = new StampedLock();

    public void write() {
        long stamp = LOCK.writeLock();
        try {
            sum++;
        } finally {
            LOCK.unlockWrite(stamp);
        }
    }

    public int read() {
        long stamp = LOCK.tryOptimisticRead();
        int val = sum;
        if (!LOCK.validate(stamp)) {
            stamp = LOCK.readLock();
            try {
                val = sum;
            } finally {
                LOCK.unlockRead(stamp);
            }
        }
        return val;
    }
}
