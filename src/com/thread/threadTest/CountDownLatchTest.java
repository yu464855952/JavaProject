package com.thread.threadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 同步工具类
 * CountDownLatch:犹如倒计时计数器,调用ContDownLatch对象的countDown方法就将计数器减一,
 * 当计数器到达0时,则所有等待者或者单个等待者开始执行。
 *
 * 例子：运动员百米赛跑.裁判哨响,线程执行,执行完毕,得到结果
 *
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i=1;i<=3;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()+"正准备接受命令");
                        cdOrder.await();
                        System.out.println("线程"+Thread.currentThread().getName()+"已接受命令");
                        Thread.sleep((long) (Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()+"回应命令处理结果");
                        cdAnswer.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };
            executorService.execute(runnable);
        }
        try{
            Thread.sleep((long) (Math.random()*10000));
            System.out.println("线程"+Thread.currentThread().getName()+"即将发布命令");
            cdOrder.countDown();
            System.out.println("线程"+Thread.currentThread().getName()+"已发送命令，正在等待结果");
            cdAnswer.await();
            System.out.println("线程"+Thread.currentThread().getName()+"已收到所有响应结果");
        }catch (Exception e){
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
