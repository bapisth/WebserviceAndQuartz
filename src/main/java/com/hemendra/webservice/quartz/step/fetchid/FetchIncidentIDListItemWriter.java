package com.hemendra.webservice.quartz.step.fetchid;

import com.hemendra.webservice.quartz.util.AppConstants;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by hp on 15-07-2017.
 */
public class FetchIncidentIDListItemWriter implements ItemWriter<String> {
	
	private StepExecution stepExecution;

	@Override
	public void write(List<? extends String> incidentIds) throws Exception {
		ExecutionContext stepContext = this.stepExecution.getExecutionContext();
        stepContext.put(AppConstants.INCIDENT_ID_FETCH_STEP_EXECUTION_KEY, incidentIds);
	}
	
	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
	
	
}
