package com.thread.threadTest;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程之间的互斥和相互通信
 * 在等待Condition时,允许发生"虚假唤醒",这通常作为对基础平台语义的让步.对于大多数
 * 应用程序,这带来的实际影响很小,因为Condition应该总是在一个循环中被等待,并测试正被等待的状态声明,
 * 某个实现可以随意移除可能的虚假唤醒，但建议总是假定这些虚假唤醒可能发生,因此总在一个循环中等待.
 *
 * 一个锁内部可以有多个Condition,即有多路等待和通知,可以参考jdk中提供Lock和Condition实现的
 * 可阻赛队列的案例(BoundedBuffer)，在传统的线程机制中 一个监视器对象只能有一路等待和通知,
 * 要想实现多路等待和通知,必须嵌套使用多个同步监视器对象.(如果只用一个Condition,两个放的都在等,一旦一个放的进去路,
 * 那么它通知可能会导致另一个放接着往下走)
 *
 */
public class ConditionCommunication {

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
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private boolean bShouldSub = true ;
        public  void sub(int i){
            lock.lock();
            try{
                while (!bShouldSub){
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1; j<=10; j++) {
                    System.out.println(Thread.currentThread().getName()+"子线程第"+i+"次循环,循环第"+j+"次");
                }
                bShouldSub = false;
               condition.signal();
            }finally {
                lock.unlock();
            }

        }

        public  void main(int i){
            lock.lock();
            try{
                while (bShouldSub){
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j=1;j<=100;j++){
                    System.out.println(Thread.currentThread().getName()+"主线程第" + i + "次循环,循环第" + j + "次");
                }
                bShouldSub = true;
                condition.signal();
            }finally {
                lock.unlock();
            }
        }
    }
}
