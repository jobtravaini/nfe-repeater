package com.job.nfe_repeater.nfe;

import javax.persistence.*;

@Entity
@Table(name = "nfe")
public class Nfe {

    @Id
    @Column(name = "access_key", unique = true)
    private String accessKey;
    @Column(name = "xml_value", columnDefinition = "CLOB NOT NULL")
    @Lob
    private String xml;

    protected Nfe() {

    }

    public Nfe(String accessKey, String xml) {
        this.accessKey = accessKey;
        this.xml = xml;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getXml() {
        return xml;
    }

}
