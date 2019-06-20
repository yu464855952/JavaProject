package com.thread.threadTest;


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步工具类
 * 1.CyclicBarrier：多个线程在不同时刻到达指定集合点后,接着去执行别的任务.
 *   到达指定集合点用CyclicBarrier对象的await方法表示。
 *
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for(int i=0;i<3;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点一,当前已有"+(cyclicBarrier.getNumberWaiting()+1)+"个已经到达");
                        cyclicBarrier.await();
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点二,当前已有"+(cyclicBarrier.getNumberWaiting()+1)+"个已经到达");
                        cyclicBarrier.await();
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"即将到达集合地点三,当前已有"+(cyclicBarrier.getNumberWaiting()+1)+"个已经到达");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
