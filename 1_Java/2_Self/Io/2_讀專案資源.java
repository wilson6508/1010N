package com.hmdp;

import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

class MyTest {

    @Test
    void test() throws IOException {
        ClassPathResource resource = new ClassPathResource("unlock.lua");
        String content = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        System.out.println("content = " + content);
    }

}
