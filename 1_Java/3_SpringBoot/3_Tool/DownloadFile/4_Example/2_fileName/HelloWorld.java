package com.eland.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class HelloWorld {

    @RequestMapping(value = "/helloWorld")
    public void insightApiRequest(HttpServletResponse response) {
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream("/static/eee.pdf");
            if (inputStream != null) {
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                // 暴露header中的Content-Disposition 不然前端獲取不到
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                response.setHeader("Content-Disposition", "attachment;filename=true");
                os = response.getOutputStream();
                bis = new BufferedInputStream(inputStream);
                while (bis.read(buffer) != -1) {
                    os.write(buffer);
                }
            }
        } catch (Exception e) {
			response.setHeader("Content-Disposition", "false");
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}