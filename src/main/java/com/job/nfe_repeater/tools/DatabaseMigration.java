package com.job.nfe_repeater.tools;

import com.job.nfe_repeater.arquivei.domain.response.Data;
import com.job.nfe_repeater.arquivei.domain.response.NfeReceivedResponse;
import com.job.nfe_repeater.arquivei.rest.NfeReceivedConsumer;
import com.job.nfe_repeater.nfe.Nfe;
import com.job.nfe_repeater.nfe.NfeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DatabaseMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigration.class);

    @Autowired
    private NfeReceivedConsumer consumer;
    @Autowired
    private NfeRepository repository;

    public void migrateNfe() {
        LOGGER.info("Migrating Database");
        NfeReceivedResponse response = consumer.getNfe();
        processResponse(response);
        LOGGER.info("Database migration complete");
    }

    private void migrateNfe(String url) {
        NfeReceivedResponse response = consumer.getNfe(url);
        processResponse(response);
    }

    private void processResponse(NfeReceivedResponse response) {
        if (null != response.getData() && response.getData().length > 0) {
            persistNfe(response.getData());
            if (null != response.getStatus() && !StringUtils.isEmpty(response.getPage().getNext())) {
                migrateNfe(response.getPage().getNext());
            }
        }
    }

    private void persistNfe(Data[] nfeArray) {
        for (Data nfe : nfeArray) {
            Nfe entry = new Nfe(nfe.getAccessKey(), nfe.getXml());
            repository.save(entry);
        }
    }

}
