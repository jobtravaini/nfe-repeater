package com.job.nfe_repeater.repository;

import com.job.nfe_repeater.error.NfeNotFoundException;
import com.job.nfe_repeater.nfe.Nfe;
import com.job.nfe_repeater.nfe.NfeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityExistsException;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NfeRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private NfeRepository repository;

    @Test
    public void whenFindById_thenReturnNfe() {
        String key = "1";
        String xml = "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"></NFe>";

        Nfe nfe = insertNfeEntry(key, xml);
        Nfe found = repository.findById(key).orElseThrow(() -> new NfeNotFoundException(key));

        Assertions.assertThat(nfe.getXml())
                .isEqualTo(found.getXml());
    }

    @Test
    public void whenNotFindById_thenExceptionIsThrown() {
        String key = "2";
        Assertions.assertThatThrownBy(() -> repository.findById(key).orElseThrow(() -> new NfeNotFoundException(key)))
                .isInstanceOf(NfeNotFoundException.class)
                .hasMessageContaining(key);
    }

    @Test
    public void whenInsertingSameId_thenExceptionIsThrown() {
        String key = "1";
        String xml = "<NFe xmlns=\"http://www.portalfiscal.inf.br/nfe\"></NFe>";

        insertNfeEntry(key, xml);
        Assertions.assertThatThrownBy(() -> insertNfeEntry(key, xml))
                .isInstanceOf(EntityExistsException.class);
    }

    private Nfe insertNfeEntry(String accessKey, String xml) {
        Nfe nfe = new Nfe(accessKey, xml);
        entityManager.persist(nfe);
        entityManager.flush();

        return nfe;
    }
}
