package com.thread.threadTest;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {

//        ExecutorService threadPool = Executors.newFixedThreadPool(3);//固定大小的线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool();//缓存线程池
        ExecutorService threadPool = Executors.newSingleThreadExecutor();//单一线程池

        for (int i=1;i<=10;i++){
            final int task = i;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j=1;j<=10;j++){
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"is looping of " + j +"for task of " + task);
                    }
                }
            });
        }
        System.out.println(" all of 10 tasks have committed! ");
        threadPool.shutdownNow();//停止所有正在执行的线程
        threadPool.shutdown();//线程池中的任务全部已经处理完成，才会退出

        //10秒之后执行
        Executors.newScheduledThreadPool(3).schedule(
                new Runnable() {
            @Override
            public void run() {
                System.out.println("bombing!");
            }
        },10,TimeUnit.SECONDS);

        //10秒之后执行，以后每隔2秒执行
        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("bombing!");
                    }
                },10,2 , TimeUnit.SECONDS);
    }

}
