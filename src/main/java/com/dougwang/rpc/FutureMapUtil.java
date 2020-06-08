package com.dougwang.rpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: MikeWang
 * @Date: 2020/6/8 5:43 PM
 * @Description: 请求对应的future
 */
public class FutureMapUtil {
    // <请求id，对应的future>
    private static final ConcurrentHashMap<String, CompletableFuture> futureMap = new ConcurrentHashMap<String, CompletableFuture>();

    public static void put(String id, CompletableFuture future) {
        futureMap.put(id, future);
    }

    public static CompletableFuture<String> get(String id) {
       return futureMap.get(id);
    }

    public static CompletableFuture remove(String id) {
        return futureMap.remove(id);
    }
}
