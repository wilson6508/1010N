package com.controller;

import com.pojo.entity.SearchLogEntity;
import com.service.SearchLogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
public class SearchLogController {

    @CrossOrigin(origins = {"http://localhost:8080", "https://xxxx.tw:8080"})
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @Resource
    private SearchLogService searchLogService;

    @PostMapping("/createLog")
    public boolean createLog(@RequestBody SearchLogEntity entity) {
        searchLogService.createLog(entity);
        return true;
    }

    @PostMapping("/upload/blog")
    public String uploadImage(@RequestParam("file") MultipartFile image) {
        try {
        String originalFilename = image.getOriginalFilename();
        String fileName = "test123.png";
        image.transferTo(new File("D:\\code\\", fileName));
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
        return "OK";
    }

}
