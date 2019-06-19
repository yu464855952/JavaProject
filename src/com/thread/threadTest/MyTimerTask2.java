package com.thread.threadTest;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask2 extends TimerTask {

    @Override
    public void run() {

        System.out.println("bombing========");
        new Timer().schedule(new MyTimerTask1(),4000);
    }
}
