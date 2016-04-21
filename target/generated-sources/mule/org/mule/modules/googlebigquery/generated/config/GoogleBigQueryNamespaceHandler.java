
package org.mule.modules.googlebigquery.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * Registers bean definitions parsers for handling elements in <code>http://www.mulesoft.org/schema/mule/google-big-query</code>.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-04-06T05:13:04+02:00", comments = "Build UNNAMED.2762.e3b1307")
public class GoogleBigQueryNamespaceHandler
    extends NamespaceHandlerSupport
{

    private static Logger logger = LoggerFactory.getLogger(GoogleBigQueryNamespaceHandler.class);

    private void handleException(String beanName, String beanScope, NoClassDefFoundError noClassDefFoundError) {
        String muleVersion = "";
        try {
            muleVersion = MuleManifest.getProductVersion();
        } catch (Exception _x) {
            logger.error("Problem while reading mule version");
        }
        logger.error(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-big-query] is not supported in mule ")+ muleVersion));
        throw new FatalBeanException(((((("Cannot launch the mule app, the  "+ beanScope)+" [")+ beanName)+"] within the connector [google-big-query] is not supported in mule ")+ muleVersion), noClassDefFoundError);
    }

    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after construction but before any custom elements are parsed. 
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     * 
     */
    public void init() {
        try {
            this.registerBeanDefinitionParser("config", new GoogleBigQueryConnectorConnectorConfigConfigDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("config", "@Config", ex);
        }
        try {
            this.registerBeanDefinitionParser("sql-query-as-string", new SqlQueryAsStringDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("sql-query-as-string", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("sql-query-as-object", new SqlQueryAsObjectDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("sql-query-as-object", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("insert-data", new InsertDataDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("insert-data", "@Processor", ex);
        }
        try {
            this.registerBeanDefinitionParser("insert-list-of-data", new InsertListOfDataDefinitionParser());
        } catch (NoClassDefFoundError ex) {
            handleException("insert-list-of-data", "@Processor", ex);
        }
    }

}
