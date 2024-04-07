package com.example.review.config;

import com.example.review.jobs.SearchPlaceJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail getPlaceDetail() {
        return JobBuilder.newJob(SearchPlaceJob.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger getPlaceDetailTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(1)
                .withRepeatCount(1);

        return TriggerBuilder.newTrigger()
                .forJob(getPlaceDetail())
                .withSchedule(scheduleBuilder)
                .build();
    }
}
