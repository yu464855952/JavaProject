package com.thread.threadTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列
 */
public class BlockingQueueTest {

    public static void main(String[] args) {
        final BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        for (int i=1;i<=2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep((long) (Math.random()*10000));
                            System.out.println(Thread.currentThread().getName()+"准备放数据");
                            blockingQueue.put(1);
                            System.out.println(Thread.currentThread().getName()+"已经放了数据,队列目前有"+blockingQueue.size()+"个数据");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep((long) (Math.random()*10000));
                            System.out.println(Thread.currentThread().getName()+"准备取数据");
                            blockingQueue.take();
                            System.out.println(Thread.currentThread().getName()+"已经取了数据,队列目前有"+blockingQueue.size()+"个数据");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
