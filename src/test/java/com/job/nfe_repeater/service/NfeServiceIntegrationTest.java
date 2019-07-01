package com.job.nfe_repeater.service;

import com.job.nfe_repeater.arquivei.domain.response.NfeData;
import com.job.nfe_repeater.arquivei.rest.NfeReceivedConsumer;
import com.job.nfe_repeater.error.NfeNotFoundException;
import com.job.nfe_repeater.nfe.NfeService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NfeServiceIntegrationTest {

    @Autowired
    private NfeService service;

    @Autowired
    private NfeReceivedConsumer nfeReceivedConsumer;

    @Test
    public void whenRetrievingXmlByValidAccessKey_thenReturnNfeXml() {
        List<NfeData> nfeList = nfeReceivedConsumer.getNfeList();
        for (NfeData nfe : nfeList) {
            Assertions.assertThat(service.retrieveNfeXmlByAccessKey(nfe.getAccessKey()))
                    .isEqualTo(nfe.getXml());
        }
    }

    @Test
    public void whenRetrievingXmlByInvalidAccessKey_thenAnExceptionOccurs() {
        String invalidKey = "MyInvalidKey";
        Assertions.assertThatThrownBy(() -> service.retrieveNfeXmlByAccessKey(invalidKey))
                .isInstanceOf(NfeNotFoundException.class);
    }

}
