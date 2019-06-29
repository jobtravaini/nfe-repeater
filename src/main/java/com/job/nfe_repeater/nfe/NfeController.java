package com.job.nfe_repeater.nfe;

import com.job.nfe_repeater.error.NfeNotFoundException;
import com.job.nfe_repeater.tools.DatabaseMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class NfeController {

    @Autowired
    private NfeRepository repository;
    @Autowired
    private DatabaseMigration migration;

    @RequestMapping("/nfe")
    public String retrieveNfeByAccessKey(@RequestParam("key") String key) {
        Nfe result = repository.findById(key).orElseThrow(() -> new NfeNotFoundException(key));
        return result.getXml();
    }

    @RequestMapping("/migrate")
    public void migrateDatabase(){
        migration.migrateNfe();
    }

}
