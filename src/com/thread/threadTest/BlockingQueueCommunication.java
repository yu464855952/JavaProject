package com.thread.threadTest;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现同步通知的功能
 *
 */
public class BlockingQueueCommunication {

    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<50;i++) {
                    business.sub(i);
                }
            }
        }).start();


        for (int i=0;i<50;i++) {
            business.main(i);
        }
    }

    static class Business{

        BlockingQueue<Integer> blockingQueueOne = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> blockingQueueTwo = new ArrayBlockingQueue<Integer>(1);

        //匿名构造方法,运行于在任何构造方法之前,创建几个对象就会调用几次
        {
            try {
                blockingQueueTwo.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //只有put和take方法才具有阻塞功能
        public void sub(int i){
            try {
                blockingQueueOne.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j=1; j<=10; j++) {
                System.out.println("子线程第"+i+"次循环,循环第"+j+"次");
            }
            try {
                blockingQueueTwo.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void main(int i){
            try {
                blockingQueueTwo.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j=1;j<=100;j++){
                System.out.println("主线程第" + i + "次循环,循环第" + j + "次");
            }
            try {
                blockingQueueOne.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
