
package org.mule.modules.googlebigquery.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.modules.googlebigquery.GoogleBigQueryConnector;


/**
 * A <code>GoogleBigQueryConnectorCapabilitiesAdapter</code> is a wrapper around {@link GoogleBigQueryConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-04-06T05:13:04+02:00", comments = "Build UNNAMED.2762.e3b1307")
public class GoogleBigQueryConnectorCapabilitiesAdapter
    extends GoogleBigQueryConnector
    implements Capabilities
{


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

}
