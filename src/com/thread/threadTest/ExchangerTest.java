package com.thread.threadTest;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger
 * 例子：用于实现两个人之间的数据交换，每个人在完成一定的事务后想于对方交换数据,
 * 第一个先拿出数据的人将一直等待第二个人拿着数据到来时，才能批次交换数据.
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Exchanger exchanger = new Exchanger();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = "xxx";
                    System.out.println("线程"+Thread.currentThread().getName()+"正在把数据"+data+ "换出去");
                    Thread.sleep((long) (Math.random()*10000));
                    String dataOne = (String) exchanger.exchange(data);
                    System.out.println("线程"+ Thread.currentThread().getName()+"换回的数据为"+dataOne);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String dataTwo = "AAA";
                    System.out.println("线程"+Thread.currentThread().getName()+"正在把数据"+dataTwo+ "换出去");
                    Thread.sleep((long) (Math.random()*10000));
                    String dataThree = (String) exchanger.exchange(dataTwo);
                    System.out.println("线程"+ Thread.currentThread().getName()+"换回的数据为"+dataThree);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
