package com.dougwang.rpc;

import com.dougwang.timingwheel.TimeOutThread;
import com.dougwang.timingwheel.Timer;
import com.dougwang.timingwheel.TimerTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: MikeWang
 * @Date: 2020/6/8 5:51 PM
 * @Description:
 */
public class CallTest {
    private static final RpcClient rpcClient = new RpcClient();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        // 1.同步调用
//        System.out.println(rpcClient.rpcSyncCall("who are you"));

        // 2.发起远程调用异步，并注册回调，马上返回
        CompletableFuture<String> future = rpcClient.rpcAsyncCall("who are you");

        future.whenComplete((v, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(v);
            }

        });

        System.out.println("---async rpc call over");


    }
}

