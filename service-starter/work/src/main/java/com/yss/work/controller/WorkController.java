package com.yss.work.controller;

import com.common.base.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/work")
public class WorkController extends BaseController {


    /**
     * 测试接口
     */
    @GetMapping("/test")
    public Object rs(){
        Map<String,Object> map=new HashMap<>();
        map.put("test",100);
        return map;
    }



}
