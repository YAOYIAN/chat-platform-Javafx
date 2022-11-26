package com.ql.entity.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HandlerSocketServerPool {
    private final ExecutorService executorService;

    public HandlerSocketServerPool(int maxThreadNum, int queueSize){
        executorService = new ThreadPoolExecutor(80,maxThreadNum,100,
                TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable target){
        executorService.execute(target);
    }
}
