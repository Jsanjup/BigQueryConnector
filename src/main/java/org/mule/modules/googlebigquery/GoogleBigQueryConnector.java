package org.mule.modules.googlebigquery;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.modules.googlebigquery.config.ConnectorConfig;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableDataInsertAllRequest;
import com.google.api.services.bigquery.model.TableDataInsertAllResponse;
import com.google.api.services.bigquery.model.TableRow;

@Connector(name="google-big-query", friendlyName="BQ's Google BigQuery")
public class GoogleBigQueryConnector {

    @Config
    ConnectorConfig config;
    
    /**
     * Custom processor
     *
     * @param friend Name to be used to generate a greeting message.
     * @return A greeting message
     */
    @Processor
    public String sqlQueryAsString (String sqlQuery){
        try {
			return displayQueryResults(sqlQuery(sqlQuery));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    @Processor
    public List<Map> sqlQueryAsObject (String sqlQuery){
        List<Map> result = new ArrayList<Map>();
        List<TableRow> rows = sqlQuery(sqlQuery);
        for (TableRow r: rows){
        	LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        	for (String key: r.keySet()){
        		map.put(key, r.get(key));
        	}
        	result.add(map);
        }
        return result;
    }
    
    public List<TableRow> sqlQuery (String sqlQuery){
    	try{
        JobReference jobId;
        Job completedJob;
        jobId = startQuery(sqlQuery);
        completedJob = checkQueryResults(jobId);
        return getQueryResults(completedJob);
    	} catch (Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    /**
     * Creates a Query Job for a particular query on a dataset
     *
     * @param bigquery  an authorized BigQuery client
     * @param projectId a String containing the current Project ID
     * @param querySql  the actual query string
     * @return a reference to the inserted query Job
     * @throws IOException
     */
    private JobReference startQuery(String querySql) throws IOException {
        System.out.format("\nExecuting Query Job: %s\n", querySql);

        Job job = new Job();
        JobConfiguration jobConfig = new JobConfiguration();
        JobConfigurationQuery queryConfig = new JobConfigurationQuery();
        jobConfig.setQuery(queryConfig);

        job.setConfiguration(jobConfig);
        queryConfig.setQuery(querySql);
        Insert insert = config.getBigquery().jobs().insert(config.getProject(),job);

        JobReference jobId = insert.execute().getJobReference();
        return jobId;
    }

    /**
     * Polls the status of a BigQuery job, returns Job reference if "Done"
     *
     * @param bigquery  an authorized BigQuery client
     * @param projectId a string containing the current project ID
     * @param jobId     a reference to an inserted query Job
     * @return a reference to the completed Job
     * @throws IOException
     * @throws InterruptedException
     */
    private Job checkQueryResults(JobReference jobId)
            throws IOException, InterruptedException {
        // Variables to keep track of total query time
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (true) {
            Job pollJob = config.getBigquery().jobs().get(jobId.getProjectId(), jobId.getJobId()).execute();
            elapsedTime = System.currentTimeMillis() - startTime;
            System.out.format("Job status (%dms) %s: %s\n", elapsedTime,
                    jobId.getJobId(), pollJob.getStatus().getState());
            if (pollJob.getStatus().getState().equals("DONE")) {
                return pollJob;
            }
            // Pause execution for one second before polling job status again, to
            // reduce unnecessary calls to the BigQUery API and lower overall
            // application bandwidth.
            Thread.sleep(config.getPollingFrequency());
        }
    }

    /**
     * Makes an API call to the BigQuery API
     *
     * @param bigquery     an authorized BigQuery client
     * @param projectId    a string containing the current project ID
     * @param completedJob to the completed Job
     * @throws IOException
     */
    private List<TableRow> getQueryResults(Job completedJob) throws IOException {
        GetQueryResultsResponse queryResult = config.getBigquery().jobs()
                .getQueryResults(
                completedJob.getJobReference().getProjectId() , completedJob
                .getJobReference()
                .getJobId()
        ).execute();
        List<TableRow> rows = queryResult.getRows();
        return rows;
    }
    
    public static String displayQueryResults(List<TableRow> rows) throws IOException {
    	String result = "Query Results:\n------------\n";
        System.out.print("\nQuery Results:\n------------\n");
        for (TableRow row : rows) {
            for (TableCell field : row.getF()) {
                System.out.printf("%-20s", field.getV());
                result += "	|	" + ((field.getV() instanceof String )? field.getV() : ReflectionToStringBuilder.toString(field.getV()));
            }
            result += "\n";
            System.out.println();
        }
        return result;
    }
    


    /**
     * Creates a Query Job for a particular query on a dataset
     *
     * @param data Map with data to insert on the table
     * @param table name of the table into which the data will be inserted.
     * @return a reference to the inserted query Job
     * @throws IOException
     * @throws GeneralSecurityException 
     * @throws InterruptedException 
     */
    @Processor
    public TableDataInsertAllResponse insertData(Map<String, Object> data, String table) throws IOException, InterruptedException, GeneralSecurityException {
        List<TableDataInsertAllRequest.Rows> rows_ready;
        TableRow row;
        TableDataInsertAllRequest request;
        TableDataInsertAllRequest.Rows row_to_add;
        //We have to convert each row to json format
        rows_ready = new ArrayList<TableDataInsertAllRequest.Rows>();
        row = new TableRow();
        for (String key: data.keySet()){
        	row.set(key, data.get(key));
        }
        row_to_add = new TableDataInsertAllRequest.Rows();
        row_to_add.setJson(row);
        rows_ready.add(row_to_add);
        request = new TableDataInsertAllRequest().setRows(rows_ready);
        return config.getBigquery().tabledata().insertAll(config.getProject(), config.getDataset(), table, request).execute();
    }
    
    @Processor
    public TableDataInsertAllResponse insertListOfData(List<Map> list, String table) throws IOException, InterruptedException, GeneralSecurityException {
        List<TableDataInsertAllRequest.Rows> rows_ready;
        TableRow row;
        TableDataInsertAllRequest request;
        TableDataInsertAllRequest.Rows row_to_add;
        //We have to convert each row to json format
        rows_ready = new ArrayList<TableDataInsertAllRequest.Rows>();
        
        for (Map<String, Object> data : list){
        	row = new TableRow();
        	for (String key: data.keySet()){
        	row.set(key, data.get(key));
        	}
        	row_to_add = new TableDataInsertAllRequest.Rows();
            row_to_add.setJson(row);
            rows_ready.add(row_to_add);
        }
        request = new TableDataInsertAllRequest().setRows(rows_ready);
        return config.getBigquery().tabledata().insertAll(config.getProject(), config.getDataset(), table, request).execute();
    }
    
    /**
     * Creates a Query Job for a particular query on a dataset
     *
     * @param table the name of the table where to insert.
     * @param object the object with the information to insert.
     * @return a reference to the inserted query Job
     * @throws IOException
     * @throws GeneralSecurityException 
     * @throws InterruptedException 
     */
    public TableDataInsertAllResponse insertObject(Object object, String table) throws IOException, InterruptedException, GeneralSecurityException {
    	return insertData(objectToMap(object), table);
    }
    
    public TableDataInsertAllResponse insertListOfObjects(List<Object> list, String table) throws IOException, InterruptedException, GeneralSecurityException {
    	List<Map> mapList = new ArrayList<Map>();
    	for (Object o: list){
    		mapList.add(objectToMap(o));
    	}
    	return insertListOfData(mapList, table);
    }
    
    public static Map<String, Object> objectToMap(Object o){
    	LinkedHashMap<String, Object> map  = new LinkedHashMap<String, Object>();
    	for (int i=0; i< o.getClass().getFields().length; i++){
    		Field f = o.getClass().getFields()[i];
    			try {
					map.put(f.getName(), f.get(o));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    		
    	}
    	return map;
    }


    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }

}