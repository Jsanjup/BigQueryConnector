
package org.mule.modules.googlebigquery.generated.connectivity;

import javax.annotation.Generated;
import org.apache.commons.pool.KeyedObjectPool;
import org.mule.api.MetadataAware;
import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.config.MuleProperties;
import org.mule.api.context.MuleContextAware;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.retry.RetryPolicyTemplate;
import org.mule.common.DefaultTestResult;
import org.mule.common.TestResult;
import org.mule.common.Testable;
import org.mule.config.PoolingProfile;
import org.mule.devkit.api.lifecycle.LifeCycleManager;
import org.mule.devkit.api.lifecycle.MuleContextAwareManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionManager;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorAdapter;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectorFactory;
import org.mule.devkit.internal.connection.management.ConnectionManagementProcessTemplate;
import org.mule.devkit.internal.connection.management.UnableToAcquireConnectionException;
import org.mule.devkit.internal.connectivity.ConnectivityTestingErrorHandler;
import org.mule.devkit.processor.ExpressionEvaluatorSupport;
import org.mule.modules.googlebigquery.GoogleBigQueryConnector;
import org.mule.modules.googlebigquery.config.ConnectorConfig;
import org.mule.modules.googlebigquery.generated.adapters.GoogleBigQueryConnectorConnectionManagementAdapter;
import org.mule.modules.googlebigquery.generated.pooling.DevkitGenericKeyedObjectPool;


/**
 * A {@code GoogleBigQueryConnectorConfigConnectionManagementConnectionManager} is a wrapper around {@link GoogleBigQueryConnector } that adds connection management capabilities to the pojo.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-04-06T05:13:04+02:00", comments = "Build UNNAMED.2762.e3b1307")
public class GoogleBigQueryConnectorConfigConnectionManagementConnectionManager
    extends ExpressionEvaluatorSupport
    implements MetadataAware, MuleContextAware, ProcessAdapter<GoogleBigQueryConnectorConnectionManagementAdapter> , Capabilities, Disposable, Initialisable, Testable, ConnectionManagementConnectionManager<ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey, GoogleBigQueryConnectorConnectionManagementAdapter, ConnectorConfig>
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String passwordfile;
    private String Project;
    private String Dataset;
    private String Application;
    private long PollingFrequency;
    /**
     * Mule Context
     * 
     */
    protected MuleContext muleContext;
    /**
     * Connector Pool
     * 
     */
    private KeyedObjectPool connectionPool;
    protected PoolingProfile poolingProfile;
    protected RetryPolicyTemplate retryPolicyTemplate;
    private final static String MODULE_NAME = "BQ's Google BigQuery";
    private final static String MODULE_VERSION = "1.0.0-SNAPSHOT";
    private final static String DEVKIT_VERSION = "3.8.0";
    private final static String DEVKIT_BUILD = "UNNAMED.2762.e3b1307";
    private final static String MIN_MULE_VERSION = "3.5.0";

    /**
     * Sets username
     * 
     * @param value Value to set
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Retrieves username
     * 
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets passwordfile
     * 
     * @param value Value to set
     */
    public void setPasswordfile(String value) {
        this.passwordfile = value;
    }

    /**
     * Retrieves passwordfile
     * 
     */
    public String getPasswordfile() {
        return this.passwordfile;
    }

    /**
     * Sets Project
     * 
     * @param value Value to set
     */
    public void setProject(String value) {
        this.Project = value;
    }

    /**
     * Retrieves Project
     * 
     */
    public String getProject() {
        return this.Project;
    }

    /**
     * Sets Dataset
     * 
     * @param value Value to set
     */
    public void setDataset(String value) {
        this.Dataset = value;
    }

    /**
     * Retrieves Dataset
     * 
     */
    public String getDataset() {
        return this.Dataset;
    }

    /**
     * Sets Application
     * 
     * @param value Value to set
     */
    public void setApplication(String value) {
        this.Application = value;
    }

    /**
     * Retrieves Application
     * 
     */
    public String getApplication() {
        return this.Application;
    }

    /**
     * Sets PollingFrequency
     * 
     * @param value Value to set
     */
    public void setPollingFrequency(long value) {
        this.PollingFrequency = value;
    }

    /**
     * Retrieves PollingFrequency
     * 
     */
    public long getPollingFrequency() {
        return this.PollingFrequency;
    }

    /**
     * Sets muleContext
     * 
     * @param value Value to set
     */
    public void setMuleContext(MuleContext value) {
        this.muleContext = value;
    }

    /**
     * Retrieves muleContext
     * 
     */
    public MuleContext getMuleContext() {
        return this.muleContext;
    }

    /**
     * Sets poolingProfile
     * 
     * @param value Value to set
     */
    public void setPoolingProfile(PoolingProfile value) {
        this.poolingProfile = value;
    }

    /**
     * Retrieves poolingProfile
     * 
     */
    public PoolingProfile getPoolingProfile() {
        return this.poolingProfile;
    }

    /**
     * Sets retryPolicyTemplate
     * 
     * @param value Value to set
     */
    public void setRetryPolicyTemplate(RetryPolicyTemplate value) {
        this.retryPolicyTemplate = value;
    }

    /**
     * Retrieves retryPolicyTemplate
     * 
     */
    public RetryPolicyTemplate getRetryPolicyTemplate() {
        return this.retryPolicyTemplate;
    }

    public void initialise() {
        connectionPool = new DevkitGenericKeyedObjectPool(new ConnectionManagementConnectorFactory(this), poolingProfile);
        if (retryPolicyTemplate == null) {
            retryPolicyTemplate = muleContext.getRegistry().lookupObject(MuleProperties.OBJECT_DEFAULT_RETRY_POLICY_TEMPLATE);
        }
    }

    @Override
    public void dispose() {
        try {
            connectionPool.close();
        } catch (Exception e) {
        }
    }

    public GoogleBigQueryConnectorConnectionManagementAdapter acquireConnection(ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey key)
        throws Exception
    {
        return ((GoogleBigQueryConnectorConnectionManagementAdapter) connectionPool.borrowObject(key));
    }

    public void releaseConnection(ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey key, GoogleBigQueryConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.returnObject(key, connection);
    }

    public void destroyConnection(ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey key, GoogleBigQueryConnectorConnectionManagementAdapter connection)
        throws Exception
    {
        connectionPool.invalidateObject(key, connection);
    }

    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        if (capability == ModuleCapability.CONNECTION_MANAGEMENT_CAPABLE) {
            return true;
        }
        return false;
    }

    @Override
    public<P >ProcessTemplate<P, GoogleBigQueryConnectorConnectionManagementAdapter> getProcessTemplate() {
        return new ConnectionManagementProcessTemplate(this, muleContext);
    }

    @Override
    public ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey getDefaultConnectionKey() {
        return new ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey(getUsername(), getPasswordfile());
    }

    @Override
    public ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey getEvaluatedConnectionKey(MuleEvent event)
        throws Exception
    {
        if (event!= null) {
            final String _transformedUsername = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("username").getGenericType(), null, getUsername()));
            if (_transformedUsername == null) {
                throw new UnableToAcquireConnectionException("Parameter username in method connect can't be null because is not @Optional");
            }
            final String _transformedPasswordfile = ((String) evaluateAndTransform(muleContext, event, this.getClass().getDeclaredField("passwordfile").getGenericType(), null, getPasswordfile()));
            if (_transformedPasswordfile == null) {
                throw new UnableToAcquireConnectionException("Parameter passwordfile in method connect can't be null because is not @Optional");
            }
            return new ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey(_transformedUsername, _transformedPasswordfile);
        }
        return getDefaultConnectionKey();
    }

    public String getModuleName() {
        return MODULE_NAME;
    }

    public String getModuleVersion() {
        return MODULE_VERSION;
    }

    public String getDevkitVersion() {
        return DEVKIT_VERSION;
    }

    public String getDevkitBuild() {
        return DEVKIT_BUILD;
    }

    public String getMinMuleVersion() {
        return MIN_MULE_VERSION;
    }

    @Override
    public ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey getConnectionKey(MessageProcessor messageProcessor, MuleEvent event)
        throws Exception
    {
        return getEvaluatedConnectionKey(event);
    }

    @Override
    public ConnectionManagementConnectionAdapter newConnection() {
        ConnectorConfigGoogleBigQueryConnectorAdapter connection = new ConnectorConfigGoogleBigQueryConnectorAdapter();
        connection.setProject(getProject());
        connection.setDataset(getDataset());
        connection.setApplication(getApplication());
        connection.setPollingFrequency(getPollingFrequency());
        return connection;
    }

    @Override
    public ConnectionManagementConnectorAdapter newConnector(ConnectionManagementConnectionAdapter<ConnectorConfig, ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey> connection) {
        GoogleBigQueryConnectorConnectionManagementAdapter connector = new GoogleBigQueryConnectorConnectionManagementAdapter();
        connector.setConfig(connection.getStrategy());
        return connector;
    }

    public ConnectionManagementConnectionAdapter getConnectionAdapter(ConnectionManagementConnectorAdapter adapter) {
        GoogleBigQueryConnectorConnectionManagementAdapter connector = ((GoogleBigQueryConnectorConnectionManagementAdapter) adapter);
        ConnectionManagementConnectionAdapter strategy = ((ConnectionManagementConnectionAdapter) connector.getConfig());
        return strategy;
    }

    public TestResult test() {
        try {
            ConnectorConfigGoogleBigQueryConnectorAdapter strategy = ((ConnectorConfigGoogleBigQueryConnectorAdapter) newConnection());
            MuleContextAwareManager.setMuleContext(strategy, this.muleContext);
            LifeCycleManager.executeInitialiseAndStart(strategy);
            ConnectionManagementConnectorAdapter connectorAdapter = newConnector(strategy);
            MuleContextAwareManager.setMuleContext(connectorAdapter, this.muleContext);
            LifeCycleManager.executeInitialiseAndStart(connectorAdapter);
            strategy.test(getDefaultConnectionKey());
            return new DefaultTestResult(org.mule.common.Result.Status.SUCCESS);
        } catch (Exception e) {
            return ((DefaultTestResult) ConnectivityTestingErrorHandler.buildFailureTestResult(e));
        }
    }

}
