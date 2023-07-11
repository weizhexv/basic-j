package juc;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Safety {
    private static final ReentrantLock LOCK = new ReentrantLock(true);
    private volatile static int num = 0;

    public static void access() {
        LOCK.lock();
        try {
            num++;
            System.out.println(Thread.currentThread().getName() + " update num:" + num);
            Thread.sleep(2000L);
        } catch (Throwable th) {
            System.out.println("err:" + th.getMessage());
        } finally {
            LOCK.unlock();
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            access();
        }
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        Thread thread = new Thread(new Task(), "runner-thread-1");

        ThreadFactory factory = Executors.defaultThreadFactory();
        Thread defaultThread = factory.newThread(new Task());

        thread.start();
        defaultThread.start();

        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(2, 4, 20, TimeUnit.SECONDS, blockingQueue);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Task());
        }
        threadPoolExecutor.shutdown();
    }

}




