package com.thread.threadTest;


/**
 * 两个线程之间的互斥和相互通信
 * 要用到共同数据（包括同步锁)或共同算法的若干方法应该贵在同一个类身上
 */
public class TraditionalThreadCommunication {

    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<50;i++) {
//                    synchronized (TraditionalThreadCommunication.class){
//                        for (int j=1; j<=10; j++) {
//                            System.out.println("子线程第"+i+"次循环,循环第"+j+"次");
//                        }
//                    }
                    business.sub(i);
                }
            }
        }).start();


        for (int i=0;i<50;i++) {
//            synchronized (TraditionalThreadCommunication.class) {
//                for (int j = 0; j < 10; j++) {
//                    System.out.println("主线程第" + i + "次循环,循环第" + j + "次");
//                }
//            }
            business.main(i);
        }
    }

    static class Business{
        private boolean bShouldSub = true ;
        public synchronized void sub(int i){
            while (!bShouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j=1; j<=10; j++) {
                System.out.println("子线程第"+i+"次循环,循环第"+j+"次");
            }
            bShouldSub = false;
            this.notify();
        }

        public synchronized void main(int i){
            while (bShouldSub){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int j=1;j<=100;j++){
                System.out.println("主线程第" + i + "次循环,循环第" + j + "次");
            }
            bShouldSub = true;
            this.notify();
        }
    }
}
