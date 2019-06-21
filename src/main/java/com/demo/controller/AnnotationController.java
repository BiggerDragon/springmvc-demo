package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/mvc")
public class AnnotationController {

    @RequestMapping("/index.do")
    public String getIndex(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("getJson.do")
    public Map<String, String> getJsonData(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhu");
        map.put("age", "24");
        return map;
    }

    @RequestMapping(value = "upload.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestPart("file")MultipartFile multipartFile) throws IOException {
        String path = "D:\\workspace\\springmvc-demo\\file"+System.currentTimeMillis()+".txt";
        File file = new File(path);
        multipartFile.transferTo(file);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "success");
        return map;
    }


}
