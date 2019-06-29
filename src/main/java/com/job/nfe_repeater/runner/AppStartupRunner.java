package com.job.nfe_repeater.runner;

import com.job.nfe_repeater.tools.DatabaseMigration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private DatabaseMigration migration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Performing startup routines");
        migration.migrateNfe();
        LOGGER.info("Startup routines are complete");
    }

}
