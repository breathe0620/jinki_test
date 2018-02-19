package kr.mubeat.cms.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by moonkyu.lee on 2017. 7. 19..
 */
@Component
public class DataScrapSchedule {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0 04 * * ?")
    private void dataScrap() {

    }


}
