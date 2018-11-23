package com.albert.pool;

import redis.clients.jedis.Jedis;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
public class RedisPoolImpl implements RedisPool{

    private final int maxSize;
    private final int minSize;
    private final int waitTime;

    private AtomicInteger activeSize = new AtomicInteger(0);

    //多线程环境编码，需要多线程安全的链式阻塞对象
    private LinkedBlockingQueue<Jedis> idle; //空闲对象
    private LinkedBlockingQueue<Jedis> busy; //繁忙对象

    public RedisPoolImpl(int maxSize, int minSize, int waitTime){
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.waitTime = waitTime;
    }

    //初始化
    @Override
    public void init(){
        idle = new LinkedBlockingQueue();
        busy = new LinkedBlockingQueue();
        for (int i = 0; i < minSize; i++){
            idle.offer(RedisUtil.createJedis());
            activeSize.incrementAndGet();
        }
    }

    //销毁
    @Override
    public void destroy(){
        idle = null;
        busy = null;
        activeSize = null;
    }

    //取连接
    @Override
    public Jedis getRedis(){
        //1、有空闲连接，则返回一个
        Jedis jedis = idle.poll();
        if (null != jedis){
            busy.offer(jedis);
            System.out.println(Thread.currentThread().getName() + "获取了空闲jedis:" + jedis);
            return jedis;
        }
        //2、没有空闲连接，未达到上限，创建一个返回
        if (activeSize.get() < this.maxSize){
            int size = activeSize.incrementAndGet();
            if (size <= this.maxSize){
                jedis = RedisUtil.createJedis();
                busy.offer(jedis);
                System.out.println(Thread.currentThread().getName() + "创建并获取了jedis:" + jedis);
                return jedis;
            }
        }
        //3、没有空闲连接，已达到上限，等待其他归还, 等待10s
        try {
            System.out.println(Thread.currentThread().getName() + "排队等待......");
            jedis = idle.poll(10000, TimeUnit.MILLISECONDS);
            if (null == jedis){
                throw new RuntimeException("Busy now");
            }
            System.out.println(Thread.currentThread().getName() + "得到连接jedis:" + jedis);
            return jedis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //是否连接
    @Override
    public void release(Jedis jedis){
        busy.remove(jedis);
        idle.offer(jedis);
        System.out.println(Thread.currentThread().getName() + "释放了连接jedis:" + jedis);
    }

    /**
     * 1、连接池，一般3个就够了 当高峰过去，回缩连接数为3个
     * 2、守护线程， 检测里面的连接数，是不是健康的对象，及时移除，建立新的顶上
     *      有些开发技术不行，手写了jedis.close();
     * 3、
     */

}
