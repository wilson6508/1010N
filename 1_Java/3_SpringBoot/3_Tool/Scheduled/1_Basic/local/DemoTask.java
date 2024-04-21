package com.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class DemoTask {

    @Scheduled(cron = "0/5 * * * * ?")
    public void demoTask() {
        System.out.println(777);
    }

}
