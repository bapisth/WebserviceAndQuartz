package com.hemendra.webservice.quartz;

import com.hemendra.webservice.quartz.model.IncidentInstanceData;
import com.hemendra.webservice.quartz.step.fetchid.FetchIncidentIDListItemProcessor;
import com.hemendra.webservice.quartz.step.fetchid.FetchIncidentIDListItemReader;
import com.hemendra.webservice.quartz.step.fetchiddetail.FetchDetailInfoForIncidentIDItemProcessor;
import com.hemendra.webservice.quartz.step.fetchiddetail.FetchDetailInfoForIncidentIDItemReader;
import com.hemendra.webservice.quartz.step.fetchiddetail.FetchDetailInfoForIncidentIDItemWriter;
import com.hemendra.webservice.quartz.step.updateandpush.UpdateEMSAndPushToIMSIncidentDetailItemReader;
import com.hemendra.webservice.quartz.step.updateandpush.UpdateEMSAndPushToIMSIncidentDetailItemWriter;
import com.hemendra.webservice.quartz.util.AppConstants;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hemendra.webservice.quartz.step.fetchid.FetchIncidentIDListItemWriter;

/**
 * Created by hp on 15-07-2017.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FetchIncidentIDListItemReader customItemReader() {
        return new FetchIncidentIDListItemReader();
    }

    @Bean
    public FetchIncidentIDListItemWriter fetchIncidentIDListItemWriter() {
        return new FetchIncidentIDListItemWriter();
    }
    
    @Bean
    public FetchIncidentIDListItemProcessor fetchIncidentIDListItemProcessor() {
    	return new FetchIncidentIDListItemProcessor();
    }
    
    @Bean
    public FetchDetailInfoForIncidentIDItemReader fetchDetailInfoForIncidentIDItemReader() {
        return new FetchDetailInfoForIncidentIDItemReader();
    }

    @Bean
    public FetchDetailInfoForIncidentIDItemWriter fetchDetailInfoForIncidentIDItemWriter() {
        return new FetchDetailInfoForIncidentIDItemWriter();
    }
    
    @Bean
    public FetchDetailInfoForIncidentIDItemProcessor fetchDetailInfoForIncidentIDItemProcessor() {
    	return new FetchDetailInfoForIncidentIDItemProcessor();
    }
    
    @Bean
    public UpdateEMSAndPushToIMSIncidentDetailItemReader updateEMSAndPushToIMSIncidentDetailItemReader() {
    	return new UpdateEMSAndPushToIMSIncidentDetailItemReader();
    }
    
    @Bean
    public UpdateEMSAndPushToIMSIncidentDetailItemWriter updateEMSAndPushToIMSIncidentDetailItemWriter() {
    	return new UpdateEMSAndPushToIMSIncidentDetailItemWriter();
    }
    
    @Bean
    public ExecutionContextPromotionListener executionContextPromotionListener() {
    	ExecutionContextPromotionListener contextPromotionListener = new ExecutionContextPromotionListener();
    	contextPromotionListener.setKeys(new String[] {AppConstants.INCIDENT_ID_FETCH_STEP_EXECUTION_KEY});
    	return contextPromotionListener; 
    }
    
    @Bean
    public ExecutionContextPromotionListener executionFetchIncidentIDDetailContextPromotionListener() {
    	ExecutionContextPromotionListener contextPromotionListener = new ExecutionContextPromotionListener();
    	contextPromotionListener.setKeys(new String[] {AppConstants.INCIDENT_ID_DETAIL_FETCH_STEP_EXECUTION_KEY});
    	return contextPromotionListener; 
    }


    //First step get all the Incident Ids
    @Bean
    public Step fetchIncidentIDs() {
        return stepBuilderFactory.get("fetchIncidentIDs")
        		.<String, String>chunk(10)
        		.reader(customItemReader())
        		.writer(fetchIncidentIDListItemWriter())
        		.listener(executionContextPromotionListener())
                .build();
    }
    
    //Get all the data corresponding to IncidentIds from Step1
    @Bean
    public Step fetchDetailOfIncidentID() {
        return stepBuilderFactory.get("fetchDetailOfIncidentID")
        		.<IncidentInstanceData, IncidentInstanceData>chunk(10)
        		.reader(fetchDetailInfoForIncidentIDItemReader())
        		.writer(fetchDetailInfoForIncidentIDItemWriter())
        		.listener(executionFetchIncidentIDDetailContextPromotionListener())
                .build();
    }
    
  //Get all the data corresponding to IncidentIds from Step1
    @Bean
    public Step updateEMSAndPushDataToIMS() {
        return stepBuilderFactory.get("updateEMSAndPushDataToIMS")
        		.<IncidentInstanceData, IncidentInstanceData>chunk(10)
        		.reader(updateEMSAndPushToIMSIncidentDetailItemReader())
        		.writer(updateEMSAndPushToIMSIncidentDetailItemWriter())
                .build();
    }

    @Bean
    public Job testJob() {
        return jobBuilderFactory.get(AppConstants.EMS_INTEGRATION_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(fetchIncidentIDs())
                .next(fetchDetailOfIncidentID())
                .next(updateEMSAndPushDataToIMS())
                .end()
                .build();
    }


}
