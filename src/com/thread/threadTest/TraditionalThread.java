package com.thread.threadTest;

public class TraditionalThread {

    public static void main(String[] args) {
        /**
         * 继承Thread类,重写run方法
         */
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1:"+Thread.currentThread().getName());
                    System.out.println("2:"+this.getName());
                }
            }
        };
        thread.start();
        /**
         * 实现runnable接口,重写run方法
         */
        Thread threadtest = new Thread(new Runnable() {
            /*
            * Thread.currentThread() ：当前线程对象
            * Runnable对象不是线程,是线程要运行的代码
            * */
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("3:"+Thread.currentThread().getName());
                }
            }
        }){};
        threadtest.start();

        /**
         * new Thread(runnable.run){run}.start()
         * 继承于Thread类(子类)的run方法会覆盖(thread类中runnable中的run方法);
         */
        Thread threadTest = new Thread(new Runnable() {
            /*
             * Thread.currentThread() ：当前线程对象
             * Runnable对象不是线程,是线程要运行的代码
             * */
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("4:"+Thread.currentThread().getName());
                }
            }
        }){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("5:"+Thread.currentThread().getName());
                }
            }

        };
        threadTest.start();

        Thread threadTest1 = new Thread();



    }
}
