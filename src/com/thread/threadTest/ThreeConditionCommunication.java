package com.thread.threadTest;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程来回切换执行
 */
public class ThreeConditionCommunication {

    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<50;i++) {
                    business.subTwo(i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<50;i++) {
                    business.subThree(i);
                }
            }
        }).start();


        for (int i=0;i<50;i++) {
            business.main(i);
        }
    }

    static class Business{
        Lock lock = new ReentrantLock();
        Condition conditionone = lock.newCondition();
        Condition conditiontwo = lock.newCondition();
        Condition conditionthree = lock.newCondition();


        private int bShouldSub = 1 ;
        public  void subTwo(int i){
            lock.lock();
            try{
                while (bShouldSub != 2){
                    try {
                        conditiontwo.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1; j<=10; j++) {
                    System.out.println("子线程2第"+i+"次循环,循环第"+j+"次");
                }
                bShouldSub = 3;
                conditionthree.signal();
            }finally {
                lock.unlock();
            }

        }

        public  void subThree(int i){
            lock.lock();
            try{
                while (bShouldSub!=3){
                    try {
                        conditionthree.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1; j<=10; j++) {
                    System.out.println("子线程3第"+i+"次循环,循环第"+j+"次");
                }
                bShouldSub = 1;
                conditionone.signal();
            }finally {
                lock.unlock();
            }

        }

        public  void main(int i){
            lock.lock();
            try{
                while (bShouldSub!=1){
                    try {
                        conditionone.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1;j<=10;j++){
                    System.out.println("主线程第" + i + "次循环,循环第" + j + "次");
                }
                bShouldSub = 2;
                conditiontwo.signal();
            }finally {
                lock.unlock();
            }
        }
    }
}
