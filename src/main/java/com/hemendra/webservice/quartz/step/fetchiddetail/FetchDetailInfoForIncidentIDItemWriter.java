package com.hemendra.webservice.quartz.step.fetchiddetail;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import com.hemendra.webservice.quartz.model.IncidentInstanceData;
import com.hemendra.webservice.quartz.util.AppConstants;

public class FetchDetailInfoForIncidentIDItemWriter implements ItemWriter<IncidentInstanceData> {
	private StepExecution stepExecution;

	@Override
	public void write(List<? extends IncidentInstanceData> incidentInstanceDataList) throws Exception {
		// TODO Auto-generated method stub
		incidentInstanceDataList.forEach(data-> {
			System.out.println(data);
		});
		
		ExecutionContext stepContext = this.stepExecution.getExecutionContext();
        stepContext.put(AppConstants.INCIDENT_ID_DETAIL_FETCH_STEP_EXECUTION_KEY, incidentInstanceDataList);
	}
	
	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

}
