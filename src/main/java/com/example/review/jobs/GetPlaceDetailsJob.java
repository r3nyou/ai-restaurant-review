package com.example.review.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class GetPlaceDetailsJob extends QuartzJobBean {
    private static final Logger LOG = LoggerFactory.getLogger(GetPlaceDetailsJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("GetPlaceDetailsJob...");
    }
}
