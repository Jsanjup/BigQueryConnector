
package org.mule.modules.googlebigquery.generated.connectivity;

import javax.annotation.Generated;
import org.mule.devkit.internal.connection.management.ConnectionManagementConnectionKey;

@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.8.0", date = "2016-04-06T05:13:04+02:00", comments = "Build UNNAMED.2762.e3b1307")
public class ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey implements ConnectionManagementConnectionKey
{

    /**
     * 
     */
    private String username;
    /**
     * 
     */
    private String passwordfile;

    public ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey(String username, String passwordfile) {
        this.username = username;
        this.passwordfile = passwordfile;
    }

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

    @Override
    public int hashCode() {
        int result = ((this.username!= null)?this.username.hashCode(): 0);
        result = ((31 *result)+((this.passwordfile!= null)?this.passwordfile.hashCode(): 0));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey)) {
            return false;
        }
        ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey that = ((ConnectionManagementConfigGoogleBigQueryConnectorConnectionKey) o);
        if (((this.username!= null)?(!this.username.equals(that.username)):(that.username!= null))) {
            return false;
        }
        if (((this.passwordfile!= null)?(!this.passwordfile.equals(that.passwordfile)):(that.passwordfile!= null))) {
            return false;
        }
        return true;
    }

}
