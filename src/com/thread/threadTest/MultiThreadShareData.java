package com.thread.threadTest;

/**
 * 多个线程共享数据
 */
public class MultiThreadShareData {

    private  static ShareData shareDataOne  = new ShareData();

    public static void main(String[] args) {

//        每个线程执行的代码相同,使用同一个runnable对象,runnable对象中有共享数据
//        ShareData shareData = new ShareData();
//        new Thread(shareData).start();
//        new Thread(shareData).start();

//      每个线程执行的代码不同,需要不同的Runnable对象
        /**
         *  1.将共享数据封装在一个对象中,然后将这个对象逐一传递给各个Runnable对象,
         *  每个线程对共享数据的操作方法分配到那个对象身上完成。
         */

        ShareData shareDataTwo = new ShareData();
        new Thread(new MyRunnableOne(shareDataTwo)).start();
        new Thread(new MyRunnableTwo(shareDataTwo)).start();

        /**
         * 2.将Runnable对象作为某一个类的内部类,共享数据作为这个外部类中的成员变量.
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                shareDataOne.increment();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                shareDataOne.decrement();
            }
        }).start();
    }
}



class  ShareData {/*implements Runnable{
    private int count = 100;
    @Override
    public void run() {
        while (true){
          count--;
        }
    }*/
    private int j = 0;
    public synchronized void increment(){
        j++;
    }

    public synchronized void decrement(){
        j--;
    }
}

class  MyRunnableOne implements Runnable{
    private  ShareData data ;
    public MyRunnableOne(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {

    }
}

class  MyRunnableTwo implements Runnable{
    private  ShareData data ;
    public MyRunnableTwo(ShareData data){
        this.data = data;
    }
    @Override
    public void run() {

    }
}

