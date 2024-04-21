package com.example.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync // 開啟多線程執行
@Async       // 指定類多線程執行 (指定方法的話 要小心測試)
@Slf4j
@Component
@EnableScheduling
public class TestSchedule {

    /*
        2024-02-02 17:17:00  INFO 13476 --- [         task-1] com.example.scheduler.TestSchedule       : Test-Start
        2024-02-02 17:17:05  INFO 13476 --- [         task-2] com.example.scheduler.TestSchedule       : Demo
        2024-02-02 17:18:00  INFO 13476 --- [         task-3] com.example.scheduler.TestSchedule       : Test-Start
        2024-02-02 17:18:05  INFO 13476 --- [         task-4] com.example.scheduler.TestSchedule       : Demo
        2024-02-02 17:18:10  INFO 13476 --- [         task-1] com.example.scheduler.TestSchedule       : Test-End
        2024-02-02 17:19:00  INFO 13476 --- [         task-5] com.example.scheduler.TestSchedule       : Test-Start
        2024-02-02 17:19:05  INFO 13476 --- [         task-6] com.example.scheduler.TestSchedule       : Demo
        2024-02-02 17:19:10  INFO 13476 --- [         task-3] com.example.scheduler.TestSchedule       : Test-End
    */

    @Scheduled(cron = "00 * * * * ?")
    public void test() throws InterruptedException {
        log.info("Test-Start");
        Thread.sleep(70 * 1000);
        log.info("Test-End");
    }

    @Scheduled(cron = "05 * * * * ?")
    public void demo() {
        log.info("Demo");
    }

}
