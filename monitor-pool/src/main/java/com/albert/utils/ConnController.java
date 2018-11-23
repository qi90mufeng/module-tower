package com.albert.controller;

import com.albert.pool.RedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CountDownLatch;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑       永无BUG     永不修改                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @author fujin
 * @version v 0.1
 * @date 2018-11-23
 */
@RestController
public class ConnController {

    @Autowired
    private RedisPool redisPool;

    private CountDownLatch countDownLatch = new CountDownLatch(15);

    @GetMapping(value = "getConn")
    public long getConn(@RequestParam("times") int times){
        System.out.println(Thread.currentThread().getName() + "请求连接,次数为" + times);
        long count = 0;

        countDownLatch.countDown();

        new Thread(() -> {
            try {
                countDownLatch.await();
                Jedis jedis = redisPool.getRedis();
                for (int i = 0; i < times; i++){
                    jedis.incr("count");
                }
                redisPool.release(jedis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return count;
    }
}
