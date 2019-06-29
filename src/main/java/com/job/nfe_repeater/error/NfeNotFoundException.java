package com.job.nfe_repeater.error;

import java.text.MessageFormat;

public class NfeNotFoundException extends RuntimeException {

    public NfeNotFoundException(String key) {
        super(MessageFormat.format("The registry {0} was not found", key));
    }
}
