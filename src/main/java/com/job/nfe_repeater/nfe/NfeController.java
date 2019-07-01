package com.job.nfe_repeater.nfe;

import com.job.nfe_repeater.tools.DatabaseMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class NfeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NfeController.class);


    @Autowired
    private NfeService service;
    @Autowired
    private DatabaseMigration migration;

    @RequestMapping("/nfe")
    public String retrieveNfeByAccessKey(@RequestParam("key") String key) {
        LOGGER.info("Request to retrieving nfe by access key: " + key);
        return service.retrieveNfeXmlByAccessKey(key);
    }

    @PostMapping("/resync")
    public void synchronizeDatabase(){
        LOGGER.info("Resynchronizing database");
        migration.migrateNfe();
    }

}
