package com.thread.threadTest;

public class TraditionalThreadSynchronized {


    public static void main(String[] args) {
        new TraditionalThreadSynchronized().init();
    }

    private void init(){
        final Outputer outputer = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("abcdefghijklmn");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output3("opqrstuvwxyz");
                }
            }
        }).start();

    }

    static class Outputer{

        public void output(String name){
            int len = name.length();
            synchronized (Outputer.class){ //锁定是必须是同一个对象
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

        public synchronized void output1(String name){
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();

        }

        public static synchronized void output3(String name){
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }


    }

}
