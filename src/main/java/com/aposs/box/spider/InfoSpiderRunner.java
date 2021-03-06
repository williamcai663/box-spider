package com.aposs.box.spider;

import com.aposs.box.spider.service.InfoSpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InfoSpiderRunner implements ApplicationRunner {

    @Autowired
    private InfoSpiderService infoSpiderService;

    private static Logger logger = LoggerFactory.getLogger(InfoSpiderRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        runSpider();
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void processInfoSpider(){
        runSpider();
    }

    private void runSpider(){
        long startTime = System.currentTimeMillis();
        logger.info("begin the info spider");
        infoSpiderService.runInfoSpider();
        logger.info("end the info spider,it cost " + (System.currentTimeMillis() - startTime));
    }


}
