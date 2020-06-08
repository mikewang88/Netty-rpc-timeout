package com.dougwang.timingwheel;

import com.dougwang.rpc.FutureMapUtil;

import java.util.concurrent.CompletableFuture;

/**
 * @Author: MikeWang
 * @Date: 2020/6/8 6:42 PM
 * @Description:
 */
public class TimeOutThread implements Runnable{
    // 2.创建消息id
    String reqId;

    public TimeOutThread(String reqId){
        this.reqId = reqId;
    }

    @Override
    public void run() {
        CompletableFuture<String> future = FutureMapUtil.get(reqId);
        if (!future.isDone()){
            future.complete("超时了！！！");
        }else {
            System.out.println("任务已经完成了！！！");
        }
    }
}
