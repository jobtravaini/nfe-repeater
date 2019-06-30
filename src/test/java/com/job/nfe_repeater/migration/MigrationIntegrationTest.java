package com.job.nfe_repeater.migration;

import com.job.nfe_repeater.arquivei.domain.response.NfeData;
import com.job.nfe_repeater.arquivei.rest.NfeReceivedConsumer;
import com.job.nfe_repeater.nfe.NfeRepository;
import com.job.nfe_repeater.tools.DatabaseMigration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MigrationIntegrationTest {

    @Autowired
    private DatabaseMigration migration;

    @Autowired
    private NfeRepository nfeRepository;

    @Autowired
    private NfeReceivedConsumer nfeReceivedConsumer;

    @Test
    public void whenMigrationIsDone_thenDatabaseIsSynchronized() {
        List<NfeData> response = nfeReceivedConsumer.getNfeList();
            for (NfeData entry : response) {
                Assertions.assertThat(nfeRepository.findById(entry.getAccessKey()).isPresent()).isEqualTo(true);
            }
    }
}
