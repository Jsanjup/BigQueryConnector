
package org.mule.modules.googlebigquery.generated.config;

import javax.annotation.Generated;
import org.mule.config.MuleManifest;
import org.mule.config.PoolingProfile;
import org.mule.modules.googlebigquery.generated.connectivity.GoogleBigQueryConnectorConfigConnectionManagementConnectionManager;
import org.mule.security.oauth.config.AbstractDevkitBasedDefinitionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-04-06T05:13:04+02:00", comments = "Build UNNAMED.2762.e3b1307")
public class GoogleBigQueryConnectorConnectorConfigConfigDefinitionParser
    extends AbstractDevkitBasedDefinitionParser
{

    private static Logger logger = LoggerFactory.getLogger(GoogleBigQueryConnectorConnectorConfigConfigDefinitionParser.class);

    public String moduleName() {
        return "BQ's Google BigQuery";
    }

    public BeanDefinition parse(Element element, ParserContext parserContext) {
        parseConfigName(element);
        BeanDefinitionBuilder builder = getBeanDefinitionBuilder(parserContext);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        setInitMethodIfNeeded(builder, GoogleBigQueryConnectorConfigConnectionManagementConnectionManager.class);
        setDestroyMethodIfNeeded(builder, GoogleBigQueryConnectorConfigConnectionManagementConnectionManager.class);
        parseProperty(builder, element, "Project", "Project");
        parseProperty(builder, element, "Dataset", "Dataset");
        parseProperty(builder, element, "Application", "Application");
        parseProperty(builder, element, "PollingFrequency", "PollingFrequency");
        parseProperty(builder, element, "username", "username");
        parseProperty(builder, element, "passwordfile", "passwordfile");
        BeanDefinitionBuilder poolingProfileBuilder = BeanDefinitionBuilder.rootBeanDefinition(PoolingProfile.class.getName());
        Element poolingProfileElement = DomUtils.getChildElementByTagName(element, "connection-pooling-profile");
        if (poolingProfileElement!= null) {
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxActive");
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxIdle");
            parseProperty(poolingProfileBuilder, poolingProfileElement, "maxWait");
            if (hasAttribute(poolingProfileElement, "exhaustedAction")) {
                poolingProfileBuilder.addPropertyValue("exhaustedAction", PoolingProfile.POOL_EXHAUSTED_ACTIONS.get(poolingProfileElement.getAttribute("exhaustedAction")));
            }
            if (hasAttribute(poolingProfileElement, "initialisationPolicy")) {
                poolingProfileBuilder.addPropertyValue("initialisationPolicy", PoolingProfile.POOL_INITIALISATION_POLICIES.get(poolingProfileElement.getAttribute("initialisationPolicy")));
            }
            if (hasAttribute(poolingProfileElement, "evictionCheckIntervalMillis")) {
                parseProperty(poolingProfileBuilder, poolingProfileElement, "evictionCheckIntervalMillis");
            }
            if (hasAttribute(poolingProfileElement, "minEvictionMillis")) {
                parseProperty(poolingProfileBuilder, poolingProfileElement, "minEvictionMillis");
            }
            builder.addPropertyValue("poolingProfile", poolingProfileBuilder.getBeanDefinition());
        }
        BeanDefinition definition = builder.getBeanDefinition();
        setNoRecurseOnDefinition(definition);
        parseRetryPolicyTemplate("reconnect", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-forever", element, parserContext, builder, definition);
        parseRetryPolicyTemplate("reconnect-custom-strategy", element, parserContext, builder, definition);
        return definition;
    }

    private BeanDefinitionBuilder getBeanDefinitionBuilder(ParserContext parserContext) {
        try {
            return BeanDefinitionBuilder.rootBeanDefinition(GoogleBigQueryConnectorConfigConnectionManagementConnectionManager.class.getName());
        } catch (NoClassDefFoundError noClassDefFoundError) {
            String muleVersion = "";
            try {
                muleVersion = MuleManifest.getProductVersion();
            } catch (Exception _x) {
                logger.error("Problem while reading mule version");
            }
            logger.error(("Cannot launch the mule app, the configuration [config] within the connector [google-big-query] is not supported in mule "+ muleVersion));
            throw new BeanDefinitionParsingException(new Problem(("Cannot launch the mule app, the configuration [config] within the connector [google-big-query] is not supported in mule "+ muleVersion), new Location(parserContext.getReaderContext().getResource()), null, noClassDefFoundError));
        }
    }

}
