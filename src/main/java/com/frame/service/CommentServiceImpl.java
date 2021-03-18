package com.frame.service;

import com.frame.utils.CallbackForController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @Override
    public int test(final CallbackForController callback) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int x = 3;
                try{

                    Thread.sleep(3000);
                    System.out.println("sleep done");
                }
                catch (Exception e){

                }
                finally {

                }
                callback.callback("成功");
            }
        };
        threadPool.execute(r);
        return 0;
    }
}
