package com.job.nfe_repeater.tools;

import com.job.nfe_repeater.arquivei.domain.response.NfeData;
import com.job.nfe_repeater.arquivei.rest.NfeReceivedConsumer;
import com.job.nfe_repeater.nfe.Nfe;
import com.job.nfe_repeater.nfe.NfeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigration.class);

    @Autowired
    private NfeReceivedConsumer consumer;
    @Autowired
    private NfeRepository repository;

    public void migrateNfe() {
        LOGGER.info("Migrating Database");
        List<NfeData> nfeList = consumer.getNfeList();
        persistNfe(nfeList);
        LOGGER.info("Database migration complete");
    }

    private void persistNfe(List<NfeData> nfeArray) {
        for (NfeData nfe : nfeArray) {
            Nfe entry = new Nfe(nfe.getAccessKey(), nfe.getXml());
            repository.save(entry);
        }
    }

}
