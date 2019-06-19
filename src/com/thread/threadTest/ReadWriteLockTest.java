package com.thread.threadTest;


import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        final QueueData queue = new QueueData();
        for (int i=1;i<=3;i++){
            new Thread(){
                public void run(){
                    while (true){
                        queue.get();
                    }
                }
            }.start();

            new Thread(){
                public void run(){
                    while (true){
                        queue.put(new Random().nextInt(10000));
                    }
                }
            }.start();
        }
    }

    static class QueueData{
        private Object data = null; //共享数据,只能有一个线程能写该数据,但可以有多个线程同时读该数据。
        ReadWriteLock rwl = new ReentrantReadWriteLock();
        public void get(){
            rwl.readLock().lock();
            System.out.println(Thread.currentThread().getName()+ "be ready to read data");
            try {
                Thread.sleep((long) Math.random()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "have  read data : " + data);
            rwl.readLock().unlock();
        }

        public void put(Object data){
            rwl.writeLock().lock();
            System.out.println(Thread.currentThread().getName()+ "be ready to write data");
            try {
                Thread.sleep((long) Math.random()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = data;
            System.out.println(Thread.currentThread().getName()+ "have write data : " + data);
            rwl.writeLock().unlock();
        }
    }
}
