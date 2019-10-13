package com.ssq.sortsmethod;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author : Mr.Shen
 * Date : 2019/10/12 20:21
 * Description :
 */
public class ThreadPool implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is Running");
    }

    private static ThreadPool instance;

    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null) {
                    instance = new ThreadPool();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        int corePoolSize = 5;
        int maxPoolSize = 10;
        long keepAliveTime = 5;
        BlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(10);

        //拒绝策略1：将抛出 RejectedExecutionException.
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        //拒绝策略2：用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

        //拒绝策略3：
//        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardOldestPolicy();

        //拒绝策略4：
        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                keepAliveTime, TimeUnit.SECONDS, queue, handler);
        for (int i = 0; i < 100; i++) {
            executor.execute(new ThreadPool());
        }
        executor.shutdown();
    }


}
