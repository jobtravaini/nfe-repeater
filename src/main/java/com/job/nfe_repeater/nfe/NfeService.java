package com.job.nfe_repeater.nfe;

import com.job.nfe_repeater.error.NfeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NfeService {

    @Autowired
    private NfeRepository repository;

    public String retrieveNfeXmlByAccessKey(String key) {
        Nfe result = repository.findById(key).orElseThrow(() -> new NfeNotFoundException(key));
        return result.getXml();
    }
}
