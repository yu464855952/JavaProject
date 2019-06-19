package com.thread.threadTest;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimerTask1 extends TimerTask {

    @Override
    public void run() {

        System.out.println("bombing========");
        new Timer().schedule(new MyTimerTask2(),2000);
    }
}
