package com.thread.threadTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号灯Semaphore可以维护当前访问自身的线程个数,并提供了同步机制
 * 使用Semaphore可以控制同时访问资源的线程个数。例如：实现一个文件允许的并发访问数。
 *
 * 单个信号量的Semaphore对象可以实现互斥锁的功能。
 * 并且可以是有一个线程获得了"锁"，再由另一个线程释放"锁"，
 * 可应用于死锁恢复的一些场合。
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i=0;i<10;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"进入,当前已有"+(3-semaphore.availablePermits())+"个并发");

                    try {
                        Thread.sleep((long)(Math.random()*10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程"+Thread.currentThread().getName()+"即将离开");
                    semaphore.release();
                    //下面代码有时候执行不准确,因为其没有和上面的代码合成原子单元
                    System.out.println("线程"+Thread.currentThread().getName()+"已离开,当前已有"+(3-semaphore.availablePermits())+"个并发");

                }
            };
            executorService.execute(runnable);
        }
    }
}
