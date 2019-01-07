package com.chris.esb.controller;

import com.chris.esb.rest.springboot.utils.CommonResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/test")
    public CommonResponse test() {
        List<Map<String, Object>> data = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Map<String, Object> item = Maps.newHashMap();
            item.put("name", "chris" + i);
            item.put("age", 10 + i);
            data.add(item);
        }
        return CommonResponse.ok().setData(data);
    }
}
