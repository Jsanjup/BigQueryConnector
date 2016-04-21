package org.mule.modules.googlebigquery.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.apache.commons.httpclient.auth.InvalidCredentialsException;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.components.ConnectionManagement;
import org.mule.api.annotations.display.Password;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.Connect;
import org.mule.api.annotations.ConnectionIdentifier;
import org.mule.api.annotations.Disconnect;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.ValidateConnection;
import org.mule.api.annotations.param.ConnectionKey;
import org.mule.api.annotations.param.Default;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;

@ConnectionManagement(friendlyName = "Big Query Connection")
public class ConnectorConfig {

	private Bigquery bigquery;

	/**
	 * Greeting message
	 */
	@Configurable
	@Default("project_name")
	private String Project;

	@Configurable
	@Default("dataset_name")
	private String Dataset;

	@Configurable
	@Default("application_name")
	private String Application;
	
	@Configurable
	@Default("1000")
	private long PollingFrequency;

	/**
	 * @return the project
	 */
	public String getProject() {
		return Project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(String project) {
		Project = project;
	}

	/**
	 * @return the dataset
	 */
	public String getDataset() {
		return Dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(String dataset) {
		Dataset = dataset;
	}

	@Connect
	@TestConnectivity
	public void connect(@ConnectionKey String username, @ConnectionKey String passwordfile) throws ConnectionException{
		HttpTransport TRANSPORT;
		JsonFactory JSON_FACTORY;
		InputStream istream;
		File file;
		GoogleCredential credential;

		try {
			TRANSPORT= new NetHttpTransport();
			JSON_FACTORY = JacksonFactory.getDefaultInstance();

			//Use the password saved in this file
			istream = ConnectorConfig.class.getResourceAsStream("/" + passwordfile);
			file = createFileFromInputStream(istream);

			//Project store-dev-001 with dataset bq-services-mdm
			credential = new  GoogleCredential.Builder()
			.setTransport(TRANSPORT)
			.setJsonFactory(JSON_FACTORY)
			.setServiceAccountId(username)
			.setServiceAccountScopes(Collections.singleton(BigqueryScopes.BIGQUERY))
			.setServiceAccountPrivateKeyFromP12File(file)
			.build();

			bigquery = new Bigquery.Builder(TRANSPORT,JSON_FACTORY,credential)
			.setApplicationName(Application)
			.build();

		} catch (InvalidCredentialsException e) {
			throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), "Invalid credentials");
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), "Invalid credentials");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionException(ConnectionExceptionCode.INCORRECT_CREDENTIALS, e.getMessage(), "Invalid credentials");
		}
	}

	@Disconnect
	public void disconnect() {
		bigquery = null;
	}

	@ValidateConnection
	public boolean isConnected() {
		return bigquery != null;
	}

	@ConnectionIdentifier
	public String connectionId() {
		return "001";
	}

	private static File createFileFromInputStream(InputStream inputStream) {

		try{
			File f = new File("my_password");
			OutputStream outputStream = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int length;

			while((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer,0,length);
			}

			outputStream.close();
			inputStream.close();

			return f;
		}catch (IOException e) {
			//Logging exception
		}

		return null;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return Application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		Application = application;
	}

	/**
	 * @return the bigquery
	 */
	public Bigquery getBigquery() {
		return bigquery;
	}

	/**
	 * @param bigquery the bigquery to set
	 */
	public void setBigquery(Bigquery bigquery) {
		bigquery = bigquery;
	}

	/**
	 * @return the pollingFrequency
	 */
	public long getPollingFrequency() {
		return PollingFrequency;
	}

	/**
	 * @param pollingFrequency the pollingFrequency to set
	 */
	public void setPollingFrequency(long pollingFrequency) {
		PollingFrequency = pollingFrequency;
	}


}