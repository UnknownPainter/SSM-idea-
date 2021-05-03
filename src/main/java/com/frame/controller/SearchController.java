package com.frame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

@Controller
public class SearchController {
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

}
