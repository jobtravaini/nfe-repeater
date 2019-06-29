package com.job.nfe_repeater.arquivei.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

    @JsonProperty("access_key")
    private String accessKey;
    private String xml;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

}
