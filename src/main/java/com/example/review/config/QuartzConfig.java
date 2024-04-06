package com.example.review.config;

import com.example.review.jobs.GetPlaceDetailsJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail getPlaceDetail() {
        return JobBuilder.newJob(GetPlaceDetailsJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger getPlaceDetailTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInHours(24)
                .withIntervalInSeconds(3)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(getPlaceDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }
}
