package com.controller;

import com.pojo.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class DownloadController {

    @CrossOrigin(origins = {"http://localhost:8080", "https://xxxx.com.tw:8080"})
    @GetMapping("/download/schedule/task")
    public void scheduleDownLoad(@RequestParam String dir, @RequestParam String file, HttpServletResponse response) throws IOException {
        String url = String.format("http://192.168.184.102/scheduledownload/%s/%s", dir, file);
        System.out.println(url);
        response.sendRedirect(url);
    }

}
