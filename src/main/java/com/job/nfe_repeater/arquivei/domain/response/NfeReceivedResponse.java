package com.job.nfe_repeater.arquivei.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NfeReceivedResponse {

    private Status status;
    private List<NfeData> data;
    private PageInformation pageInformation;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<NfeData> getData() {
        return data;
    }

    public void setData(List<NfeData> data) {
        this.data = data;
    }

    public PageInformation getPageInformation() {
        return pageInformation;
    }

    public void setPageInformation(PageInformation pageInformation) {
        this.pageInformation = pageInformation;
    }

}
