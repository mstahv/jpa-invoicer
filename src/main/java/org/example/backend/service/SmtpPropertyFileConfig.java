package org.example.backend.service;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

/**
 * @author alejandro@vaadin.com
 **/
public class SmtpPropertyFileConfig implements PropertyFileConfig {
    @Override public String getPropertyFileName() {
        return "smtp.properties";
    }

    @Override public boolean isOptional() {
        return false;
    }
}
