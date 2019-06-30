package com.job.nfe_repeater.controller;

import com.job.nfe_repeater.arquivei.domain.response.NfeData;
import com.job.nfe_repeater.arquivei.rest.NfeReceivedConsumer;
import com.job.nfe_repeater.tools.DatabaseMigration;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NfeControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DatabaseMigration migration;

    @Autowired
    private NfeReceivedConsumer nfeReceivedConsumer;

    @Test
    public void whenConsumingRestWithValidAccessKey_thenResponseContainsNfeXml() throws Exception {
        List<NfeData> nfeList = nfeReceivedConsumer.getNfeList();
        for (NfeData nfe : nfeList) {
            mvc.perform(MockMvcRequestBuilders.get("/nfe")
                    .param("key", nfe.getAccessKey())
                    .contentType(MediaType.TEXT_PLAIN_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(nfe.getXml()));
        }
    }

    @Test
    public void whenConsumingRestWithInvalidAccessKey_thenResponseContainsNfeXml() throws Exception {
        String invalidKey = "MyInvalidKey";
        mvc.perform(MockMvcRequestBuilders.get("/nfe")
                .param("key", invalidKey)
                .contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(invalidKey)));
    }

}
