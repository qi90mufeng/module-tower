package com.albert.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/task")
public class TaskController {

    /**
     * 查询任务
     */
    @GetMapping(value = "/queryTask")
    public void queryTask(){

    }

    /**
     * 新增任务
     */
    @PostMapping(value = "/addTask")
    public void addTask(){

    }

    /**
     * 分配任务
     */
    @GetMapping(value = "/assignTask")
    public void assignTask(){

    }
}
