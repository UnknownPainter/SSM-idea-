package com.frame.controller;

import com.frame.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@Controller
@ResponseBody
public class SearchController {
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/hot/tag",method = RequestMethod.GET)
    DeferredResult<List<String>> getHotTag(){
        final DeferredResult<List<String>> deferredResult = new DeferredResult<>();
        threadPool.execute(()->{
            deferredResult.setResult(searchService.getHotTag());
        });
        return deferredResult;
    }

}
