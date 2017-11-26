package com.hilum.otp.dataaccess.model;

import javax.persistence.Id;
import java.io.Serializable;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String clientId;

    private String clientSecret;

    private String realm;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret == null ? null : clientSecret.trim();
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm == null ? null : realm.trim();
    }
}
