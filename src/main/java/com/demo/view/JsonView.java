package com.demo.view;

import java.util.Map;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonView extends MappingJackson2JsonView{
    protected Object filterModel(Map<String, Object> model) {
        System.out.println("JsonView...");
        Map<String, Object> result = model;
        if (!result.containsKey("ReturnCode")) {
            result.put("ReturnCode", "000000");
        }
        return result;
    }
}