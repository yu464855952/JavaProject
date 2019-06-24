package com.thread.threadTest;

import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        //Callable采用ExecutorService的submit方法提交,返回的future对象可以取消任务
        Future<String> future = threadPool.submit(
            new Callable<String>() {
                @Override
                public String call() throws Exception{
                    Thread.sleep(200);
                    return "hello";
                };
            }
        );
        System.out.println("等待结果");

        try {
            System.out.println("拿到结果:" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("拿到结果:" + future.get(1,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        ExecutorService threadPoolOne = Executors.newFixedThreadPool(10);
        //用于提交一组Callable任务，其中take方法返回已完成的一个Callable任务对应的Future对象.
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPoolOne);
        for(int i=1;i<=10;i++){
            final int seq = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(10000));
                    return seq;
                }
            });
        }

        for (int i=1;i<=10;i++){
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
