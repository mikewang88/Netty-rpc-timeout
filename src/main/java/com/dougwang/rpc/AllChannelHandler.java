package com.dougwang.rpc;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: MikeWang
 * @Date: 2020/6/8 5:39 PM
 * @Description: step 1
 *
 */
public class AllChannelHandler {
    //使用JDK 的threadpoolexecutor 线程池创建已个线程池
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8,8,1, TimeUnit.MINUTES,
    new SynchronousQueue<>(),new ThreadPoolExecutor.CallerRunsPolicy());

    // 异步执行任务
    public static void channelRead(Runnable r){
        executor.execute(r);
    }

    //关闭线程池
    public static void shutdown(){
        executor.shutdown();
    }

}
