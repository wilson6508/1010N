package com.example.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
public class TestSchedule {

    /*
        2024-02-02 17:06:00  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Test-Start
        2024-02-02 17:07:10  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Test-End
        2024-02-02 17:07:10  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Demo
        2024-02-02 17:08:00  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Test-Start
        2024-02-02 17:09:10  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Test-End
        2024-02-02 17:09:10  INFO 12616 --- [   scheduling-1] com.example.scheduler.TestSchedule       : Demo

        自行解讀:
        排程時間觸發 --> 執行完畢後   --> 等待下次排程觸發時間
        只有一條線程 --> test()阻塞後 --> demo()排隊要執行
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
