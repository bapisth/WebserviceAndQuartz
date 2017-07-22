package com.hemendra.webservice.quartz.quartz;


import com.hemendra.webservice.quartz.util.AppConstants;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 16-07-2017.
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobLocator jobLocator;
    
    @Value("${ims.job.cron.expression}")
    private String cronExpression;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor =
                new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);

        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(QuartzJobLauncher.class);
        Map<String, Object> map = new HashMap<>();
        map.put("jobName", AppConstants.EMS_INTEGRATION_JOB_NAME);
        map.put("jobLauncher", jobLauncher);
        map.put("jobLocator", jobLocator);
        jobDetailFactoryBean.setJobDataAsMap(map);
        return jobDetailFactoryBean;

    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression(cronExpression);//every 5 seconds
        return cronTriggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean().getObject());
        return schedulerFactoryBean;
    }
}
